package hcmute.truongtrangiahung.cuoiky.Model;

public class ItemChiTietQuanLy {
    private String hinh;
    private String ten;
    private DanhMuc danhMuc;
    private ThuongHieu thuongHieu;
    private SanPham sanPham;
    private TaiKhoan taiKhoan; //dung tam
    private int flag;

    public ItemChiTietQuanLy(String hinh, TaiKhoan taiKhoan) {
        this.hinh = hinh;
        this.taiKhoan = taiKhoan;
        this.flag = 4;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }


    public ItemChiTietQuanLy(String hinh, SanPham sanPham) {
        this.hinh = hinh;
        this.sanPham = sanPham;
        this.flag = 1;
    }

    public ItemChiTietQuanLy(String hinh, ThuongHieu thuongHieu) {
        this.hinh = hinh;
        this.thuongHieu = thuongHieu;
        this.flag = 3;
    }



    public int getFlag() {
        return flag;
    }

    public ThuongHieu getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(ThuongHieu thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public ItemChiTietQuanLy(String hinh, DanhMuc danhMuc) {
        this.hinh = hinh;
        this.danhMuc = danhMuc;
        this.flag = 2;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public ItemChiTietQuanLy() {
    }

    public ItemChiTietQuanLy(String hinh, String ten) {
        this.hinh = hinh;
        this.ten = ten;
    }
}
