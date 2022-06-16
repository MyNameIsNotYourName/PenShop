package hcmute.truongtrangiahung.cuoiky.Model;

public class TaiKhoan {
    private String tenTaiKhoan;
    private String matKhau;
    private String ten;
    private String diaChi;
    private String SDT;
    private String email;
    private boolean quyen;

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public boolean isQuyen() {
        return quyen;
    }

    public void setQuyen(boolean quyen) {
        this.quyen = quyen;
    }

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan, String matKhau, String ten, String diaChi, String SDT, String email, boolean quyen) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.ten = ten;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.email = email;
        this.quyen = quyen;
    }
}
