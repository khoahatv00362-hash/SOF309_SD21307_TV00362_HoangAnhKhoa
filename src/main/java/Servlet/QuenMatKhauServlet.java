package Servlet;

import Controllers.DangNhapController;
import Models.TaiKhoan;
import Repository.TaiKhoanRepository;
import Services.OtpService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

@WebServlet("/quenmatkhau")
public class QuenMatKhauServlet extends HttpServlet {

    private static final long OTP_TTL_MS = 5 * 60 * 1000L;
    private static final int MAX_THU = 5;

    private static final String S_OTP = "quenMk.otp";
    private static final String S_EMAIL = "quenMk.email";
    private static final String S_TIME = "quenMk.time";
    private static final String S_THU = "quenMk.thu";

    private final TaiKhoanRepository repo = new TaiKhoanRepository();
    private final DangNhapController dangNhapController = new DangNhapController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        if ("1".equals(req.getParameter("lai"))) {
            xoaOtp(req.getSession(true));
        }
        hienThi(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);
        String action = req.getParameter("action");

        if ("send".equals(action)) {
            guiMa(req, session);
        } else if ("reset".equals(action)) {
            datLai(req, session);
        }
        hienThi(req, resp);
    }

    private void guiMa(HttpServletRequest req, HttpSession session) {
        String email = req.getParameter("email");
        email = email == null ? "" : email.trim();
        if (email.isEmpty()) {
            req.setAttribute("loi", "Vui lòng nhập email.");
            return;
        }

        TaiKhoan tk = repo.findByEmail(email);
        String otp = OtpService.generateOtp();
        session.setAttribute(S_EMAIL, email);
        session.setAttribute(S_OTP, otp);
        session.setAttribute(S_TIME, System.currentTimeMillis());
        session.setAttribute(S_THU, 0);

        if (tk != null && tk.isHoatDong()) {
            OtpService.sendOtp(email, otp);
        }
        req.setAttribute("thongBao", "Mã xác nhận đã được gửi đến email nếu tài khoản tồn tại trong hệ thống.");
    }

    private void datLai(HttpServletRequest req, HttpSession session) {
        String email = (String) session.getAttribute(S_EMAIL);
        String otp = (String) session.getAttribute(S_OTP);
        Long thoiGian = (Long) session.getAttribute(S_TIME);
        Integer thu = (Integer) session.getAttribute(S_THU);

        if (email == null || otp == null || thoiGian == null) {
            req.setAttribute("loi", "Phiên đã hết hạn, vui lòng nhập lại email để nhận mã.");
            xoaOtp(session);
            return;
        }
        if (System.currentTimeMillis() - thoiGian > OTP_TTL_MS) {
            xoaOtp(session);
            req.setAttribute("loi", "Mã xác nhận đã hết hạn, vui lòng yêu cầu mã mới.");
            return;
        }
        if (thu != null && thu >= MAX_THU) {
            xoaOtp(session);
            req.setAttribute("loi", "Nhập sai quá nhiều lần, vui lòng yêu cầu mã mới.");
            return;
        }

        String nhap = req.getParameter("otp");
        boolean dung = nhap != null && MessageDigest.isEqual(
                otp.getBytes(StandardCharsets.UTF_8),
                nhap.trim().getBytes(StandardCharsets.UTF_8));
        if (!dung) {
            session.setAttribute(S_THU, (thu == null ? 0 : thu) + 1);
            req.setAttribute("loi", "Mã xác nhận không đúng.");
            return;
        }

        try {
            TaiKhoan tk = repo.findByEmail(email);
            if (tk == null || !tk.isHoatDong()) {
                throw new Exception("Không thể đặt lại mật khẩu cho tài khoản này.");
            }
            dangNhapController.datLaiMatKhau(tk.getMaNguoidung(),
                    req.getParameter("matKhauMoi"), req.getParameter("nhapLaiMatKhau"));
            xoaOtp(session);
            req.setAttribute("thanhCong", true);
            req.setAttribute("thongBao", "Đặt lại mật khẩu thành công. Bạn có thể đăng nhập.");
        } catch (Exception e) {
            req.setAttribute("loi", e.getMessage());
        }
    }

    private void xoaOtp(HttpSession session) {
        session.removeAttribute(S_OTP);
        session.removeAttribute(S_EMAIL);
        session.removeAttribute(S_TIME);
        session.removeAttribute(S_THU);
    }

    private void hienThi(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        boolean buocMa = session.getAttribute(S_OTP) != null
                && !Boolean.TRUE.equals(req.getAttribute("thanhCong"));
        req.setAttribute("buocMa", buocMa);
        req.setAttribute("emailDangDung", session.getAttribute(S_EMAIL));
        req.getRequestDispatcher("/WEB-INF/common/quenMatKhau.jsp").forward(req, resp);
    }
}

