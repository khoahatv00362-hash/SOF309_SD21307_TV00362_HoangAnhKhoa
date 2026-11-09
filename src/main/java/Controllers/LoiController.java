package Controllers;

public class LoiController {

    public String thongBaoTheoMa(int maLoi) {
        switch (maLoi) {
            case 404: return "Không tìm thấy trang bạn yêu cầu.";
            case 403: return "Bạn không có quyền truy cập chức năng này.";
            case 500: return "Hệ thống gặp sự cố, vui lòng thử lại sau.";
            default:  return "Đã xảy ra lỗi không mong muốn.";
        }
    }
}

