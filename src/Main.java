import control.CutiController;
import control.DataStore;
import control.MemberController;
import control.OtorisasiController;
import control.PemesananController;
import control.PenerbanganController;
import entity.Cuti;
import entity.Member;
import entity.Pemesanan;
import entity.Penerbangan;
import entity.Penumpang;
import entity.StatusPemesanan;
import entity.Tiket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import view.CariPenerbanganView;
import view.CutiView;
import view.OtorisasiView;
import view.PemesananView;
import view.ProfilView;

public class Main {
    private final Scanner input = new Scanner(System.in);
    private final DataStore data = new DataStore();
    private final PenerbanganController penerbanganController = new PenerbanganController(data);
    private final PemesananController pemesananController = new PemesananController(data);
    private final MemberController memberController = new MemberController(data);
    private final OtorisasiController otorisasiController = new OtorisasiController(data);
    private final CutiController cutiController = new CutiController(data);
    private final CariPenerbanganView penerbanganView = new CariPenerbanganView();
    private final PemesananView pemesananView = new PemesananView();
    private final ProfilView profilView = new ProfilView();
    private final OtorisasiView otorisasiView = new OtorisasiView();
    private final CutiView cutiView = new CutiView();

    public static void main(String[] args) {
        new Main().jalankan();
    }

