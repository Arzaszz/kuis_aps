package view;

import entity.Pemesanan;
import entity.StatusPemesanan;
import java.util.List;

public class OtorisasiView {
    public void tampilkan(List<Pemesanan> pemesanan) {
        if (pemesanan.isEmpty()) {
            System.out.println("Tidak ada pemesanan untuk ditampilkan.");
            return;
        }
        for (Pemesanan item : pemesanan) {
            System.out.printf("%s | %-22s | %-32s | %s%n",
                    item.getKodeBooking(), item.getNamaPemesan(), item.getEmail(),
                    item.getStatus().getLabel());
        }
    }

    public void tampilkanPilihanStatus() {
        System.out.println("Status: semua, confirmed, booked, waiting for the payment, cancelled");
    }

    public StatusPemesanan parseStatus(String pilihan) {
        if (pilihan == null || pilihan.trim().isEmpty()
                || pilihan.trim().equalsIgnoreCase("semua")) return null;
        return StatusPemesanan.fromLabel(pilihan);
    }
}
