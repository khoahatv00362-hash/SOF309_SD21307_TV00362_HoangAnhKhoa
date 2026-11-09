package Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter DINH_DANG_NGAY =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DINH_DANG_NGAY_GIO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private DateUtils() {
    }

    public static String dinhDang(LocalDate ngay) {
        return ngay == null ? "" : ngay.format(DINH_DANG_NGAY);
    }

    public static String dinhDang(LocalDateTime ngayGio) {
        return ngayGio == null ? "" : ngayGio.format(DINH_DANG_NGAY_GIO);
    }

    public static LocalDate doc(String chuoi) {
        if (chuoi == null || chuoi.trim().isEmpty()) return null;
        try {
            return LocalDate.parse(chuoi.trim(), DINH_DANG_NGAY);
        } catch (Exception e) {
            return null;
        }
    }
}

