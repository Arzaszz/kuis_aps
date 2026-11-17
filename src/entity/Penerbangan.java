package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Penerbangan {
    private final String nomorPenerbangan;
    private final Maskapai maskapai;
    private final Bandara asal;
    private final Bandara tujuan;
    private final LocalDateTime waktuBerangkat;
    private final LocalDateTime waktuTiba;
    private final BigDecimal harga;
    private int kursiTersedia;

    public Penerbangan(String nomorPenerbangan, Maskapai maskapai, Bandara asal, Bandara tujuan,
            LocalDateTime waktuBerangkat, LocalDateTime waktuTiba, BigDecimal harga,
            int kursiTersedia) {
        if (nomorPenerbangan == null || nomorPenerbangan.trim().isEmpty()
                || maskapai == null || asal == null || tujuan == null
                || waktuBerangkat == null || waktuTiba == null
                || !waktuTiba.isAfter(waktuBerangkat)
                || harga == null || harga.signum() < 0 || kursiTersedia < 0) {
            throw new IllegalArgumentException("Data penerbangan tidak valid.");
        }
        this.nomorPenerbangan = nomorPenerbangan.trim();
        this.maskapai = maskapai;
        this.asal = asal;
        this.tujuan = tujuan;
        this.waktuBerangkat = waktuBerangkat;
        this.waktuTiba = waktuTiba;
        this.harga = harga;
        this.kursiTersedia = kursiTersedia;
    }

    public void pesanKursi() {
        if (kursiTersedia == 0) throw new IllegalStateException("Kursi penerbangan sudah habis.");
        kursiTersedia--;
    }

    public void kembalikanKursi() { kursiTersedia++; }

    public String getNomorPenerbangan() { return nomorPenerbangan; }
    public Maskapai getMaskapai() { return maskapai; }
    public Bandara getAsal() { return asal; }
    public Bandara getTujuan() { return tujuan; }
    public LocalDateTime getWaktuBerangkat() { return waktuBerangkat; }
    public LocalDateTime getWaktuTiba() { return waktuTiba; }
    public BigDecimal getHarga() { return harga; }
    public int getKursiTersedia() { return kursiTersedia; }
}
