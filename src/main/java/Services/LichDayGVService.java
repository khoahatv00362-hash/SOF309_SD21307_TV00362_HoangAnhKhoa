package Services;

import Models.LichDayGiangVien;
import Repository.LichDayGVRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LichDayGVService {

    private final LichDayGVRepository repo = new LichDayGVRepository();

    public boolean insert(LichDayGiangVien lh) {
        return repo.insert(lh);
    }

    public boolean update(LichDayGiangVien lh) {
        return repo.update(lh);
    }

    public boolean delete(int maLichday) {
        return repo.delete(maLichday);
    }

    public boolean syncLichHocFromLichDay(int maLichhoc, Integer maPhong, Integer maCa, LocalDate ngay) {
        return repo.syncLichHocFromLichDay(maLichhoc, maPhong, maCa, ngay);
    }

    public boolean trungLichGV(int maGiangvien, LocalDate ngay, Integer maCa, LocalTime gioBatdau, LocalTime gioKetthuc, int currentMaLichday) {
        return repo.trungLichGV(maGiangvien, ngay, maCa, gioBatdau, gioKetthuc, currentMaLichday);
    }

    public LichDayGiangVien findById(int maLichday) {
        return repo.findById(maLichday);
    }

    public List<LichDayGiangVien> findByMaGiangVien(int maGiangvien) {
        return repo.findByMaGiangVien(maGiangvien);
    }

    public List<LichDayGiangVien> findByGVAndDateRange(int maGiangvien, LocalDate tu, LocalDate den) {
        return repo.findByGVAndDateRange(maGiangvien, tu, den);
    }

    public int layMaGiangVienByNguoiDung(int maNguoidung) {
        return repo.findMaGiangVienByMaNguoiDung(maNguoidung);
    }
}

