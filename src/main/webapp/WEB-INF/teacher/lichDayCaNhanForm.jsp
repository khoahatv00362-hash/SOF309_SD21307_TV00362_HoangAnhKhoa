<%@ page import="Models.LichDayGiangVien" %>
<%@ page import="Utils.Html" %>
<%-- form them / sua mot lich day ca nhan --%>
<%
    request.setAttribute("trang", "lichday_gv");
    request.setAttribute("tieuDeTrang", "Lịch dạy");
%>
<%@ include file="/WEB-INF/header.jsp" %>

<%
    // neu co "lich" tuc la dang sua, nguoc lai la them moi
    LichDayGiangVien lich = (LichDayGiangVien) request.getAttribute("lich");
    boolean laSua = (lich != null);
    String urlGoc = request.getContextPath();

    // lay gia tri san co de dien vao form (khi sua)
    String vMaLichday = laSua ? String.valueOf(lich.getMaLichday()) : "";
    String vNgay      = (laSua && lich.getNgay() != null) ? lich.getNgay().toString() : "";
    String vTieuDe    = laSua ? (lich.getTieuDe() == null ? "" : lich.getTieuDe()) : "";
    String vNoiDung   = laSua ? (lich.getNoiDung() == null ? "" : lich.getNoiDung()) : "";
    String vGioBd     = (laSua && lich.getGioBatdau() != null)  ? lich.getGioBatdau().toString()  : "";
    String vGioKt     = (laSua && lich.getGioKetthuc() != null) ? lich.getGioKetthuc().toString() : "";
    String vLoai      = laSua ? (lich.getLoaiLich() == null ? "VanPhong" : lich.getLoaiLich()) : "VanPhong";
    String vTrangThai = laSua ? (lich.getTrangThai() == null ? "DaXepLich" : lich.getTrangThai()) : "DaXepLich";

    // danh sach loai lich + nhan
    String[] maLoai  = {"GiangDay", "VanPhong", "Hop", "TuVan", "Khac"};
    String[] tenLoai = {"Giảng dạy", "Văn phòng", "Họp", "Tư vấn", "Khác"};
    String[] maTT    = {"DaXepLich", "DangDienRa", "DaHoanThanh", "Huy"};
    String[] tenTT   = {"Đã xếp lịch", "Đang diễn ra", "Đã hoàn thành", "Hủy"};
%>

<h1 class="trang-tieude"><%= laSua ? "Sửa lịch" : "Thêm lịch mới" %></h1>
<p class="trang-mota">Nhập thông tin buổi/hoạt động. Cần có giờ bắt đầu và giờ kết thúc.</p>

<div class="the">
    <form method="post" action="<%= urlGoc %>/giangvien/lichday">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="maLichday" value="<%= Html.escape(vMaLichday) %>">

        <div class="nhom-form">
            <label for="ngay">Ngày</label>
            <input type="date" id="ngay" name="ngay" class="o-nhap" min="<%= java.time.LocalDate.now() %>" value="<%= Html.escape(vNgay) %>" required>
        </div>

        <div class="nhom-form">
            <label for="loaiLich">Loại lịch</label>
            <select id="loaiLich" name="loaiLich" class="o-nhap">
                <%
                    for (int i = 0; i < maLoai.length; i++) {
                %>
                    <option value="<%= maLoai[i] %>" <%= maLoai[i].equals(vLoai) ? "selected" : "" %>><%= tenLoai[i] %></option>
                <% } %>
            </select>
        </div>

        <div class="nhom-form">
            <label for="tieuDe">Tiêu đề</label>
            <input type="text" id="tieuDe" name="tieuDe" class="o-nhap" value="<%= Html.escape(vTieuDe) %>" required>
        </div>

        <div class="nhom-form">
            <label for="gioBatDau">Giờ bắt đầu</label>
            <input type="time" id="gioBatDau" name="gioBatDau" class="o-nhap" value="<%= Html.escape(vGioBd) %>">
        </div>

        <div class="nhom-form">
            <label for="gioKetThuc">Giờ kết thúc</label>
            <input type="time" id="gioKetThuc" name="gioKetThuc" class="o-nhap" value="<%= Html.escape(vGioKt) %>">
        </div>

        <div class="nhom-form">
            <label for="trangThai">Trạng thái</label>
            <select id="trangThai" name="trangThai" class="o-nhap">
                <%
                    for (int i = 0; i < maTT.length; i++) {
                %>
                    <option value="<%= maTT[i] %>" <%= maTT[i].equals(vTrangThai) ? "selected" : "" %>><%= tenTT[i] %></option>
                <% } %>
            </select>
        </div>

        <div class="nhom-form" style="max-width:100%;">
            <label for="noiDung">Nội dung / ghi chú</label>
            <textarea id="noiDung" name="noiDung" class="o-nhap" rows="3"><%= Html.escape(vNoiDung) %></textarea>
        </div>

        <button type="submit" class="nut"><%= laSua ? "Cập nhật" : "Thêm lịch" %></button>
        <a class="nut nut-xam" href="<%= urlGoc %>/giangvien/lichday">Hủy</a>
    </form>
</div>

<%@ include file="/WEB-INF/footer.jsp" %>
