package entity;

public class Pegawai {
    private final String id;
    private final String nama;
    private final int jatahCutiTahunan;

    public Pegawai(String id, String nama, int jatahCutiTahunan) {
        if (id == null || id.trim().isEmpty() || nama == null || nama.trim().isEmpty()
                || jatahCutiTahunan < 0) {
            throw new IllegalArgumentException("Data pegawai tidak valid.");
        }
        this.id = id.trim();
        this.nama = nama.trim();
        this.jatahCutiTahunan = jatahCutiTahunan;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public int getJatahCutiTahunan() { return jatahCutiTahunan; }
}
