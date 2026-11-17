package control;

import entity.Pemesanan;
import entity.StatusPemesanan;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtorisasiController {
    private final DataStore data;

    public OtorisasiController(DataStore data) { this.data = data; }

    public List<Pemesanan> semuaPemesanan(StatusPemesanan filter) {
        List<Pemesanan> hasil = new ArrayList<Pemesanan>();
        for (Pemesanan pemesanan : data.getPemesanan()) {
            if (filter == null || pemesanan.getStatus() == filter) hasil.add(pemesanan);
        }
        return Collections.unmodifiableList(hasil);
    }

    public Pemesanan otorisasi(String kodeBooking) {
        if (kodeBooking == null || kodeBooking.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode booking reference wajib diisi.");
        }
        Pemesanan pemesanan = data.cariPemesanan(kodeBooking);
        if (pemesanan == null) {
            throw new IllegalArgumentException("Kode booking reference tidak sesuai.");
        }
        return pemesanan;
    }
}
