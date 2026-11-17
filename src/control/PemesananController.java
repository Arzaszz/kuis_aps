package control;

import entity.Pemesanan;
import entity.Penerbangan;
import entity.Penumpang;
import entity.StatusPemesanan;
import entity.Tiket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PemesananController {
    private final DataStore data;

    public PemesananController(DataStore data) { this.data = data; }

    public Pemesanan pesan(Penerbangan penerbangan, Penumpang penumpang, String idMember) {
        if (penerbangan == null || penumpang == null) {
            throw new IllegalArgumentException("Penerbangan dan data penumpang wajib dipilih.");
        }
        if (idMember != null && !idMember.trim().isEmpty()
                && data.cariMember(idMember) == null) {
            throw new IllegalArgumentException("Member tidak ditemukan.");
        }
        Pemesanan pemesanan = new Pemesanan(penumpang.getNama(), penumpang.getEmail(), idMember);
        penerbangan.pesanKursi();
        pemesanan.tambahTiket(new Tiket(pemesanan.getKodeBooking(), penerbangan, penumpang));
        data.tambahPemesanan(pemesanan);
        return pemesanan;
    }

    public List<Pemesanan> cari(String email) {
        List<Pemesanan> hasil = new ArrayList<Pemesanan>();
        for (Pemesanan pemesanan : data.getPemesanan()) {
            if (email == null || pemesanan.getEmail().equalsIgnoreCase(email.trim())) {
                hasil.add(pemesanan);
            }
        }
        return Collections.unmodifiableList(hasil);
    }

    public Pemesanan cariDenganKode(String kodeBooking) {
        Pemesanan pemesanan = data.cariPemesanan(kodeBooking);
        if (pemesanan == null) throw new IllegalArgumentException("Kode booking tidak ditemukan.");
        return pemesanan;
    }

    public Pemesanan batalkan(String kodeBooking, String email, boolean manajer) {
        Pemesanan pemesanan = cariDenganKode(kodeBooking);
        if (!manajer && (email == null || !pemesanan.getEmail().equalsIgnoreCase(email.trim()))) {
            throw new IllegalArgumentException("Pemesanan tidak ditemukan untuk akun tersebut.");
        }
        if (pemesanan.getStatus() == StatusPemesanan.CANCELLED) {
            throw new IllegalStateException("Pemesanan sudah dibatalkan.");
        }
        pemesanan.batalkan();
        for (Tiket tiket : pemesanan.getTiket()) tiket.getPenerbangan().kembalikanKursi();
        return pemesanan;
    }

    public Pemesanan ubahStatus(String kodeBooking, StatusPemesanan status) {
        if (status == null || status == StatusPemesanan.CANCELLED) {
            throw new IllegalArgumentException("Status baru tidak valid.");
        }
        Pemesanan pemesanan = cariDenganKode(kodeBooking);
        if (pemesanan.getStatus() == StatusPemesanan.CANCELLED) {
            throw new IllegalStateException("Pemesanan yang dibatalkan tidak dapat diubah.");
        }
        pemesanan.setStatus(status);
        return pemesanan;
    }
}
