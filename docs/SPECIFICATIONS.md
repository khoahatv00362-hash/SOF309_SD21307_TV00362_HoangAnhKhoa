# TÀI LIỆU ĐẶC TẢ HỆ THỐNG (SYSTEM SPECIFICATIONS)
## DỰ ÁN: HỆ THỐNG QUẢN LÝ ĐÀO TẠO & SINH VIÊN (QLSV)
### MÔN HỌC: SOF309 - KIỂM THỬ NÂNG CAO / ĐẢM BẢO CHẤT LƯỢNG PHẦN MỀM

---

## 1. THÔNG TIN CHUNG (PROJECT METADATA)

- **Tên dự án:** Hệ thống Quản lý Đào tạo & Sinh viên (QLSV)
- **Mã môn học:** SOF309
- **Lớp học:** SD21307
- **Mã số sinh viên (MSSV):** TV00362
- **Họ và tên sinh viên:** Hoàng Anh Khoa
- **Repository GitHub cá nhân:** `https://github.com/khoahatv00362-hash/SOF309_SD21307_TV00362_HoangAnhKhoa.git`
- **Môi trường triển khai Local:**
  - URL: `http://localhost:8080/login` hoặc `http://localhost:8080/QLSV/login`
  - Web Server: Apache Tomcat 11.0 (Servlet Specification 5.0 / Jakarta EE)
  - Java Development Kit: JDK 21 LTS
  - Hệ quản trị CSDL: Microsoft SQL Server 2022 (Database Name: `qlsinhvien`)
  - Collation CSDL: `Vietnamese_CI_AS`

---

## 2. KIẾN TRÚC HỆ THỐNG & CÔNG NGHỆ (ARCHITECTURE & TECH STACK)

### 2.1. Mô hình kiến trúc MVC (Model - View - Controller)
Hệ thống được thiết kế theo mô hình chuẩn Java Web MVC cổ điển và vững chắc:
- **Model:**
  - Các Entity Beans ánh xạ trực tiếp từ CSDL: `TaiKhoan`, `SinhVien`, `GiangVien`, `MonHoc`, `LopHocPhan`, `DangKyHocPhan`, `DiemSo`, `DiemDanh`, `LichHoc`, `KyThi`, `Khoa`, `ChuyenNganh`, `HocKy`, `PhongHoc`, `CaHoc`, `ThongBao`, `NhatKyHoatDong`.
  - Tầng Repository thực thi các câu lệnh SQL an toàn thông qua `PreparedStatement` để triệt tiêu lỗ hổng SQL Injection.
  - Tầng Service xử lý các luật nghiệp vụ, tính toán GPA, logic điểm danh, sinh OTP ngẫu nhiên an toàn.
- **View:**
  - Trang giao diện JavaServer Pages (JSP) tổ chức theo các thư mục quyền: `/WEB-INF/admin/`, `/WEB-INF/teacher/`, `/WEB-INF/student/`, `/WEB-INF/common/`.
  - Layout dùng chung với `header.jsp`, `sidebar.jsp`, `footer.jsp`, chống lộ mã nguồn và chống người dùng truy cập trực tiếp file JSP không thông qua Controller.
- **Controller & Filter:**
  - Hệ thống Jakarta Servlet bắt giữ các yêu cầu HTTP GET/POST và điều phối luồng xử lý.
  - Tầng Filter trung gian kiểm soát luồng dữ liệu trước khi đến Servlet:
    1. `CharacterEncodingFilter`: Đảm bảo toàn bộ request/response xử lý bảng mã tiếng Việt UTF-8.
    2. `AuthenticationFilter`: Bắt buộc người dùng phải đăng nhập trước khi truy cập các URL nội bộ (`/admin/*`, `/giangvien/*`, `/sinhvien/*`).
    3. `AuthorizationFilter`: Kiểm tra danh sách quyền của người dùng trong phiên (Session); chặn người dùng vai trò SinhVien hoặc GiangVien truy cập trái phép vào phân hệ `/admin/*`.
    4. `CsrfFilter`: Tự động tạo mã xác thực một lần và kiểm tra `_csrf` token trên mọi yêu cầu POST/PUT/DELETE nhằm ngăn chặn tấn công giả mạo yêu cầu từ trang khác (CSRF).
    5. `AuditLogFilter`: Tự động ghi lại lịch sử thao tác, địa chỉ IP và hành động của người dùng vào bảng `nhatky_hoatdong`.

