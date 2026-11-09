# KẾ HOẠCH KIỂM THỬ (TEST PLAN)
## DỰ ÁN: HỆ THỐNG QUẢN LÝ ĐÀO TẠO VÀ SINH VIÊN (QLSV)
### MÔN HỌC: SOF309 - KIỂM THỬ NÂNG CAO CÙNG AI

---

## 1. Thông tin chung

| Hạng mục | Nội dung |
|---|---|
| Phiên bản tài liệu | 2.0 – Unit-test-first |
| Phạm vi dự án | Java Web MVC QLSV |
| Người thực hiện | Hoàng Anh Khoa – TV00362 – SD21307 |
| Framework kiểm thử | JUnit Jupiter 5.10.2 |
| Công cụ thực thi | Maven Surefire 3.2.5 |
| JDK cấu hình Maven | Java 17 |
| CSDL tùy chọn | Microsoft SQL Server, chỉ cho DB contract/integration test |
| Nguồn truy vết | src/test/java và pom.xml |

## 2. Mục tiêu kiểm thử

- Xác minh các đơn vị xử lý nghiệp vụ, tiện ích, model, service, servlet/controller và filter hoạt động đúng theo đặc tả.
- Phát hiện hồi quy bằng bộ JUnit có thể chạy lặp lại qua Maven.
- Phân biệt rõ unit test độc lập với kiểm thử hợp đồng, kiểm thử tích hợp CSDL và kiểm thử UI; không gộp chúng thành cùng một chỉ số.
- Duy trì liên kết hai chiều giữa tài liệu test case và mã nguồn kiểm thử.

## 3. Phạm vi

### 3.1. Trong phạm vi

| Nhóm | Thành phần | Mục tiêu |
|---|---|---|
| Unit test | Utils, Models, Services, Controllers | Kiểm tra luật nghiệp vụ, tính toán, kiểm tra dữ liệu và xử lý ngoại lệ độc lập. |
| Unit contract | Servlet, Filter, contract test | Kiểm tra điều hướng, request/response, session, phân quyền và ràng buộc trang bằng fixture/proxy/mock nội bộ. |
| DB contract / integration | Repository có điều kiện QLSV_DB_CONTRACT=true | Kiểm tra schema, migration, ràng buộc và truy xuất trên SQL Server khi môi trường sẵn sàng. |
| UI automation | Selenium login test | Ghi nhận riêng để tham khảo; không tính vào phạm vi unit test. |

### 3.2. Ngoài phạm vi

- Đánh giá UI/UX thủ công: thẩm mỹ, responsive, trải nghiệm người dùng và hiệu ứng giao diện.
- Load/stress/security penetration test quy mô lớn.
- Bất kỳ kết quả Pass/Fail nào không có log thực thi Maven hoặc báo cáo test tương ứng.

## 4. Chiến lược kiểm thử

1. **Unit test:** chạy độc lập, không kết nối CSDL hoặc trình duyệt; ưu tiên giá trị biên, nhánh lỗi, luật nghiệp vụ và ngoại lệ.
2. **Unit contract:** cô lập Servlet/Filter bằng fixture, proxy hoặc request/response giả lập; kiểm tra status, forward/redirect, session và thông báo lỗi.
3. **DB contract/integration:** chỉ bật khi có SQL Server đúng schema/dữ liệu và biến môi trường QLSV_DB_CONTRACT=true; dữ liệu test phải có thể làm sạch/hoàn nguyên.
4. **UI automation:** cần Chrome/ChromeDriver, Tomcat và tài khoản test; báo cáo tách biệt, không ảnh hưởng chỉ số unit test.
5. **Truy vết:** mỗi test case trong TEST_CASES.md tham chiếu đến một lớp và phương thức JUnit hiện có.

## 5. Môi trường và công cụ

| Hạng mục | Cấu hình |
|---|---|
| Ngôn ngữ | Java 17 |
| Build tool | Maven |
| Test runner | Maven Surefire 3.2.5 |
| Test framework | JUnit 5.10.2 |
| Kiểm thử giao diện | Selenium 4.20.0 (tùy chọn) |
| Kiểm thử CSDL | SQL Server với QLSV_DB_CONTRACT=true (tùy chọn) |

