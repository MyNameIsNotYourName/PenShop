package hcmute.truongtrangiahung.cuoiky.Model;

public class HoaDon {
    private int id;
    private String tenTaiKhoan;
    private String ngay;
    private int phiVanChuyen;
    private int tongTien;
    private int trangThai;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getPhiVanChuyen() {
        return phiVanChuyen;
    }

    public void setPhiVanChuyen(int phiVanChuyen) {
        this.phiVanChuyen = phiVanChuyen;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public HoaDon() {
    }

    public HoaDon(int id, String tenTaiKhoan, String ngay, int phiVanChuyen, int tongTien, int trangThai) {
        this.id = id;
        this.tenTaiKhoan = tenTaiKhoan;
        this.ngay = ngay;
        this.phiVanChuyen = phiVanChuyen;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }
}
