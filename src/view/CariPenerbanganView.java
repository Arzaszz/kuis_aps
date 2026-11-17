package view;

import entity.Penerbangan;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CariPenerbanganView {
    private static final DateTimeFormatter WAKTU = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final NumberFormat RUPIAH =
            NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

    public void tampilkan(List<Penerbangan> penerbangan) {
        if (penerbangan.isEmpty()) {
            System.out.println("Tidak ada penerbangan yang sesuai dengan pencarian.");
            return;
        }
        System.out.println("\nPenerbangan tersedia (diurutkan dari harga terendah):");
        System.out.printf("%-4s %-10s %-22s %-22s %-22s %-18s %-8s%n",
                "No", "Nomor", "Maskapai", "Asal", "Tujuan", "Berangkat", "Kursi");
        for (int i = 0; i < penerbangan.size(); i++) {
            Penerbangan p = penerbangan.get(i);
            System.out.printf("%-4d %-10s %-22s %-22s %-22s %-18s %-8d %s%n",
                    i + 1, p.getNomorPenerbangan(), p.getMaskapai().getNama(),
                    p.getAsal().getKota() + " (" + p.getAsal().getKode() + ")",
                    p.getTujuan().getKota() + " (" + p.getTujuan().getKode() + ")",
                    p.getWaktuBerangkat().format(WAKTU), p.getKursiTersedia(),
                    RUPIAH.format(p.getHarga()));
        }
    }
}
