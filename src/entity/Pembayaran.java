package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pembayaran {
    private final String id;
    private final String referensiPemesanan;
    private final BigDecimal jumlah;
    private final LocalDateTime waktuPembayaran;
    private String status;

    public Pembayaran(String id, String referensiPemesanan, BigDecimal jumlah,
            LocalDateTime waktuPembayaran, String status) {
        if (jumlah == null || jumlah.signum() < 0 || waktuPembayaran == null) {
            throw new IllegalArgumentException("Jumlah dan waktu pembayaran tidak valid.");
        }
        this.id = wajib(id, "ID pembayaran");
        this.referensiPemesanan = wajib(referensiPemesanan, "Referensi pemesanan");
        this.jumlah = jumlah;
        this.waktuPembayaran = waktuPembayaran;
        this.status = wajib(status, "Status pembayaran");
    }

    private static String wajib(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " wajib diisi.");
        }
        return nilai.trim();
    }

    public String getId() { return id; }
    public String getReferensiPemesanan() { return referensiPemesanan; }
    public BigDecimal getJumlah() { return jumlah; }
    public LocalDateTime getWaktuPembayaran() { return waktuPembayaran; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = wajib(status, "Status pembayaran"); }
}
