$ErrorActionPreference = "Stop"

if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    throw "JDK tidak ditemukan. Pasang JDK lalu pastikan javac tersedia di PATH."
}

if (-not (Get-Command sonar-scanner -ErrorAction SilentlyContinue)) {
    throw "SonarScanner CLI tidak ditemukan. Pasang SonarScanner CLI dan tambahkan folder bin ke PATH."
}

if (-not $env:SONAR_TOKEN) {
    throw 'Token belum diatur. Jalankan $env:SONAR_TOKEN = "TOKEN_ANDA" terlebih dahulu.'
}

$serverUrl = "http://localhost:9000"
try {
    $null = Invoke-WebRequest -Uri "$serverUrl/api/system/status" -TimeoutSec 5
} catch {
    throw "SonarQube tidak dapat diakses di $serverUrl. Jalankan server terlebih dahulu."
}

$buildDirectory = Join-Path $PSScriptRoot "build\sonar-classes"
New-Item -ItemType Directory -Force -Path $buildDirectory | Out-Null

$sourceFiles = Get-ChildItem -Path (Join-Path $PSScriptRoot "src") -Recurse -Filter *.java |
    Select-Object -ExpandProperty FullName
if (-not $sourceFiles) {
    throw "Tidak ditemukan berkas Java di folder src."
}

Push-Location $PSScriptRoot
try {
    & javac -encoding UTF-8 -d $buildDirectory $sourceFiles
    if ($LASTEXITCODE -ne 0) {
        throw "Kompilasi Java gagal dengan exit code $LASTEXITCODE."
    }

    & sonar-scanner "-Dsonar.token=$env:SONAR_TOKEN"
    if ($LASTEXITCODE -ne 0) {
        throw "Analisis SonarQube gagal dengan exit code $LASTEXITCODE."
    }
} finally {
    Pop-Location
}
