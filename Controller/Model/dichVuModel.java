package MVC.Model;

public class dichVuModel {
    private String maDichVu;
    private String tenDichVu;
    private String loaiDichVu;
    private double giaTien;
    private float heSoBaoHiem;

    public dichVuModel(String maDichVu, String tenDichVu, String loaiDichVu, double giaTien, float heSoBaoHiem)
    {
    	this.maDichVu = maDichVu;
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.loaiDichVu = loaiDichVu;
        this.giaTien = giaTien;
        this.heSoBaoHiem = heSoBaoHiem;
    }
    // Getters and setters
 
    public String getMaDichVu() {
        return maDichVu;
    }
 
    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getLoaiDichVu() {
        return loaiDichVu;
    }

    public void setLoaiDichVu(String loaiDichVu) {
        this.loaiDichVu = loaiDichVu;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public float getHeSoBaoHiem() {
        return heSoBaoHiem;
    }

    public void setHeSoBaoHiem(float heSoBaoHiem) {
        this.heSoBaoHiem = heSoBaoHiem;
    }

}
