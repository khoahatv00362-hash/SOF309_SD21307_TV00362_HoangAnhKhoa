package Services;

import Models.Quyen;
import Models.VaiTro;
import Repository.QuyenRepository;
import java.util.List;
import java.util.Map;

public class PhanQuyenService {

    private final QuyenRepository repo = new QuyenRepository();

    public List<VaiTro> dsVaiTro() {
        return repo.dsVaiTro();
    }

    public List<Quyen> dsVaiTroCuaNguoiDung(int maNguoidung) {
        return repo.dsVaiTroCuaNguoiDung(maNguoidung);
    }

    public Map<Integer, List<String>> tenVaiTroTheoNguoiDung() {
        return repo.tenVaiTroTheoNguoiDung();
    }

    public boolean ganVaiTro(int maNguoidung, int maVaitro) {
        return repo.ganVaiTro(maNguoidung, maVaitro);
    }

    public boolean goVaiTro(int maNguoidung, int maVaitro) {
        return repo.goVaiTro(maNguoidung, maVaitro);
    }
}

