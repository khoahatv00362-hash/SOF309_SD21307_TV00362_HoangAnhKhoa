package Controllers;

import Models.LichHoc;
import Repository.LichHocRepository;

import java.util.List;

public class XemLichHocController {

    private final LichHocRepository repo = new LichHocRepository();

    public List<LichHoc> lichHocCuaSinhVien(int maSinhvien) {
        return repo.findByMaSinhVien(maSinhvien);
    }
}

