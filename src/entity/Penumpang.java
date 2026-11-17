package entity;

public class Penumpang {
    private final String nama;
    private final String email;
    private final String telepon;
    private final String nomorIdentitas;

    public Penumpang(String nama, String email, String telepon, String nomorIdentitas) {
        this.nama = wajib(nama, "Nama penumpang");
        this.email = wajib(email, "Email penumpang");
        this.telepon = telepon == null ? "" : telepon.trim();
        this.nomorIdentitas = wajib(nomorIdentitas, "Nomor identitas");
    }

    private static String wajib(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " wajib diisi.");
        }
        return nilai.trim();
    }

    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public String getTelepon() { return telepon; }
    public String getNomorIdentitas() { return nomorIdentitas; }
}
