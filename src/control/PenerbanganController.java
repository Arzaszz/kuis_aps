package control;

import entity.Penerbangan;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PenerbanganController {
    private final DataStore data;

    public PenerbanganController(DataStore data) { this.data = data; }

    public List<Penerbangan> cari(String asal, String tujuan, LocalDate tanggal, LocalTime mulaiJam) {
        List<Penerbangan> hasil = new ArrayList<Penerbangan>();
        for (Penerbangan penerbangan : data.getPenerbangan()) {
            if (penerbangan.getKursiTersedia() > 0
                    && cocok(penerbangan.getAsal().getKode(), penerbangan.getAsal().getKota(),
                            penerbangan.getAsal().getNama(), asal)
                    && cocok(penerbangan.getTujuan().getKode(), penerbangan.getTujuan().getKota(),
                            penerbangan.getTujuan().getNama(), tujuan)
                    && (tanggal == null || penerbangan.getWaktuBerangkat().toLocalDate().equals(tanggal))
                    && (mulaiJam == null || !penerbangan.getWaktuBerangkat().toLocalTime().isBefore(mulaiJam))) {
                hasil.add(penerbangan);
            }
        }
        Collections.sort(hasil, Comparator.comparing(Penerbangan::getHarga)
                .thenComparing(Penerbangan::getWaktuBerangkat));
        return Collections.unmodifiableList(hasil);
    }

    public List<Penerbangan> cariMultiDestinasi(String asal, List<String> tujuan,
            LocalDate tanggal, LocalTime mulaiJam) {
        if (tujuan == null || tujuan.isEmpty()) {
            throw new IllegalArgumentException("Masukkan setidaknya satu tujuan.");
        }
        List<Penerbangan> hasil = new ArrayList<Penerbangan>();
        for (Penerbangan penerbangan : data.getPenerbangan()) {
            if (penerbangan.getKursiTersedia() == 0
                    || !cocok(penerbangan.getAsal().getKode(), penerbangan.getAsal().getKota(),
                            penerbangan.getAsal().getNama(), asal)
                    || (tanggal != null && !penerbangan.getWaktuBerangkat().toLocalDate().equals(tanggal))
                    || (mulaiJam != null && penerbangan.getWaktuBerangkat().toLocalTime().isBefore(mulaiJam))) {
                continue;
            }
            for (String tujuanPilihan : tujuan) {
                if (cocok(penerbangan.getTujuan().getKode(), penerbangan.getTujuan().getKota(),
                        penerbangan.getTujuan().getNama(), tujuanPilihan)) {
                    hasil.add(penerbangan);
                    break;
                }
            }
        }
        Collections.sort(hasil, Comparator.comparing(Penerbangan::getHarga)
                .thenComparing(Penerbangan::getWaktuBerangkat));
        return Collections.unmodifiableList(hasil);
    }

    private boolean cocok(String kode, String kota, String nama, String kriteria) {
        if (kriteria == null || kriteria.trim().isEmpty()) return true;
        String nilai = kriteria.trim();
        return kode.equalsIgnoreCase(nilai) || kota.equalsIgnoreCase(nilai)
                || nama.equalsIgnoreCase(nilai);
    }
}
