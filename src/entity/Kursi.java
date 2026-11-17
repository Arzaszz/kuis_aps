package entity;

public class Kursi {
    private final String nomor;
    private final KelasPenerbangan kelas;

    public Kursi(String nomor, KelasPenerbangan kelas) {
        if (nomor == null || nomor.trim().isEmpty() || kelas == null) {
            throw new IllegalArgumentException("Nomor dan kelas kursi wajib diisi.");
        }
        this.nomor = nomor.trim();
        this.kelas = kelas;
    }

    public String getNomor() { return nomor; }
    public KelasPenerbangan getKelas() { return kelas; }
}