### 2.2. Cơ chế An toàn & Bảo mật
- **Băm mật khẩu PBKDF2 kết hợp hỗ trợ ngược SHA-256:**
  - Mật khẩu mới được băm bằng thuật toán `PBKDF2WithHmacSHA256` với 120.000 vòng lặp và muối ngẫu nhiên (salt) 16 byte (`pbkdf2$120000$<salt>$<hash>`).
  - Hệ thống duy trì tính tương thích ngược với dữ liệu mẫu cũ (băm SHA-256 kèm pepper `"qlsv$"` 64 ký tự). Khi người dùng đăng nhập thành công bằng mật khẩu cũ, hệ thống tự động băm lại và nâng cấp lên PBKDF2 mà không làm gián đoạn trải nghiệm người dùng.
- **Quên mật khẩu qua OTP Console:**
  - Người dùng nhập email tại `/quenmatkhau`.
  - Hệ thống kiểm tra tài khoản, sinh mã xác thực 6 số ngẫu nhiên qua `SecureRandom` có hiệu lực trong 5 phút và in trực tiếp ra Console của server / IntelliJ.
  - Khống chế tối đa 5 lần thử sai để chống vét cạn (brute-force) và bảo vệ quyền riêng tư bằng cách trả về thông báo chung, không để lộ email có tồn tại trong hệ thống hay không.

---

## 3. ĐẶC TẢ CƠ SỞ DỮ LIỆU (DATABASE SCHEMA SPECIFICATIONS)

Hệ thống quản lý trên CSDL `qlsinhvien` bao gồm 25 bảng và 7 view tổng hợp:

1. `nguoidung`: Lưu trữ thông tin tài khoản, email, mật khẩu băm, họ tên, số điện thoại, địa chỉ, trạng thái hoạt động, thời gian đăng nhập cuối.
2. `vaitro`: Danh mục các vai trò hệ thống (`Admin`, `GiangVien`, `SinhVien`, `DaoTao`).
3. `vaitro_nguoidung`: Bảng liên kết n-n giữa người dùng và vai trò.
4. `sinhvien`: Hồ sơ chi tiết sinh viên (Mã SV, mã người dùng, mã lớp, MSSV, ngày sinh, giới tính, trạng thái).
5. `giangvien`: Hồ sơ chi tiết giảng viên (Mã GV, mã người dùng, học vị, chuyên ngành, trạng thái).
6. `khoa`: Danh mục các khoa đào tạo (CNTT, Kinh tế, Ngoại ngữ,...).
7. `chuyennganh`: Danh mục chuyên ngành trực thuộc khoa.
8. `monhoc`: Danh mục môn học, số tín chỉ, số tiết lý thuyết, thực hành.
9. `monhoc_tienquyet`: Ràng buộc môn học điều kiện trước khi đăng ký.
10. `khoahoc`: Danh mục khóa đào tạo (K21, K22, K23).
11. `lop`: Danh mục lớp sinh hoạt của sinh viên.
12. `hocky`: Quản lý các học kỳ theo năm học.
13. `phonghoc`: Danh mục giảng đường, phòng thực hành, sức chứa.
14. `cahoc`: Danh mục ca học và khung giờ quy định.
15. `lophocphan`: Lớp học phần được mở theo kỳ để sinh viên đăng ký tín chỉ (sĩ số tối đa, trạng thái mở/đóng).
16. `dangky_hocphan`: Danh sách sinh viên đăng ký vào lớp học phần và trạng thái duyệt.
17. `lichhoc`: Thời khóa biểu học tập của lớp học phần.
18. `lichday_giangvien`: Lịch giảng dạy được phân công cho từng giảng viên.
19. `kythi`: Lịch thi kết thúc học phần, phòng thi, ca thi.
20. `diemso`: Kết quả học tập của sinh viên theo học phần (Điểm chuyên cần, Điểm giữa kỳ, Điểm cuối kỳ, Điểm tổng kết).
21. `diemdanh`: Lịch sử điểm danh chi tiết từng buổi của sinh viên (Có mặt, Đi trễ, Vắng có phép, Vắng không phép).
22. `thongbao`: Nội dung thông báo chung hoặc theo chuyên ngành.
23. `thongbao_nguoinhan`: Quản lý trạng thái đọc thông báo của từng người nhận.
24. `nhatky_hoatdong`: Lưu vết kiểm toán (Audit log) các thao tác thêm, sửa, xóa, đăng nhập.
25. `gmail_token`: Lưu trữ thông tin liên kết OAuth với Google.

