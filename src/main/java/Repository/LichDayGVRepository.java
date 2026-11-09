package Repository;

import Models.LichDayGiangVien;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LichDayGVRepository {

    public boolean insert(LichDayGiangVien lh) {
        return false;
    }

    public boolean update(LichDayGiangVien lh) {
        return false;
    }

    public boolean delete(int maLichday) {
        return false;
    }

    public boolean syncLichHocFromLichDay(int maLichhoc, Integer maPhong, Integer maCa, LocalDate ngay) {
        return false;
    }

    public boolean trungLichGV(int maGiangvien, LocalDate ngay, Integer maCa, LocalTime gioBatdau, LocalTime gioKetthuc, int currentMaLichday) {
        return false;
    }

    public LichDayGiangVien findById(int maLichday) {
        return null;
    }

    public List<LichDayGiangVien> findByMaGiangVien(int maGiangvien) {
        return new ArrayList<>();
    }

    public List<LichDayGiangVien> findByGVAndDateRange(int maGiangvien, LocalDate tu, LocalDate den) {
        return new ArrayList<>();
    }

    public int findMaGiangVienByMaNguoiDung(int maNguoidung) {
        return 0;
    }
}

