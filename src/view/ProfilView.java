package view;

import entity.Member;
import entity.Tiket;

public class ProfilView {
    public void tampilkan(Member member) {
        System.out.println("\nProfil member " + member.getId());
        System.out.println("Nama           : " + nilai(member.getNama()));
        System.out.println("Email          : " + nilai(member.getEmail()));
        System.out.println("Telepon        : " + nilai(member.getTelepon()));
        System.out.println("Nomor identitas: " + nilai(member.getNomorIdentitas()));
        System.out.println("Kelengkapan    : " + (member.profilLengkap() ? "lengkap" : "belum lengkap"));
    }

    public void tampilkanHasilValidasi(Tiket tiket) {
        System.out.println("Tiket " + tiket.getNomorTiket() + " berhasil divalidasi untuk member.");
    }

    private String nilai(String nilai) {
        return nilai == null || nilai.trim().isEmpty() ? "(belum diisi)" : nilai;
    }
}
