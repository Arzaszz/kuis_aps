package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Pemesanan {
    private final String kodeBooking;
    private final String namaPemesan;
    private final String email;
    private final String idMember;
    private final LocalDateTime waktuPemesanan;
    private final List<Tiket> tiket = new ArrayList<Tiket>();
    private StatusPemesanan status;

    public Pemesanan(String namaPemesan, String email, String idMember) {
        this.kodeBooking = "BK-" + UUID.randomUUID().toString()
                .replace("-", "").substring(0, 8).toUpperCase();
        this.namaPemesan = wajib(namaPemesan, "Nama pemesan");
        this.email = wajib(email, "Email pemesan");
        this.idMember = idMember == null ? "" : idMember.trim();
        this.waktuPemesanan = LocalDateTime.now();
        this.status = StatusPemesanan.BOOKED;
    }

    public void tambahTiket(Tiket tiketBaru) {
        if (tiketBaru == null) {
            throw new IllegalArgumentException("Tiket wajib diisi.");
        }
        tiket.add(tiketBaru);
    }

    public void batalkan() {
        if (status == StatusPemesanan.CANCELLED) {
            throw new IllegalStateException("Pemesanan sudah dibatalkan.");
        }
        status = StatusPemesanan.CANCELLED;
    }

    private static String wajib(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " wajib diisi.");
        }
        return nilai.trim();
    }

    public String getKodeBooking() { return kodeBooking; }
    public String getNamaPemesan() { return namaPemesan; }
    public String getEmail() { return email; }
    public String getIdMember() { return idMember; }
    public LocalDateTime getWaktuPemesanan() { return waktuPemesanan; }
    public List<Tiket> getTiket() { return Collections.unmodifiableList(tiket); }
    public StatusPemesanan getStatus() { return status; }
    public void setStatus(StatusPemesanan status) {
        if (status == null) throw new IllegalArgumentException("Status pemesanan wajib diisi.");
        this.status = status;
    }
}
