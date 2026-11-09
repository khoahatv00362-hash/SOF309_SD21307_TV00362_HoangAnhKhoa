package Models;

import java.time.LocalDateTime;

public class NhatKyHoatDong {

    private int maNhatky;
    private int maNguoidung;
    private String hanhDong;
    private LocalDateTime thoiGian;
    private String hoTenNguoiDung;
    private String tenVaiTro;
    private String ipAddress;

    public NhatKyHoatDong() {
    }

    public int getMaNhatky() {
        return maNhatky;
    }

    public void setMaNhatky(int maNhatky) {
        this.maNhatky = maNhatky;
    }

    public int getMaNguoidung() {
        return maNguoidung;
    }

    public void setMaNguoidung(int maNguoidung) {
        this.maNguoidung = maNguoidung;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    public String getDoiTuongHienThi() {
        if (tenVaiTro == null) return "Người dùng";
        switch (tenVaiTro.toUpperCase()) {
            case "ADMIN":   return "QTV hệ thống";
            case "TEACHER": return "Giảng viên";
            case "STUDENT": return "Sinh viên";
            default:        return "Người dùng";
        }
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getHoTenNguoiDung() {
        return hoTenNguoiDung;
    }

    public void setHoTenNguoiDung(String hoTenNguoiDung) {
        this.hoTenNguoiDung = hoTenNguoiDung;
    }
}

