package Models;

public class ThongKeGiangDay {
    private final int soCa;
    private final int soBuoi;
    private final int tongPhut;

    public ThongKeGiangDay(int soCa, int soBuoi, int tongPhut) {
        this.soCa = soCa;
        this.soBuoi = soBuoi;
        this.tongPhut = tongPhut;
    }

    public int getSoCa() {
        return soCa;
    }

    public int getSoBuoi() {
        return soBuoi;
    }

    public int getTongPhut() {
        return tongPhut;
    }

    public double getSoGio() {
        return Math.round(tongPhut / 60.0 * 10.0) / 10.0;
    }
}

