package Models;

public class ThongBaoNguoiNhan {

    private int maThongbao;
    private int maNguoidung;
    private boolean daDoc;

    public ThongBaoNguoiNhan() {
    }

    public ThongBaoNguoiNhan(int maThongbao, int maNguoidung, boolean daDoc) {
        this.maThongbao = maThongbao;
        this.maNguoidung = maNguoidung;
        this.daDoc = daDoc;
    }

    public int getMaThongbao() {
        return maThongbao;
    }

    public void setMaThongbao(int maThongbao) {
        this.maThongbao = maThongbao;
    }

    public int getMaNguoidung() {
        return maNguoidung;
    }

    public void setMaNguoidung(int maNguoidung) {
        this.maNguoidung = maNguoidung;
    }

    public boolean isDaDoc() {
        return daDoc;
    }

    public void setDaDoc(boolean daDoc) {
        this.daDoc = daDoc;
    }
}

