
# SOF309_SD21307_TV00362_HoangAnhKhoa
## DỰ ÁN KIỂM THỬ HỆ THỐNG QUẢN LÝ ĐÀO TẠO & SINH VIÊN (QLSV)

- Môn học: SOF309 - Kiểm thử phần mềm / Đảm bảo chất lượng phần mềm
- Lớp: SD21307
- Sinh viên thực hiện: Hoàng Anh Khoa
- Mã số sinh viên (MSSV): TV00362
- Repository URL: `https://github.com/khoahatv00362-hash/SOF309_SD21307_TV00362_HoangAnhKhoa.git`

---


### 1. Yêu cầu môi trường
- **Java:** JDK 21 LTS
- **Server:** Apache Tomcat 11.0 (hoặc Tomcat 10.1+)
- **Database:** Microsoft SQL Server 2022 (Tên CSDL: `qlsinhvien`)
- **Port Web:** `8080` (Truy cập: `http://localhost:8080/login` hoặc `http://localhost:8080/QLSV/login`)

### 2. Cấu hình CSDL
1. Tạo CSDL `qlsinhvien` trong SQL Server bằng các script trong thư mục `database/`.
2. Sao chép `src/main/resources/db.properties.example` thành `src/main/resources/db.properties` và điền mật khẩu sa cục bộ của bạn.

### 3. Đăng nhập kiểm thử (Mật khẩu mặc định: `123456`)
- Quản trị viên (Admin): `admin@hcmus.edu.vn` (Mật khẩu: `123456`)
- Giảng viên (Teacher): `gv01@hcmus.edu.vn` (Mật khẩu: `123456`)
- Sinh viên (Student): `sv003@hcmus.edu.vn` (Mật khẩu: `123456`, MSSV: `220003`)
- Quên mật khẩu: Truy cập `http://localhost:8080/quenmatkhau`, nhập email, hệ thống sẽ in mã xác thực OTP 6 số ra Console/Log server của IntelliJ.
=======
# SOF309_SD21307_TV00362_HoangAnhKhoa

