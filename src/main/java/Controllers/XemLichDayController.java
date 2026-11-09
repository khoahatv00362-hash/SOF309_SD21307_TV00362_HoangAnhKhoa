package Controllers;

import Models.LichDayGiangVien;
import Repository.LichDayGVRepository;

import java.util.List;

public class XemLichDayController {

    private final LichDayGVRepository repo = new LichDayGVRepository();

    public List<LichDayGiangVien> lichDayCuaGV(int maGiangvien) {
        return repo.findByMaGiangVien(maGiangvien);
    }
    public int layMaGVByNguoiDung(int maNguoidung) {
        return repo.findMaGiangVienByMaNguoiDung(maNguoidung);
    }
}

