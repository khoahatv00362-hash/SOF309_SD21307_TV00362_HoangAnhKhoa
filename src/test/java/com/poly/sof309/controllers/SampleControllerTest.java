package com.poly.sof309.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================================
 * [Y3] FILE TEST MẪU BẰNG JUNIT 5 CHO CONTROLLER
 * Sinh viên tham khảo file này để viết unit test cho các hàm trong Controller/Service
 * dựa theo bộ Test Case đã thiết kế ở Y2 (trong file docs/TEST_CASES.md).
 * =========================================================================
 */
public class SampleControllerTest {

    @Test
    @DisplayName("TC_AUTH_01: Kiểm tra đăng nhập với thông tin hợp lệ")
    public void testLoginSuccess() {
        String username = "admin";
        String password = "123";
        
        boolean isValidUser = authenticate(username, password);
        assertTrue(isValidUser, "Đăng nhập phải thành công với tài khoản đúng");
    }

    @Test
    @DisplayName("TC_AUTH_02: Kiểm tra đăng nhập thất bại khi để trống mật khẩu")
    public void testLoginEmptyPassword() {
        String username = "admin";
        String password = "";

        boolean isValidUser = authenticate(username, password);
        assertFalse(isValidUser, "Đăng nhập phải thất bại khi mật khẩu trống");
    }

    @Test
    @DisplayName("TC_AUTH_03: Kiểm tra đăng nhập thất bại khi để trống tên đăng nhập")
    public void testLoginEmptyUsername() {
        String username = "";
        String password = "123";

        boolean isValidUser = authenticate(username, password);
        assertFalse(isValidUser, "Đăng nhập phải thất bại khi username trống");
    }

    @Test
    @DisplayName("TC_AUTH_04: Kiểm tra đăng nhập thất bại khi sai mật khẩu")
    public void testLoginWrongPassword() {
        String username = "admin";
        String password = "wrong_password";

        boolean isValidUser = authenticate(username, password);
        assertFalse(isValidUser, "Đăng nhập phải thất bại khi mật khẩu sai");
    }

    @Test
    @DisplayName("TC_AUTH_05: Kiểm tra tài khoản không tồn tại trong hệ thống")
    public void testLoginNonExistentUser() {
        String username = "unknown_user@example.com";
        String password = "123";

        boolean isValidUser = authenticate(username, password);
        assertFalse(isValidUser, "Đăng nhập phải thất bại khi người dùng không tồn tại");
    }

    // Hàm giả lập (Sinh viên thay bằng gọi trực tiếp Controller / Service của dự án web)
    private boolean authenticate(String user, String pass) {
        if (user == null || user.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            return false;
        }
        return "admin".equals(user) && "123".equals(pass);
    }
}
