package entity;

public class Pesawat {
    private final String kode;
    private final String model;
    private final int kapasitas;

    public Pesawat(String kode, String model, int kapasitas) {
        if (kode == null || kode.trim().isEmpty() || model == null || model.trim().isEmpty()
                || kapasitas <= 0) {
            throw new IllegalArgumentException("Data pesawat tidak valid.");
        }
        this.kode = kode.trim();
        this.model = model.trim();
        this.kapasitas = kapasitas;
    }

    public String getKode() { return kode; }
    public String getModel() { return model; }
    public int getKapasitas() { return kapasitas; }
}
