<div style="font-family: 'Times New Roman', Times, serif; font-size: 13pt; line-height: 1.6;">

# NHẬT KÝ SỬ DỤNG GEN AI KẾT HỢP RAG THIẾT KẾ KẾ HOẠCH VÀ KỊCH BẢN KIỂM THỬ CHỨC NĂNG (Y2)
## DỰ ÁN: HỆ THỐNG QUẢN LÝ ĐÀO TẠO VÀ SINH VIÊN (QLSV)
### MÔN HỌC: SOF309 - KIỂM THỬ NÂNG CAO CÙNG AI
### SINH VIÊN: HOÀNG ANH KHOA - MSSV: TV00362 - LỚP: SD21307
### GIẢNG VIÊN HƯỚNG DẪN: ThS. TRẦN QUANG BÌNH

---

## 1. MỤC ĐÍCH VÀ PHƯƠNG PHÁP RAG (RETRIEVAL-AUGMENTED GENERATION)

Theo chuẩn đầu ra yêu cầu của giai đoạn Y2:
1. Áp dụng các kỹ thuật thiết kế kịch bản kiểm thử chức năng chuyên nghiệp:
   - Phân vùng tương đương (Equivalence Partitioning).
   - Phân tích giá trị biên (Boundary Value Analysis).
   - Bảng quyết định và ma trận phân quyền theo vai trò (Decision Table / Role-Based Access).
   - Kiểm thử ngoại lệ nghiệp vụ (Exception Testing).
   - Trọng tâm kiểm thử: 100% kiểm thử chức năng (Functional Testing) tại các Controller, Service và Filter. Hoàn toàn không kiểm thử giao diện người dùng (UI/UX).
2. Quy trình áp dụng công nghệ Generative AI kết hợp kỹ thuật RAG:
   - Cơ sở tri thức nạp vào context (Knowledge Base): Nạp toàn bộ tài liệu đặc tả hệ thống `SPECIFICATIONS.md` (chứa cấu trúc 25 bảng CSDL `qlsinhvien`, danh sách các Controller Java: `DangNhapController`, `DangKyHocPhanController`, `NhapDiemController`, `DiemDanhController`, `TaiKhoanController` và các Filter bảo mật).
   - Nhiệm vụ của AI: Dựa trên context thực tế để sinh ra tài liệu kế hoạch kiểm thử chức năng `TEST_PLAN.md` và bảng danh sách 160 kịch bản kiểm thử chức năng chi tiết `TEST_CASES.md`.
3. Nhật ký này ghi nhận đầy đủ 3 thành phần bắt buộc:
   - Câu lệnh nhắc lệnh đã sử dụng (Prompts).
   - Phiên bản kết quả do AI sinh ra ban đầu (Raw AI Output).
   - Phiên bản sinh viên đã rà soát, phản biện và tinh chỉnh hoàn thiện (Refined Version).

---

## 2. PHẦN 1: CÁC CÂU LỆNH PROMPT ĐÃ SỬ DỤNG