---

## 4. MA TRẬN PHÂN QUYỀN HỆ THỐNG (ROLE & PERMISSION MATRIX)

| Mã Phân Hệ | Nhóm Chức Năng | Khách (Guest) | Quản Trị Viên (Admin) | Giảng Viên (GiangVien) | Sinh Viên (SinhVien) |
|---|---|:---:|:---:|:---:|:---:|
| **AUTH** | Đăng nhập Email & Mật khẩu | ✅ | ✅ | ✅ | ✅ |
| **AUTH** | Đăng nhập Google OAuth2 | ✅ | ✅ | ✅ | ✅ |
| **AUTH** | Quên mật khẩu & Nhận OTP Console | ✅ | ✅ | ✅ | ✅ |
| **AUTH** | Đổi mật khẩu cá nhân | ❌ | ✅ | ✅ | ✅ |
| **AUTH** | Đăng xuất | ❌ | ✅ | ✅ | ✅ |
| **SEC** | Chặn truy cập trái quyền (Filter) | ✅ | ✅ | ✅ | ✅ |
| **SEC** | Bảo vệ CSRF Token | ✅ | ✅ | ✅ | ✅ |
| **ADMIN** | Quản lý Khoa & Chuyên ngành | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Quản lý Môn học & Tiên quyết | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Quản lý Lớp, Khóa học, Học kỳ | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Quản lý Phòng học & Ca học | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Quản lý Giảng viên & Sinh viên | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Quản lý Lớp học phần & Lịch học | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Quản lý Kỳ thi & Lịch thi | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Quản lý Tài khoản & Phân quyền | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Duyệt đăng ký học phần | ❌ | ✅ | ❌ | ❌ |
| **ADMIN** | Thống kê GPA & Xem Nhật ký hệ thống | ❌ | ✅ | ❌ | ❌ |
| **TEACHER**| Xem danh sách lớp giảng dạy | ❌ | ❌ | ✅ | ❌ |
| **TEACHER**| Nhập điểm học phần | ❌ | ❌ | ✅ | ❌ |
| **TEACHER**| Cập nhật điểm & Thống kê điểm | ❌ | ❌ | ✅ | ❌ |
| **TEACHER**| Điểm danh từng buổi học | ❌ | ❌ | ✅ | ❌ |
| **TEACHER**| Xem lịch dạy & lịch thi được phân công | ❌ | ❌ | ✅ | ❌ |
| **TEACHER**| Xem thông báo & Hồ sơ giảng viên | ❌ | ❌ | ✅ | ❌ |
| **STUDENT**| Bảng điều khiển sinh viên (Dashboard) | ❌ | ❌ | ❌ | ✅ |
| **STUDENT**| Đăng ký học phần tín chỉ | ❌ | ❌ | ❌ | ✅ |
| **STUDENT**| Xem bảng điểm cá nhân & Điểm GPA | ❌ | ❌ | ❌ | ✅ |
| **STUDENT**| Xem lịch học & Thời khóa biểu | ❌ | ❌ | ❌ | ✅ |
| **STUDENT**| Xem lịch sử điểm danh cá nhân | ❌ | ❌ | ❌ | ✅ |
| **STUDENT**| Xem lịch thi kết thúc học phần | ❌ | ❌ | ❌ | ✅ |
| **STUDENT**| Xem thông báo & Thông tin cá nhân | ❌ | ❌ | ❌ | ✅ |

---

## 5. ĐẶC TẢ CHI TIẾT 48 CHỨC NĂNG & ENDPOINTS THỰC TẾ

