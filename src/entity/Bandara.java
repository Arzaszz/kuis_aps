package entity;

public class Bandara {
    private final String kode;
    private final String nama;
    private final String kota;

    public Bandara(String kode, String nama, String kota) {
        this.kode = wajib(kode, "Kode bandara");
        this.nama = wajib(nama, "Nama bandara");
        this.kota = wajib(kota, "Kota");
    }

    private static String wajib(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " wajib diisi.");
        }
        return nilai.trim();
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public String getKota() { return kota; }
}