### Câu lệnh Prompt 1: Nạp Context (RAG) và Yêu cầu sinh Kế hoạch kiểm thử chức năng (TEST_PLAN.md)
```text
[BỐI CẢNH HỆ THỐNG - KNOWLEDGE BASE]
Bạn là một chuyên gia kiểm thử phần mềm (QA/QC) và Giảng viên môn SOF309.
Dưới đây là tài liệu đặc tả nghiệp vụ phần mềm (SPECIFICATIONS.md) của dự án Web Quản lý Đào tạo và Sinh viên (QLSV):
- Công nghệ nền tảng: Java Servlet 5.0, JSP, Apache Tomcat 11, Microsoft SQL Server (CSDL: qlsinhvien).
- Danh mục bảng chính: nguoidung, vaitro, sinhvien, giangvien, lophocphan, dangky_hocphan, diemso, diemdanh,...
- Hệ thống Controller: DangNhapController, DangKyHocPhanController, NhapDiemController, DiemDanhController, TaiKhoanController,...
- Cơ chế an toàn và bảo mật: Băm mật khẩu PBKDF2 (kèm tương thích ngược SHA-256), chống CSRF Token, phân quyền Filter, quên mật khẩu nhận OTP qua Console IntelliJ.

[YÊU CẦU CHO AI]
Hãy lập một tài liệu TEST_PLAN.md chuẩn mực cho dự án QLSV trên.
Các tiêu chí bắt buộc:
1. Trọng tâm: Chỉ kiểm thử chức năng (Functional Testing), không kiểm thử giao diện người dùng (UI/UX).
2. Đầy đủ các mục: Mục tiêu, phạm vi kiểm thử (In-scope: nghiệp vụ Controller/Service; Out-of-scope: UI/UX, màu sắc, font chữ, responsive), môi trường kiểm thử (Tomcat 11, localhost:8080, SQL Server qlsinhvien).
3. Nêu rõ các kỹ thuật thiết kế test case áp dụng: Phân vùng tương đương, Phân tích giá trị biên, Bảng quyết định phân quyền.
4. Bảng danh sách tài khoản kiểm thử mẫu cho 3 vai trò: Quản trị viên (Admin), Giảng viên, Sinh viên để người chấm có thể đăng nhập ngay.
5. Bảng thống kê số lượng test case phân bổ theo từng phân hệ nghiệp vụ.
```

---

### Câu lệnh Prompt 2: Yêu cầu sinh Bộ Test Case chức năng chi tiết (TEST_CASES.md)
```text
[BỐI CẢNH HỆ THỐNG]
Dựa vào tài liệu SPECIFICATIONS.md và danh sách các phương thức trong tầng Controller của dự án QLSV:
- DangNhapController: dangNhap(email, matKhauTho), doiMatKhau(maNguoidung, matKhauCu, matKhauMoi, nhapLaiMatKhauMoi), datLaiMatKhau(maNguoidung, matKhauMoi, nhapLaiMatKhauMoi).
- DangKyHocPhanController: dangKyHocPhan(maSV, maLHP), huyDangKy(maDangKy), kiemTraTienQuyet(), kiemTraSiSo().
- NhapDiemController: luuDiem(maDangKy, chuyenCan, giuaKy, cuoiKy), kiemTraHopLe(diem).
- DiemDanhController: diemDanh(maLHP, maSV, ngay, trangThai).
- TaiKhoanController: themMoi(email, hoTen, matKhau), capNhatTrangThai(id, hoatDong).
- Cac Filter: AuthenticationFilter, AuthorizationFilter, CsrfFilter.

[YÊU CẦU CHO AI]
Hãy thiết kế bộ kịch bản kiểm thử chức năng (TEST_CASES.md) chi tiết dạng bảng Markdown.
Yêu cầu cụ thể:
1. Chỉ tập trung vào kiểm thử chức năng (Functional Testing: dữ liệu đầu vào, xử lý nghiệp vụ, tính toán điểm, điều hướng, lưu trữ cơ sở dữ liệu). Tuyệt đối không kiểm thử UI/UX.
2. Cấu trúc mỗi kịch bản gồm các cột: Mã TC, Vai trò, Chức năng, Tiêu đề kịch bản, Tiền điều kiện, Các bước thực hiện, Dữ liệu kiểm thử, Kết quả mong đợi, Phân loại (Thuận/Âm/Biên), Mức ưu tiên (Cao/TB/Thấp), Kết quả thực tế, Trạng thái (Pass).
3. Bao phủ toàn diện các phân hệ:
   - Phân hệ Chung: Đăng nhập mật khẩu, Đăng nhập Google, Đổi mật khẩu, Quên mật khẩu OTP Console, Đăng xuất.
   - Phân hệ Bảo mật: Filter xác thực, Phân quyền vai trò, Chống CSRF token, Mã hóa tiếng Việt UTF-8.
   - Phân hệ Admin: Quản lý danh mục đào tạo, Hồ sơ người dùng, Vận hành học vụ, Phân quyền.
   - Phân hệ Giảng viên: Lớp giảng dạy, Nhập điểm thang 0 đến 10, Cập nhật điểm, Điểm danh sinh viên.
   - Phân hệ Sinh viên: Đăng ký học phần, Xem bảng điểm chi tiết, Xem điểm GPA tích lũy, Xem lịch học và lịch thi.
4. Dữ liệu kiểm thử phải khớp chính xác với dữ liệu thực tế trong CSDL qlsinhvien, không dùng dữ liệu giả định.
```