### 5.1. Phân hệ Xác thực & Dùng chung (Common & Authentication)
1. **`/login` (GET/POST):** Trang đăng nhập hệ thống. Hỗ trợ nhập Email + Password. Khi đăng nhập thành công, điều hướng tương ứng: Admin → `/dashboard`, Giảng viên → `/giangvien/dashboard`, Sinh viên → `/sinhvien/dashboard`.
2. **`/gmail/login` & `/gmail/callback` (GET):** Khởi tạo quy trình xác thực OAuth2 với Google API, đối chiếu email Google trả về với bảng `nguoidung` và tạo phiên làm việc.
3. **`/quenmatkhau` (GET/POST):** Quy trình 2 bước: Nhập email gửi mã OTP 6 số qua Console IntelliJ (bảo mật, thời hạn 5 phút); nhập OTP cùng mật khẩu mới (tối thiểu 6 ký tự) để khôi phục quyền truy cập.
4. **`/giangvien/doimatkhau` (GET/POST):** Kiểm tra mật khẩu hiện tại, nhận mật khẩu mới và xác nhận mật khẩu, băm lại bằng PBKDF2 và cập nhật vào CSDL.
5. **`/logout` (GET/POST):** Hủy toàn bộ phiên làm việc (Session Invalidation), xóa Cookie và chuyển hướng người dùng về trang đăng nhập.

### 5.2. Phân hệ Quản trị viên (Admin Module - `/admin/*`)
6. **`/admin/taikhoan`:** Hiển thị danh sách tài khoản, trạng thái khóa/mở, thêm tài khoản local mới với mật khẩu mặc định `123456`.
7. **`/admin/phanquyen`:** Gán và điều chỉnh vai trò (`Admin`, `GiangVien`, `SinhVien`) cho từng mã người dùng.
8. **`/admin/khoa`:** Quản lý danh mục Khoa (Thêm, sửa, xóa, tìm kiếm mã khoa, tên khoa, thông tin liên hệ).
9. **`/admin/chuyennganh`:** Quản lý chuyên ngành trực thuộc khoa đào tạo.
10. **`/admin/monhoc`:** Quản lý danh mục môn học, số tín chỉ lý thuyết và thực hành.
11. **`/admin/montienquyet`:** Thiết lập môn học tiên quyết bắt buộc trước khi sinh viên được đăng ký môn học tiếp theo.
12. **`/admin/lophoc`:** Quản lý lớp sinh hoạt của các khóa sinh viên.
13. **`/admin/khoahoc`:** Quản lý các khóa tuyển sinh (niên khóa).
14. **`/admin/hocky`:** Thiết lập học kỳ và năm học đang vận hành.
15. **`/admin/phonghoc`:** Quản lý hệ thống phòng học, tòa nhà và sức chứa tối đa.
16. **`/admin/cahoc`:** Cấu hình khung thời gian bắt đầu và kết thúc của các ca học trong ngày.
17. **`/admin/giangvien`:** Quản lý hồ sơ giảng viên, học vị, chuyên ngành giảng dạy.
18. **`/admin/sinhvien`:** Quản lý hồ sơ lý lịch sinh viên, MSSV, lớp sinh hoạt, ngày sinh, tình trạng học tập.
19. **`/admin/lophocphan`:** Mở lớp học phần theo kỳ học, phân công giảng viên giảng dạy, giới hạn sĩ số tối đa.
20. **`/admin/lichhoc`:** Xếp thời khóa biểu học tập (Thứ, Ca, Phòng) cho lớp học phần.
21. **`/admin/kythi`:** Xếp lịch thi, phòng thi, ca thi cho các học phần hoàn thành.
22. **`/duyetdangky`:** Duyệt danh sách sinh viên đăng ký học phần tín chỉ, chuyển trạng thái từ `ChoDuyet` sang `DaDuyet` hoặc `TuChoi`.
23. **`/admin/thongbao`:** Soạn và phát hành thông báo đến giảng viên hoặc sinh viên.
24. **`/admin/nhatky`:** Tra cứu nhật ký kiểm toán hệ thống, lịch sử cập nhật dữ liệu.
25. **`/admin/gpa`:** Tổng hợp và tính toán điểm trung bình tích lũy GPA toàn trường.
26. **`/admin/timkiem`:** Công cụ tìm kiếm tổng hợp thông tin sinh viên, lớp học phần, môn học.

