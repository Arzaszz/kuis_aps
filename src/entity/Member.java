package entity;

public class Member {
    private final String id;
    private String nama;
    private String email;
    private String telepon;
    private String nomorIdentitas;

    public Member(String id, String nama, String email, String telepon, String nomorIdentitas) {
        this.id = wajib(id, "ID member");
        updateProfil(nama, email, telepon, nomorIdentitas);
    }

    public void updateProfil(String nama, String email, String telepon, String nomorIdentitas) {
        this.nama = bersihkan(nama);
        this.email = bersihkan(email);
        this.telepon = bersihkan(telepon);
        this.nomorIdentitas = bersihkan(nomorIdentitas);
    }

    public boolean profilLengkap() {
        return terisi(nama) && terisi(email) && terisi(telepon) && terisi(nomorIdentitas)
                && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    private static String bersihkan(String nilai) {
        return nilai == null ? "" : nilai.trim();
    }

    private static boolean terisi(String nilai) { return nilai != null && !nilai.trim().isEmpty(); }

    private static String wajib(String nilai, String namaField) {
        if (!terisi(nilai)) {
            throw new IllegalArgumentException(namaField + " wajib diisi.");
        }
        return nilai.trim();
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public String getTelepon() { return telepon; }
    public String getNomorIdentitas() { return nomorIdentitas; }
}
