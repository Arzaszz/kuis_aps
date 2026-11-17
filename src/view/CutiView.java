package view;

import entity.Cuti;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CutiView {
    private static final DateTimeFormatter TANGGAL = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void tampilkanPeriode(List<Cuti> pengajuan) {
        if (pengajuan.isEmpty()) {
            System.out.println("Tidak ada pengajuan cuti pada periode tersebut.");
            return;
        }
        for (Cuti cuti : pengajuan) {
            System.out.printf("%s | %-20s | %s s.d. %s | %d hari | %s%n",
                    cuti.getId(), cuti.getPegawai().getNama(),
                    cuti.getTanggalMulai().format(TANGGAL), cuti.getTanggalSelesai().format(TANGGAL),
                    cuti.getDurasiHari(), cuti.getStatus());
        }
    }

    public void tampilkanRiwayat(List<Cuti> riwayat, int sisaCuti, int tahun) {
        System.out.println("\nRiwayat cuti:");
        tampilkanPeriode(riwayat);
        System.out.println("Sisa cuti disetujui tahun " + tahun + ": " + sisaCuti + " hari.");
    }
}
