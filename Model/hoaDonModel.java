
package MVC.Model;


public class hoaDonModel {
    private String maHoaDon;
    private String maBenhNhan;
    private String ngayLapHoaDon;
    private double tongTienBaoHiem;
    private double tongTienDichVu;
    private double tongTienThanhToan;
    private String hinhThucThanhToan;

    // Constructor
    public hoaDonModel(String maHoaDon, String maBenhNhan, String ngayLapHoaDon, double tongTienBaoHiem,
                       double tongTienDichVu, double tongTienThanhToan, String hinhThucThanhToan) {
        this.maHoaDon = maHoaDon;
        this.maBenhNhan = maBenhNhan;
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.tongTienBaoHiem = tongTienBaoHiem;
        this.tongTienDichVu = tongTienDichVu;
        this.tongTienThanhToan = tongTienThanhToan;
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    // Getters and Setters
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getNgayLapHoaDon() {
        return ngayLapHoaDon;
    }

    public void setNgayLapHoaDon(String ngayLapHoaDon) {
        this.ngayLapHoaDon = ngayLapHoaDon;
    }

    public double getTongTienBaoHiem() {
        return tongTienBaoHiem;
    }

    public void setTongTienBaoHiem(double tongTienBaoHiem) {
        this.tongTienBaoHiem = tongTienBaoHiem;
    }

    public double getTongTienDichVu() {
        return tongTienDichVu;
    }

    public void setTongTienDichVu(double tongTienDichVu) {
        this.tongTienDichVu = tongTienDichVu;
    }

    public double getTongTienThanhToan() {
        return tongTienThanhToan;
    }

    public void setTongTienThanhToan(double tongTienThanhToan) {
        this.tongTienThanhToan = tongTienThanhToan; 
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

}