### Lệnh thực thi

`powershell
# Suite mặc định theo cấu hình pom.xml
mvn test

# Chạy một test case theo lớp/phương thức
mvn "-Dtest=<fully-qualified-class>#<method>" test

# Bật các test cần SQL Server
$env:QLSV_DB_CONTRACT = 'true'
mvn test
`

## 6. Tổng quan bộ test tại thời điểm lập kế hoạch

| Nhóm test | Số phương thức | Số lớp | Điều kiện |
|---|---:|---:|---|
| Unit test độc lập | 175 | - | Chạy bằng Maven, không cần phụ thuộc ngoài |
| Unit contract | 166 | - | Fixture/proxy/mock nội bộ |
| DB contract / integration | 27 | - | SQL Server + QLSV_DB_CONTRACT=true |
| UI automation | 1 | - | Chrome/ChromeDriver + Tomcat |
| **Tổng phương thức JUnit** | **369** | **83** | Theo source src/test/java |
| Phương thức thuộc lớp bị Surefire exclude | 16 | - | Không có trong suite mặc định |

> Số liệu trong bảng được trích từ annotation JUnit hiện có. Các test có điều kiện môi trường có thể xuất hiện ở trạng thái Skipped, N/A hoặc Blocked nếu không thỏa tiền điều kiện.

## 7. Tiêu chí vào và ra

### 7.1. Tiêu chí vào

- Mã nguồn biên dịch được bằng Maven.
- Các dependency trong pom.xml được tải thành công.
- Với DB contract/integration: SQL Server, schema, dữ liệu mẫu và biến môi trường đã sẵn sàng.
- Với UI automation: Tomcat, Chrome/ChromeDriver và dữ liệu đăng nhập test đã sẵn sàng.

### 7.2. Tiêu chí Pass/Fail/Blocked/N/A

| Trạng thái | Định nghĩa |
|---|---|
| Pass | Maven/JUnit chạy thành công; assertion của test đạt. |
| Fail | Test chạy nhưng assertion, ngoại lệ hoặc điều kiện mong đợi không đạt. |
| Blocked | Không thể chạy do thiếu môi trường, dependency hoặc dữ liệu bắt buộc. |
| Skipped | JUnit chủ động bỏ qua theo điều kiện cấu hình. |
| N/A | Chưa thực thi hoặc chưa có bằng chứng kết quả tại thời điểm lập tài liệu. |

### 7.3. Tiêu chí ra

- 100% unit test thuộc suite mục tiêu đã được chạy và có báo cáo Pass/Fail/Skipped.
- Mọi Fail được ghi nhận bằng mã test case, log, nguyên nhân và hướng xử lý.
- DB contract/integration và UI automation được báo cáo riêng; không dùng để tuyên bố toàn bộ unit test đã Pass.
- Không có ô trạng thái nào được ghi Pass khi chưa có log Maven/Surefire tương ứng.

## 8. Rủi ro và phương án xử lý

| Rủi ro | Ảnh hưởng | Xử lý |
|---|---|---|
| Thiếu SQL Server hoặc dữ liệu mẫu | DB contract không chạy được | Đánh dấu Blocked/Skipped; không ảnh hưởng kết quả unit test độc lập. |
| Selenium thiếu ChromeDriver/Tomcat | UI automation không chạy được | Chạy tách biệt và báo cáo N/A/Blocked. |
| Test bị Surefire exclude | Không có trong suite mặc định | Nêu rõ trong báo cáo; chỉ chạy khi được kích hoạt có chủ đích. |
| Dữ liệu CSDL thay đổi | Kết quả integration không ổn định | Sử dụng schema/dữ liệu mẫu đã kiểm soát và dọn dữ liệu sau test. |

## 9. Báo cáo kiểm thử

Mỗi lần chạy cần lưu: ngày giờ, lệnh chạy, commit/phiên bản nguồn, tổng số run/pass/fail/error/skipped, danh sách test thất bại và log Surefire. TEST_CASES.md là danh mục truy vết; trạng thái mặc định là N/A cho đến khi có bằng chứng thực thi.