---

## 3. PHẦN 2: PHIÊN BẢN BAN ĐẦU DO GEN AI SINH RA (RAW AI OUTPUT)

Sau khi nhận prompt, phiên bản ban đầu do AI sinh ra có cấu trúc sơ khai như sau:

| Mã TC | Chức năng | Tiêu đề kịch bản | Dữ liệu kiểm thử | Kết quả mong đợi |
|---|---|---|---|---|
| TC-01 | Login | Đăng nhập đúng | admin@qlsv.edu.vn / 123456 | Đăng nhập thành công |
| TC-02 | Login | Đăng nhập sai pass | admin@qlsv.edu.vn / wrongpass | Báo lỗi đăng nhập |
| TC-03 | Đổi MK | Đổi mật khẩu thành công | Cũ: 123456, Mới: 654321 | Đổi mật khẩu thành công |
| TC-04 | ĐKHP | Sinh viên đăng ký | MaSV=1, MaMon=IT01 | Đăng ký học phần thành công |
| TC-05 | Nhập điểm | Giảng viên nhập điểm | Điểm = 8.5 | Lưu điểm thành công |
| TC-06 | Nhập điểm | Nhập điểm âm | Điểm = -1.0 | Báo lỗi điểm không hợp lệ |
| TC-07 | Điểm danh | Điểm danh sinh viên | Trạng thái = Có mặt | Ghi nhận điểm danh |

### Nhận xét các thiếu sót của Phiên bản AI sinh ban đầu:
1. **Dữ liệu giả định không có thật trong hệ thống:** AI tự bịa ra email `admin@qlsv.edu.vn`, mã môn `IT01`, mã sinh viên `MaSV=1`. Những dữ liệu này không hề tồn tại trong CSDL `qlsinhvien`. Nếu người chấm mang dữ liệu này kiểm thử trên hệ thống thật thì mọi test case đều bị lỗi.
2. **Thiếu ánh xạ tới phương thức Controller:** AI chỉ ghi tên chức năng chung chung (Login, Đổi MK), không chỉ định rõ method xử lý trong mã nguồn Java (ví dụ: `DangNhapController.dangNhap`, `DangKyHocPhanController.dangKyHocPhan`).
3. **Thiếu chi tiết các bước kiểm thử:** Thiếu hoàn toàn cột tiền điều kiện, các bước thực hiện chi tiết, phân loại kỹ thuật kiểm thử (Thuận, Âm, Biên) và mức độ ưu tiên.
4. **Bỏ sót các tính năng cốt lõi mới xây dựng:** Chưa có kịch bản cho tính năng Quên mật khẩu nhận mã OTP qua Console IntelliJ, chưa có kịch bản kiểm tra CSRF Token cho request POST, chưa kiểm tra cơ chế nâng cấp băm mật khẩu PBKDF2.

---

## 4. PHẦN 3: PHIÊN BẢN SINH VIÊN ĐÃ RÀ SOÁT VÀ TINH CHỈNH HOÀN THIỆN (REFINED VERSION)

Sinh viên Hoàng Anh Khoa đã tiến hành rà soát, đối chiếu trực tiếp với mã nguồn và cơ sở dữ liệu để hoàn thiện:

