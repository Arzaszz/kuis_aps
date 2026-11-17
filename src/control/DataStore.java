package control;

import entity.Bandara;
import entity.Cuti;
import entity.Maskapai;
import entity.Member;
import entity.Pegawai;
import entity.Pemesanan;
import entity.Penerbangan;
import entity.Penumpang;
import entity.StatusPemesanan;
import entity.Tiket;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataStore {
    private final List<Penerbangan> penerbangan = new ArrayList<Penerbangan>();
    private final List<Pemesanan> pemesanan = new ArrayList<Pemesanan>();
    private final List<Member> member = new ArrayList<Member>();
    private final List<Pegawai> pegawai = new ArrayList<Pegawai>();
    private final List<Cuti> cuti = new ArrayList<Cuti>();

    public DataStore() {
        isiDataContoh();
    }

    private void isiDataContoh() {
        Bandara cgk = new Bandara("CGK", "Soekarno-Hatta", "Jakarta");
        Bandara dps = new Bandara("DPS", "Ngurah Rai", "Denpasar");
        Bandara juanda = new Bandara("SUB", "Juanda", "Surabaya");
        Bandara kualanamu = new Bandara("KNO", "Kualanamu", "Medan");
        Maskapai garuda = new Maskapai("GA", "Garuda Indonesia");
        Maskapai lion = new Maskapai("JT", "Lion Air");
        Maskapai citilink = new Maskapai("QG", "Citilink");
        LocalDate besok = LocalDate.now().plusDays(1);
        tambahPenerbangan(new Penerbangan("GA-401", garuda, cgk, dps,
                besok.atTime(8, 0), besok.atTime(11, 0), new BigDecimal("1450000"), 30));
        tambahPenerbangan(new Penerbangan("JT-610", lion, cgk, dps,
                besok.atTime(10, 30), besok.atTime(13, 30), new BigDecimal("920000"), 45));
        tambahPenerbangan(new Penerbangan("QG-680", citilink, cgk, juanda,
                besok.atTime(9, 15), besok.atTime(10, 50), new BigDecimal("780000"), 25));
        tambahPenerbangan(new Penerbangan("GA-411", garuda, dps, juanda,
                besok.atTime(14, 0), besok.atTime(15, 0), new BigDecimal("830000"), 18));
        tambahPenerbangan(new Penerbangan("JT-202", lion, cgk, kualanamu,
                besok.plusDays(1).atTime(7, 45), besok.plusDays(1).atTime(10, 10),
                new BigDecimal("1100000"), 20));

        tambahMember(new Member("M-001", "Ayu Lestari", "ayu@example.com",
                "081234567890", "3175010101900001"));

        Pegawai rina = new Pegawai("PG-001", "Rina Putri", 12);
        Pegawai budi = new Pegawai("PG-002", "Budi Santoso", 12);
        tambahPegawai(rina);
        tambahPegawai(budi);
        tambahCuti(new Cuti("CUT-001", rina, besok, besok.plusDays(2), "MENUNGGU"));
        tambahCuti(new Cuti("CUT-002", budi, besok.plusDays(4), besok.plusDays(5), "DISETUJUI"));
        tambahCuti(new Cuti("CUT-003", rina, LocalDate.now().minusMonths(2),
                LocalDate.now().minusMonths(2).plusDays(1), "DISETUJUI"));
    }

    public List<Penerbangan> getPenerbangan() {
        return Collections.unmodifiableList(penerbangan);
    }

    public List<Pemesanan> getPemesanan() {
        return Collections.unmodifiableList(pemesanan);
    }

    public List<Member> getMember() {
        return Collections.unmodifiableList(member);
    }

    public List<Pegawai> getPegawai() {
        return Collections.unmodifiableList(pegawai);
    }

    public List<Cuti> getCuti() {
        return Collections.unmodifiableList(cuti);
    }

    public void tambahPemesanan(Pemesanan nilai) { pemesanan.add(nilai); }
    public void tambahMember(Member nilai) { member.add(nilai); }
    public void tambahPegawai(Pegawai nilai) { pegawai.add(nilai); }
    public void tambahCuti(Cuti nilai) { cuti.add(nilai); }
    public void tambahPenerbangan(Penerbangan nilai) { penerbangan.add(nilai); }

    public Member cariMember(String id) {
        for (Member nilai : member) {
            if (nilai.getId().equalsIgnoreCase(id)) return nilai;
        }
        return null;
    }

    public Pegawai cariPegawai(String idOrNama) {
        for (Pegawai nilai : pegawai) {
            if (nilai.getId().equalsIgnoreCase(idOrNama)
                    || nilai.getNama().equalsIgnoreCase(idOrNama)) return nilai;
        }
        return null;
    }

    public Pemesanan cariPemesanan(String kodeBooking) {
        for (Pemesanan nilai : pemesanan) {
            if (nilai.getKodeBooking().equalsIgnoreCase(kodeBooking)) return nilai;
        }
        return null;
    }
}
