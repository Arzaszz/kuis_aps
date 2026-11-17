package control;

import entity.Member;
import entity.Pemesanan;
import entity.Penumpang;
import entity.Tiket;

public class MemberController {
    private final DataStore data;

    public MemberController(DataStore data) { this.data = data; }

    public Member cariMember(String id) {
        Member member = data.cariMember(id);
        if (member == null) throw new IllegalArgumentException("Member tidak ditemukan.");
        return member;
    }

    public Member perbaruiProfil(String id, String nama, String email,
            String telepon, String nomorIdentitas) {
        Member member = cariMember(id);
        member.updateProfil(nama, email, telepon, nomorIdentitas);
        return member;
    }

    public Tiket validasiTiket(String idMember, String kodeBooking, String nomorTiket) {
        Member member = cariMember(idMember);
        if (!member.profilLengkap()) {
            throw new IllegalStateException("Data profil tidak lengkap.");
        }
        Pemesanan pemesanan = data.cariPemesanan(kodeBooking);
        if (pemesanan == null || !pemesanan.getIdMember().equalsIgnoreCase(member.getId())) {
            throw new IllegalArgumentException("Tiket tidak terdaftar pada akun member ini.");
        }
        for (Tiket tiket : pemesanan.getTiket()) {
            if (tiket.getNomorTiket().equalsIgnoreCase(nomorTiket)) {
                Penumpang penumpang = tiket.getPenumpang();
                boolean cocok = member.getNama().equalsIgnoreCase(penumpang.getNama())
                        && member.getEmail().equalsIgnoreCase(penumpang.getEmail())
                        && member.getTelepon().equalsIgnoreCase(penumpang.getTelepon())
                        && member.getNomorIdentitas().equalsIgnoreCase(penumpang.getNomorIdentitas());
                if (!cocok) throw new IllegalStateException("Data profil tidak sesuai dengan data tiket.");
                tiket.validasi();
                return tiket;
            }
        }
        throw new IllegalArgumentException("Nomor tiket tidak ditemukan pada pemesanan.");
    }
}