1. **Chuẩn hóa 100% dữ liệu kiểm thử theo CSDL thực tế `qlsinhvien`:**
   - Cập nhật toàn bộ tài khoản thử nghiệm theo dữ liệu thật trong cơ sở dữ liệu:
     + Quản trị viên (Admin): `admin@hcmus.edu.vn` (Mật khẩu: `123456`)
     + Quản trị viên (Admin): `khoahatv00362@fpt.edu.vn` (Mật khẩu: `123456`)
     + Giảng viên: `gv01@hcmus.edu.vn` (Mật khẩu: `123456`)
     + Sinh viên: `sv003@hcmus.edu.vn` (Mật khẩu: `123456`, MSSV: `220003`, Lớp: `CNTT02_K22`)
     + Sinh viên: `sv004@hcmus.edu.vn` (Mật khẩu: `123456`, MSSV: `220004`, Lớp: `CNTT02_K22`)
   - Mã lớp học phần thực tế: `IT001_HK1_N01` (Nhập môn lập trình), `IT002_HK1_N01` (Cấu trúc dữ liệu và giải thuật).

2. **Gắn liền từng kịch bản kiểm thử với phương thức trong Controller và Service:**
   - Kịch bản `TC-AUTH-001` gắn với: `DangNhapController.dangNhap(email, matKhauTho)`.
   - Kịch bản `TC-AUTH-008` gắn với: `DangNhapController.doiMatKhau(maNguoidung, matKhauCu, matKhauMoi, nhapLaiMatKhauMoi)`.
   - Kịch bản `TC-AUTH-013` gắn với: `QuenMatKhauServlet.doPost()` và `OtpService.generateOtp()`.
   - Kịch bản `TC-AUTH-016` gắn với: `DangNhapController.datLaiMatKhau(maNguoidung, matKhauMoi, nhapLaiMatKhauMoi)`.
   - Kịch bản `TC-SEC-004` gắn với: `CsrfFilter.doFilter()` và `CsrfToken.matches()`.
   - Kịch bản `TC-DK-001` gắn với: `DangKyHocPhanController.dangKyHocPhan(maSV, maLHP)`.
   - Kịch bản `TC-GV-003` gắn với: `NhapDiemController.luuDiem(maDangKy, chuyenCan, giuaKy, cuoiKy)`.

3. **Bổ sung 4 kịch bản kiểm thử chuyên biệt cho Quên mật khẩu qua OTP Console:**
   - `TC-AUTH-013`: Yêu cầu gửi mã OTP với email hợp lệ (mã 6 số in trực tiếp ra Console IntelliJ).
   - `TC-AUTH-014`: Yêu cầu gửi mã OTP với email không tồn tại (xử lý an toàn, hiển thị thông báo chung để không lộ email người dùng).
   - `TC-AUTH-015`: Nhập mã OTP sai hoặc vượt quá 5 lần thử (chống tấn công vét cạn brute-force, hủy phiên OTP).
   - `TC-AUTH-016`: Đặt lại mật khẩu thành công bằng mã OTP hợp lệ trong vòng 5 phút (mật khẩu mới được băm theo chuẩn PBKDF2).

4. **Định hình phạm vi kiểm thử: 100% chức năng, không kiểm thử UI/UX:**
   - Toàn bộ 160 kịch bản đều tập trung vào kiểm tra logic tính toán, kiểm tra luồng dữ liệu, tính toàn vẹn cơ sở dữ liệu và xử lý quyền hạn.
   - Không có kịch bản nào kiểm thử về màu sắc, căn lề, độ co giãn giao diện hay thẩm mỹ giao diện.

5. **Kết quả đầu ra hoàn thiện lưu trữ trong repository:**
   - File kế hoạch kiểm thử: `docs/TEST_PLAN.md` (chuẩn font Times New Roman, không ký tự đặc biệt).
   - File danh sách kịch bản: `docs/TEST_CASES.md` (bao gồm đầy đủ 160 kịch bản kiểm thử chức năng chi tiết).

</div>
