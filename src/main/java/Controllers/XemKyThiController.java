package Controllers;

import Models.KyThi;
import Repository.KyThiRepository;

import java.util.List;

public class XemKyThiController {

    private final KyThiRepository repo = new KyThiRepository();

    public List<KyThi> kyThiCuaSinhVien(int maSinhvien) {
        return repo.findByMaSinhVien(maSinhvien);
    }
}