### 5.3. Phân hệ Giảng viên (Teacher Module - `/giangvien/*`)
27. **`/giangvien/dashboard`:** Tổng hợp số lớp đang giảng dạy, lịch dạy hôm nay, ca dạy sắp tới.
28. **`/giangvien/lopgiangday`:** Danh sách các lớp học phần được phân công giảng dạy trong học kỳ.
29. **`/giangvien/nhapdiem`:** Giao diện bảng điểm lớp học phần để giảng viên nhập điểm chuyên cần, giữa kỳ và cuối kỳ.
30. **`/giangvien/capnhatdiem`:** Chỉnh sửa điểm số đã nhập khi có phúc khảo hoặc điều chỉnh hợp lệ.
31. **`/giangvien/diemdanh`:** Bảng điểm danh sinh viên theo từng buổi học (Có mặt / Trễ / Vắng phép / Vắng không phép).
32. **`/giangvien/thongkediem`:** Biểu đồ và bảng phân bố điểm, tỷ lệ đạt/trượt của lớp học phần.
33. **`/giangvien/lichday` & `/giangvien/xemlichday`:** Xem lịch biểu giảng dạy chi tiết theo tuần, ca học, phòng học.
34. **`/giangvien/thongbao`:** Xem danh sách thông báo từ phòng đào tạo gửi đến giảng viên.
35. **`/giangvien/hoso`:** Xem thông tin cá nhân và chuyên ngành phụ trách.

### 5.4. Phân hệ Sinh viên (Student Module - `/sinhvien/*`)
36. **`/sinhvien/dashboard`:** Tổng quan tiến độ học tập, thông báo mới nhất, lịch học trong ngày.
37. **`/sinhvien/dangky`:** Chức năng đăng ký học phần tín chỉ trực tuyến: chọn môn học mở trong kỳ, kiểm tra môn tiên quyết và sĩ số còn trống.
38. **`/sinhvien/bangdiem`:** Tra cứu bảng điểm chi tiết các môn học đã hoàn thành và điểm tổng kết.
39. **`/sinhvien/gpa`:** Xem điểm trung bình tích lũy GPA theo thang điểm 4 và thang điểm 10 kèm xếp loại học lực.
40. **`/sinhvien/lichhoc`:** Thời khóa biểu học tập cá nhân theo từng tuần và học kỳ.
41. **`/sinhvien/diemdanh`:** Theo dõi tỷ lệ chuyên cần và số buổi vắng học trên từng môn.
42. **`/sinhvien/kythi`:** Tra cứu lịch thi, phòng thi, ca thi và số báo danh của bản thân.
43. **`/sinhvien/thongbao`:** Xem thông báo chung của nhà trường và khoa.
44. **`/sinhvien/thongtin`:** Xem thông tin lý lịch cá nhân, chuyên ngành, lớp sinh hoạt.

---

## 6. MA TRẬN TRUY XUẤT KIỂM THỬ (TEST TRACEABILITY MATRIX)

Toàn bộ các đặc tả yêu cầu trong tài liệu này được kiểm thử toàn diện qua bộ tài liệu:
- **[TEST_PLAN.md](./TEST_PLAN.md):** Kế hoạch kiểm thử tổng thể, chiến lược, tiêu chí Pass/Fail và môi trường thực nghiệm.
- **[TEST_CASES.md](./TEST_CASES.md):** Danh sách chi tiết **160 kịch bản kiểm thử** (Test Cases) bao phủ 100% các phân hệ:
  - 16 Test Cases Xác thực & Chung (`TC-AUTH-001` → `TC-AUTH-016`)
  - 6 Test Cases Bảo mật & Filter (`TC-SEC-001` → `TC-SEC-006`)
  - 106 Test Cases Quản trị viên Admin (`TC-KHOA`, `TC-CN`, `TC-MH`, `TC-LH`, `TC-LHP`, `TC-TK`, `TC-PQ`,...)
  - 17 Test Cases Nghiệp vụ Giảng viên (`TC-GV-001` → `TC-GV-017`)
  - 15 Test Cases Nghiệp vụ Sinh viên (`TC-SV-001` → `TC-SV-015`)
