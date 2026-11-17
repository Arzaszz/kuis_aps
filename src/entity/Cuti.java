package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Cuti {
    private final String id;
    private final Pegawai pegawai;
    private final LocalDate tanggalMulai;
    private final LocalDate tanggalSelesai;
    private String status;

    public Cuti(String id, Pegawai pegawai, LocalDate tanggalMulai,
            LocalDate tanggalSelesai, String status) {
        if (tanggalMulai == null || tanggalSelesai == null
                || tanggalSelesai.isBefore(tanggalMulai)) {
            throw new IllegalArgumentException("Periode cuti tidak valid.");
        }
        this.id = wajib(id, "ID cuti");
        this.pegawai = pegawai;
        if (pegawai == null) {
            throw new IllegalArgumentException("Pegawai wajib diisi.");
        }
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.status = wajib(status, "Status cuti");
    }

    private static String wajib(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " wajib diisi.");
        }
        return nilai.trim();
    }

    public long getDurasiHari() {
        return ChronoUnit.DAYS.between(tanggalMulai, tanggalSelesai) + 1;
    }

    public boolean beririsan(LocalDate mulai, LocalDate selesai) {
        return !tanggalMulai.isAfter(selesai) && !tanggalSelesai.isBefore(mulai);
    }

    public String getId() { return id; }
    public Pegawai getPegawai() { return pegawai; }
    public LocalDate getTanggalMulai() { return tanggalMulai; }
    public LocalDate getTanggalSelesai() { return tanggalSelesai; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = wajib(status, "Status cuti"); }
}
