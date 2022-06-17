package hcmute.truongtrangiahung.cuoiky.Model;

public class HoaDon {
    private int id;
    private String tenTaiKhoan;
    private String ngay;
    private int phiVanChuyen;
    private int tongTien;
    private String ten;
    private String SDT;
    private String email;
    private String DiaChi;
    private String ghiChu;
    private int trangThai;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public HoaDon(int id, String tenTaiKhoan, String ngay, int phiVanChuyen, int tongTien, String ten, String SDT, String email, String diaChi, String ghiChu, int trangThai) {
        this.id = id;
        this.tenTaiKhoan = tenTaiKhoan;
        this.ngay = ngay;
        this.phiVanChuyen = phiVanChuyen;
        this.tongTien = tongTien;
        this.ten = ten;
        this.SDT = SDT;
        this.email = email;
        DiaChi = diaChi;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

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
