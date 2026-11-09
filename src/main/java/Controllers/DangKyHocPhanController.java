package Controllers;

import Models.DangKyHocPhan;
import Repository.DangKyHocPhanRepository.LopHocPhanKhaDung;
import Repository.DangKyHocPhanRepository.LopHocPhanDaDangKy;
import Services.DangKyHocPhanService;

import java.util.List;

public class DangKyHocPhanController {

    private final DangKyHocPhanService service;

    public DangKyHocPhanController() {
        this.service = new DangKyHocPhanService();
    }

    public DangKyHocPhanController(DangKyHocPhanService service) {
        this.service = service;
    }

    public boolean dangKy(int maSinhvien, int maLophocphan) throws Exception {
        return service.dangKy(maSinhvien, maLophocphan);
    }

    public boolean huyDangKy(int maDangky) {
        return service.huyDangKy(maDangky);
    }

    public List<LopHocPhanKhaDung> dsLHPKhaDung(int maSinhvien) {
        return service.dsLHPKhaDung(maSinhvien);
    }

    public List<DangKyHocPhan> dsDangKyCuaSV(int maSinhvien) {
        return service.dsDangKyCuaSV(maSinhvien);
    }

    public List<LopHocPhanDaDangKy> dsDangKyChiTietCuaSV(int maSinhvien) {
        return service.dsDangKyChiTietCuaSV(maSinhvien);
    }

    public int layMaSinhVienByNguoiDung(int maNguoidung) {
        return service.layMaSinhVienByNguoiDung(maNguoidung);
    }
}

