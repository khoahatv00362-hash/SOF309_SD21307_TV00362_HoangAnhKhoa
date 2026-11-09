package Controllers;

import Services.GoogleOAuthService;

public class GmailOAuthController {

    private final GoogleOAuthService service = new GoogleOAuthService();

    public String taoDuongDanDangNhap(String state) {
        return service.buildAuthUrl(state);
    }
}

