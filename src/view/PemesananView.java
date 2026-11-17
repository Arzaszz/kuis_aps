package view;

import entity.Pemesanan;
import entity.Tiket;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PemesananView {
    private static final DateTimeFormatter WAKTU = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final NumberFormat RUPIAH =
            NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

    public void tampilkan(List<Pemesanan> pemesanan) {
        if (pemesanan.isEmpty()) {
            System.out.println("Belum ada pemesanan.");
            return;
        }
        for (Pemesanan item : pemesanan) tampilkan(item);
    }

    public void tampilkan(Pemesanan pemesanan) {
        System.out.printf("%s | %-22s | %-32s | status: %s%n",
                pemesanan.getKodeBooking(), pemesanan.getNamaPemesan(), pemesanan.getEmail(),
                pemesanan.getStatus().getLabel());
        for (Tiket tiket : pemesanan.getTiket()) {
            System.out.printf("  Tiket %s | %s | %s -> %s | %s | %s | validasi: %s%n",
                    tiket.getNomorTiket(), tiket.getPenerbangan().getNomorPenerbangan(),
                    tiket.getPenerbangan().getAsal().getKota(),
                    tiket.getPenerbangan().getTujuan().getKota(),
                    tiket.getPenerbangan().getWaktuBerangkat().format(WAKTU),
                    RUPIAH.format(tiket.getPenerbangan().getHarga()),
                    tiket.isTervalidasi() ? "valid" : "belum");
        }
    }

    public void tampilkanEtiket(Pemesanan pemesanan) {
        System.out.println("\nE-TIKET");
        tampilkan(pemesanan);
        System.out.println("Simulasi email: e-tiket ditampilkan untuk " + pemesanan.getEmail()
                + " (belum dikirim melalui SMTP).");
    }
}
