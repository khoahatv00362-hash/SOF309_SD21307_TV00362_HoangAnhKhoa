package Controllers;

import Models.TaiKhoan;
import Repository.TaiKhoanRepository;
import Utils.MatKhauUtil;

public class DangNhapController {

    private final TaiKhoanRepository repo;

    public DangNhapController() {
        this(new TaiKhoanRepository());
    }

    DangNhapController(TaiKhoanRepository repo) {
        this.repo = repo;
    }

    public TaiKhoan dangNhap(String email, String matKhauTho) {
        if (email == null || email.trim().isEmpty() || matKhauTho == null || matKhauTho.isEmpty()) {
            return null;
        }

        TaiKhoan tk = repo.findByEmail(email.trim());

        if (tk == null) {
            return null;
        }

        if (!tk.isHoatDong()) {
            return null;
        }

        String storedHash = tk.getMatKhau();
        if (!MatKhauUtil.khop(matKhauTho, storedHash)) {
            return null;
        }

        if (MatKhauUtil.canNangCap(storedHash)) {
            repo.capNhatMatKhau(tk.getMaNguoidung(), MatKhauUtil.bam(matKhauTho));
        }
        tk.setMatKhau(null);
        return tk;
    }

    public void doiMatKhau(int maNguoidung, String matKhauCu, String matKhauMoi, String nhapLaiMatKhauMoi) throws Exception {
        if (matKhauMoi == null || matKhauMoi.length() < 6) {
            throw new Exception("Mật khẩu mới phải có ít nhất 6 ký tự.");
        }
        if (!matKhauMoi.equals(nhapLaiMatKhauMoi)) {
            throw new Exception("Mật khẩu mới nhập lại không khớp.");
        }

        TaiKhoan tk = repo.findByMaNguoidung(maNguoidung);
        if (tk == null) {
            throw new Exception("Không tìm thấy tài khoản.");
        }

        if (tk.getMatKhau() != null && !tk.getMatKhau().isEmpty() && !MatKhauUtil.khop(matKhauCu, tk.getMatKhau())) {
            throw new Exception("Mật khẩu hiện tại không đúng.");
        }

        boolean ok = repo.capNhatMatKhau(maNguoidung, MatKhauUtil.bam(matKhauMoi));
        if (!ok) {
            throw new Exception("Cập nhật mật khẩu thất bại, vui lòng thử lại.");
        }
    }

    public void datLaiMatKhau(int maNguoidung, String matKhauMoi, String nhapLaiMatKhauMoi) throws Exception {
        if (matKhauMoi == null || matKhauMoi.length() < 6) {
            throw new Exception("Mật khẩu mới phải có ít nhất 6 ký tự.");
        }
        if (!matKhauMoi.equals(nhapLaiMatKhauMoi)) {
            throw new Exception("Mật khẩu mới nhập lại không khớp.");
        }
        if (!repo.capNhatMatKhau(maNguoidung, MatKhauUtil.bam(matKhauMoi))) {
            throw new Exception("Cập nhật mật khẩu thất bại, vui lòng thử lại.");
        }
    }
}