    private void jalankan() {
        System.out.println("Sistem Pemesanan Penerbangan - data contoh disimpan sementara di memori.");
        System.out.println("Member contoh: M-001 (Ayu Lestari, ayu@example.com).");
        while (true) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Cari dan pesan penerbangan");
            System.out.println("2. Profil member dan validasi tiket");
            System.out.println("3. Lihat atau batalkan pemesanan");
            System.out.println("4. Jadwal dan riwayat cuti (HRD)");
            System.out.println("5. Otorisasi booking (manajer travel)");
            System.out.println("0. Keluar");
            String pilihan = baca("Pilih menu: ");
            if ("0".equals(pilihan)) return;
            try {
                if ("1".equals(pilihan)) cariDanPesan();
                else if ("2".equals(pilihan)) kelolaProfil();
                else if ("3".equals(pilihan)) kelolaPemesanan();
                else if ("4".equals(pilihan)) kelolaCuti();
                else if ("5".equals(pilihan)) otorisasiBooking();
                else System.out.println("Pilihan menu tidak dikenal.");
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Tidak dapat memproses permintaan: " + ex.getMessage());
            } catch (DateTimeParseException ex) {
                System.out.println("Format tanggal/jam tidak valid. Gunakan yyyy-MM-dd dan HH:mm.");
            }
        }
    }

    private void cariDanPesan() {
        boolean multiDestinasi = "2".equals(baca("Jenis pencarian (1 satu tujuan, 2 multi-destinasi): "));
        String asal = baca("Kota/kode bandara asal (kosongkan untuk semua): ");
        LocalDate tanggal = bacaTanggal("Tanggal berangkat yyyy-MM-dd (kosongkan untuk semua): ");
        LocalTime jam = bacaJam("Berangkat mulai HH:mm (kosongkan untuk semua): ");
        List<Penerbangan> hasil;
        if (multiDestinasi) {
            String[] tujuanInput = baca("Tujuan dipisahkan koma (kota atau kode): ").split(",");
            List<String> tujuan = new ArrayList<String>();
            for (String item : tujuanInput) if (!item.trim().isEmpty()) tujuan.add(item.trim());
            hasil = penerbanganController.cariMultiDestinasi(asal, tujuan, tanggal, jam);
        } else {
            String tujuan = baca("Kota/kode bandara tujuan (kosongkan untuk semua): ");
            hasil = penerbanganController.cari(asal, tujuan, tanggal, jam);
        }
        penerbanganView.tampilkan(hasil);
        if (hasil.isEmpty()) return;
        int nomor = bacaInt("Nomor penerbangan yang ingin dipesan (0 untuk kembali): ");
        if (nomor == 0) return;
        if (nomor < 1 || nomor > hasil.size()) {
            System.out.println("Nomor penerbangan tidak tersedia.");
            return;
        }

        String jenisPemesan = baca("Pesan sebagai (1 member, 2 pengunjung/atas nama orang lain): ");
        String idMember = null;
        Penumpang penumpang;
        if ("1".equals(jenisPemesan)) {
            String id = baca("ID member [M-001]: ");
            if (id.trim().isEmpty()) id = "M-001";
            Member member = memberController.cariMember(id);
            if (!member.profilLengkap()) {
                throw new IllegalStateException("Lengkapi profil member sebelum memesan tiket.");
            }
            idMember = member.getId();
            penumpang = new Penumpang(member.getNama(), member.getEmail(),
                    member.getTelepon(), member.getNomorIdentitas());
        } else if ("2".equals(jenisPemesan)) {
            penumpang = new Penumpang(baca("Nama pengunjung: "), baca("Email pengunjung: "),
                    baca("Telepon (opsional): "), baca("Nomor identitas: "));
        } else {
            System.out.println("Jenis pemesan tidak dikenal.");
            return;
        }
        Pemesanan pemesanan = pemesananController.pesan(hasil.get(nomor - 1), penumpang, idMember);
        pemesananView.tampilkanEtiket(pemesanan);
    }

    private void kelolaProfil() {
        String id = baca("ID member [M-001]: ");
        if (id.trim().isEmpty()) id = "M-001";
        Member member = memberController.cariMember(id);
        profilView.tampilkan(member);
        if ("y".equalsIgnoreCase(baca("Perbarui profil? (y/n): "))) {
            memberController.perbaruiProfil(id, baca("Nama lengkap: "), baca("Email: "),
                    baca("Telepon: "), baca("Nomor identitas: "));
            profilView.tampilkan(member);
        }
        if ("y".equalsIgnoreCase(baca("Validasi tiket? (y/n): "))) {
            String kode = baca("Kode booking: ");
            String nomorTiket = baca("Nomor tiket: ");
            Tiket tiket = memberController.validasiTiket(id, kode, nomorTiket);
            profilView.tampilkanHasilValidasi(tiket);
        }
    }

    private void kelolaPemesanan() {
        String email = baca("Email pemesan: ");
        List<Pemesanan> pemesanan = pemesananController.cari(email);
        pemesananView.tampilkan(pemesanan);
        if (pemesanan.isEmpty()) return;
        if ("y".equalsIgnoreCase(baca("Batalkan salah satu pemesanan? (y/n): "))) {
            String kode = baca("Kode booking yang dibatalkan: ");
            Pemesanan batal = pemesananController.batalkan(kode, email, false);
            System.out.println("Pemesanan " + batal.getKodeBooking() + " berhasil dibatalkan.");
        }
    }

    private void kelolaCuti() {
        System.out.println("1. Lihat pengajuan pada periode");
        System.out.println("2. Lihat riwayat dan sisa cuti pegawai");
        String pilihan = baca("Pilih: ");
        if ("1".equals(pilihan)) {
            LocalDate mulai = bacaTanggalWajib("Tanggal mulai yyyy-MM-dd: ");
            LocalDate selesai = bacaTanggalWajib("Tanggal selesai yyyy-MM-dd: ");
            List<Cuti> hasil = cutiController.cariPeriode(mulai, selesai);
            cutiView.tampilkanPeriode(hasil);
        } else if ("2".equals(pilihan)) {
            String pegawai = baca("ID atau nama pegawai (contoh PG-001): ");
            List<Cuti> riwayat = cutiController.riwayat(pegawai);
            int sisa = cutiController.sisaCutiTahunBerjalan(pegawai, LocalDate.now().getYear());
            cutiView.tampilkanRiwayat(riwayat, sisa, LocalDate.now().getYear());
        } else {
            System.out.println("Pilihan tidak dikenal.");
        }
    }

    private void otorisasiBooking() {
        otorisasiView.tampilkanPilihanStatus();
        StatusPemesanan filter = otorisasiView.parseStatus(baca("Tampilkan status: "));
        otorisasiView.tampilkan(otorisasiController.semuaPemesanan(filter));
        if ("y".equalsIgnoreCase(baca("Periksa kode booking reference? (y/n): "))) {
            Pemesanan pemesanan = otorisasiController.otorisasi(baca("Kode booking reference: "));
            System.out.println("Status booking: " + pemesanan.getStatus().getLabel());
            pemesananView.tampilkan(pemesanan);
        }
        if ("y".equalsIgnoreCase(baca("Perbarui status booking? (y/n): "))) {
            String kode = baca("Kode booking reference: ");
            otorisasiView.tampilkanPilihanStatus();
            StatusPemesanan status = otorisasiView.parseStatus(baca("Status baru: "));
            if (status == null) throw new IllegalArgumentException("Pilih status tertentu.");
            Pemesanan diperbarui = pemesananController.ubahStatus(kode, status);
            System.out.println("Status diperbarui: " + diperbarui.getStatus().getLabel());
        }
    }

    private String baca(String prompt) {
        System.out.print(prompt);
        return input.hasNextLine() ? input.nextLine().trim() : "0";
    }

    private int bacaInt(String prompt) {
        try {
            return Integer.parseInt(baca(prompt));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Masukkan nomor yang valid.");
        }
    }

    private LocalDate bacaTanggal(String prompt) {
        String nilai = baca(prompt);
        return nilai.isEmpty() ? null : LocalDate.parse(nilai);
    }

    private LocalDate bacaTanggalWajib(String prompt) {
        String nilai = baca(prompt);
        if (nilai.isEmpty()) throw new IllegalArgumentException("Tanggal wajib diisi.");
        return LocalDate.parse(nilai);
    }

    private LocalTime bacaJam(String prompt) {
        String nilai = baca(prompt);
        return nilai.isEmpty() ? null : LocalTime.parse(nilai);
    }
}
