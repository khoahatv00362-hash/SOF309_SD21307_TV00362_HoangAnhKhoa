package Controllers;

import Repository.GiangVienPortalRepository;
import Repository.GiangVienPortalRepository.ThongBaoRow;

import java.util.List;

public class XemThongBaoController {

    private final GiangVienPortalRepository repo = new GiangVienPortalRepository();

    public List<ThongBaoRow> thongBaoCuaToi(int maNguoidung) {
        return repo.thongBaoCuaToi(maNguoidung);
    }
}

