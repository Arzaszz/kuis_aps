package entity;

public class Maskapai {
    private final String kode;
    private final String nama;

    public Maskapai(String kode, String nama) {
        if (kode == null || kode.trim().isEmpty() || nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode dan nama maskapai wajib diisi.");
        }
        this.kode = kode.trim().toUpperCase();
        this.nama = nama.trim();
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
}
