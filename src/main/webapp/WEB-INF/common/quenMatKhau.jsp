<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Utils.Html" %>
<%
    String contextPath = request.getContextPath();
    String loi = (String) request.getAttribute("loi");
    String thongBao = (String) request.getAttribute("thongBao");
    boolean buocMa = Boolean.TRUE.equals(request.getAttribute("buocMa"));
    boolean thanhCong = Boolean.TRUE.equals(request.getAttribute("thanhCong"));
    String email = (String) request.getAttribute("emailDangDung");
    String csrf = (String) request.getAttribute("csrfToken");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu - Hệ thống Quản lý Sinh viên</title>
    <link rel="stylesheet" href="<%= contextPath %>/assets/css/login.css?v=2">
</head>
<body>
<div class="khung-dangnhap">
    <div class="panel-trai">
        <div class="gioi-thieu">
            <h1>Hệ thống Quản lý<br>Đào tạo &amp; Sinh viên</h1>
            <p>Khôi phục quyền truy cập tài khoản của bạn.</p>
        </div>
    </div>

    <div class="panel-phai">
        <h2>Quên mật khẩu</h2>

        <% if (loi != null) { %>
            <div class="bao-loi"><%= Html.escape(loi) %></div>
        <% } %>
        <% if (thongBao != null) { %>
            <p class="phu-de"><%= Html.escape(thongBao) %></p>
        <% } %>

        <% if (thanhCong) { %>
            <a href="<%= contextPath %>/login" class="nut-dangnhap" style="display:block;text-align:center;text-decoration:none;">Về trang đăng nhập</a>
        <% } else if (!buocMa) { %>
            <p class="phu-de">Nhập email tài khoản để nhận mã xác nhận.</p>
            <form method="post" action="<%= contextPath %>/quenmatkhau">
                <input type="hidden" name="_csrf" value="<%= Html.escape(csrf) %>">
                <input type="hidden" name="action" value="send">
                <div class="nhom-nhap">
                    <label for="email">Email</label>
                    <input type="text" id="email" name="email" required autofocus placeholder="vd: admin@fpt.edu.vn">
                </div>
                <button type="submit" class="nut-dangnhap">Gửi mã xác nhận</button>
            </form>
            <div class="hang-phu"><a href="<%= contextPath %>/login">Quay lại đăng nhập</a></div>
        <% } else { %>
            <p class="phu-de">Mã gồm 6 số, có hiệu lực 5 phút. Tài khoản: <b><%= Html.escape(email) %></b></p>
            <form method="post" action="<%= contextPath %>/quenmatkhau">
                <input type="hidden" name="_csrf" value="<%= Html.escape(csrf) %>">
                <input type="hidden" name="action" value="reset">
                <div class="nhom-nhap">
                    <label for="otp">Mã xác nhận</label>
                    <input type="text" id="otp" name="otp" required autofocus maxlength="6" placeholder="Nhập mã 6 số">
                </div>
                <div class="nhom-nhap">
                    <label for="matKhauMoi">Mật khẩu mới (tối thiểu 6 ký tự)</label>
                    <input type="password" id="matKhauMoi" name="matKhauMoi" required minlength="6">
                </div>
                <div class="nhom-nhap">
                    <label for="nhapLaiMatKhau">Nhập lại mật khẩu mới</label>
                    <input type="password" id="nhapLaiMatKhau" name="nhapLaiMatKhau" required minlength="6">
                </div>
                <button type="submit" class="nut-dangnhap">Đặt lại mật khẩu</button>
            </form>
            <div class="hang-phu"><a href="<%= contextPath %>/quenmatkhau?lai=1">Gửi lại mã / đổi email</a></div>
        <% } %>
    </div>
</div>
</body>
</html>
