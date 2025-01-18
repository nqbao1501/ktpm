package MVC.Model;

public class chiTietHoaDonModel {
    private String maChiTietHoaDon;
    private String maHoaDon;
    private String maDichVu;
    private int soLuong;
    private double thanhTien;
    private double thanhTienBHYT;

    // Constructor
    public chiTietHoaDonModel(String maChiTietHoaDon, String maHoaDon, String maDichVu, int soLuong, double thanhTien, double thanhTienBHYT) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.maHoaDon = maHoaDon;
        this.maDichVu = maDichVu;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.thanhTienBHYT = thanhTienBHYT;
    }

    // Getters and Setters
    public String getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(String maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public double getThanhTienBHYT() {
        return thanhTienBHYT;
    }

    public void setThanhTienBHYT(double thanhTienBHYT) {
        this.thanhTienBHYT = thanhTienBHYT;
    }

}
