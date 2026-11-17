package control;

import entity.Cuti;
import entity.Pegawai;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CutiController {
    private final DataStore data;

    public CutiController(DataStore data) { this.data = data; }

    public List<Cuti> cariPeriode(LocalDate mulai, LocalDate selesai) {
        validasiPeriode(mulai, selesai);
        List<Cuti> hasil = new ArrayList<Cuti>();
        for (Cuti pengajuan : data.getCuti()) {
            if (pengajuan.beririsan(mulai, selesai)) hasil.add(pengajuan);
        }
        return Collections.unmodifiableList(hasil);
    }

    public List<Cuti> riwayat(String idAtauNamaPegawai) {
        Pegawai pegawai = data.cariPegawai(idAtauNamaPegawai);
        if (pegawai == null) throw new IllegalArgumentException("Pegawai tidak ditemukan.");
        List<Cuti> hasil = new ArrayList<Cuti>();
        for (Cuti pengajuan : data.getCuti()) {
            if (pengajuan.getPegawai().getId().equalsIgnoreCase(pegawai.getId())) hasil.add(pengajuan);
        }
        return Collections.unmodifiableList(hasil);
    }

    public int sisaCutiTahunBerjalan(String idAtauNamaPegawai, int tahun) {
        Pegawai pegawai = data.cariPegawai(idAtauNamaPegawai);
        if (pegawai == null) throw new IllegalArgumentException("Pegawai tidak ditemukan.");
        int terpakai = 0;
        LocalDate awalTahun = LocalDate.of(tahun, 1, 1);
        LocalDate akhirTahun = LocalDate.of(tahun, 12, 31);
        for (Cuti pengajuan : data.getCuti()) {
            if (pengajuan.getPegawai().getId().equalsIgnoreCase(pegawai.getId())
                    && pengajuan.getStatus().equalsIgnoreCase("DISETUJUI")
                    && !pengajuan.getTanggalMulai().isAfter(akhirTahun)
                    && !pengajuan.getTanggalSelesai().isBefore(awalTahun)) {
                LocalDate mulai = pengajuan.getTanggalMulai().isBefore(awalTahun)
                        ? awalTahun : pengajuan.getTanggalMulai();
                LocalDate selesai = pengajuan.getTanggalSelesai().isAfter(akhirTahun)
                        ? akhirTahun : pengajuan.getTanggalSelesai();
                if (!selesai.isBefore(mulai)) {
                    terpakai += (int) (selesai.toEpochDay() - mulai.toEpochDay() + 1);
                }
            }
        }
        return Math.max(0, pegawai.getJatahCutiTahunan() - terpakai);
    }

    private void validasiPeriode(LocalDate mulai, LocalDate selesai) {
        if (mulai == null || selesai == null || selesai.isBefore(mulai)) {
            throw new IllegalArgumentException("Periode tanggal tidak valid.");
        }
    }
}
