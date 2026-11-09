package Models;

public class GmailToken {
    private String email;
    private String accessToken;

    public GmailToken() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}

