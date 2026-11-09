package Controllers;

import Models.TieuChiTimKiem;
import Repository.TimKiemRepository.KetQuaSinhVien;
import Services.TimKiemService;
import Utils.Pagination;

import java.util.List;

public class TimKiemController {

    private final TimKiemService service = new TimKiemService();

    public List<KetQuaSinhVien> timSinhVien(TieuChiTimKiem tc) { return service.timSinhVien(tc); }
    public Pagination layPhanTrang(TieuChiTimKiem tc)          { return service.layPhanTrang(tc); }
}

