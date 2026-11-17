package entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Tiket {
    private final String nomorTiket;
    private final String kodeBooking;
    private final Penerbangan penerbangan;
    private final Penumpang penumpang;
    private final LocalDateTime waktuTerbit;
    private boolean tervalidasi;

    public Tiket(String kodeBooking, Penerbangan penerbangan, Penumpang penumpang) {
        if (kodeBooking == null || kodeBooking.trim().isEmpty()
                || penerbangan == null || penumpang == null) {
            throw new IllegalArgumentException("Data tiket tidak valid.");
        }
        this.nomorTiket = "TK-" + UUID.randomUUID().toString()
                .replace("-", "").substring(0, 8).toUpperCase();
        this.kodeBooking = kodeBooking.trim();
        this.penerbangan = penerbangan;
        this.penumpang = penumpang;
        this.waktuTerbit = LocalDateTime.now();
    }

    public void validasi() { tervalidasi = true; }

    public String getNomorTiket() { return nomorTiket; }
    public String getKodeBooking() { return kodeBooking; }
    public Penerbangan getPenerbangan() { return penerbangan; }
    public Penumpang getPenumpang() { return penumpang; }
    public LocalDateTime getWaktuTerbit() { return waktuTerbit; }
    public boolean isTervalidasi() { return tervalidasi; }
}
