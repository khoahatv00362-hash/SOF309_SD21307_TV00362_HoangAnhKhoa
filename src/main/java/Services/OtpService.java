package Services;

import java.security.SecureRandom;

public class OtpService {
    public static String generateOtp() {
        SecureRandom rand = new SecureRandom();
        int otp = 100000 + rand.nextInt(900000);
        return String.valueOf(otp);
    }

    public static void sendOtp(String email, String otp) {
        System.out.println("OTP CODE [" + email + "]: " + otp);
    }
}

