package Utils;

import java.util.regex.Pattern;

public final class Validator {

    private static final Pattern MAU_EMAIL =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$");

    private Validator() {
    }

    public static boolean khongRong(String chuoi) {
        return chuoi != null && !chuoi.trim().isEmpty();
    }

    public static boolean doDaiTrongKhoang(String chuoi, int toiThieu, int toiDa) {
        if (chuoi == null) return false;
        int doDai = chuoi.trim().length();
        return doDai >= toiThieu && doDai <= toiDa;
    }

    public static boolean laEmail(String chuoi) {
        return chuoi != null && MAU_EMAIL.matcher(chuoi.trim()).matches();
    }

    public static boolean laSoNguyen(String chuoi) {
        if (!khongRong(chuoi)) return false;
        try {
            Integer.parseInt(chuoi.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean trongKhoang(double giaTri, double min, double max) {
        return giaTri >= min && giaTri <= max;
    }

    public static boolean laSoDienThoai(String chuoi) {
        if (!khongRong(chuoi)) return false;
        String s = chuoi.trim().replace(" ", "");
        return s.matches("^(0|\\+84)\\d{9,10}$");
    }
}

