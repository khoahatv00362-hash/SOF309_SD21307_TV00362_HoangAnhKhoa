package Utils;

public final class MaGiangVien {

    private MaGiangVien() {
    }

    public static String format(int maNguoidung) {
        return String.format("GV%03d", maNguoidung);
    }
}

