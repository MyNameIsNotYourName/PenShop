package hcmute.truongtrangiahung.cuoiky.Model;

public class ItemChiTietDonHang {
    private int hinh;
    private String tenSanPham;
    private int soLuongSanPham;
    private String tongGiaSanPham;

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuongSanPham() {
        return soLuongSanPham;
    }

    public void setSoLuongSanPham(int soLuongSanPham) {
        this.soLuongSanPham = soLuongSanPham;
    }

    public String getTongGiaSanPham() {
        return tongGiaSanPham;
    }

    public void setTongGiaSanPham(String tongGiaSanPham) {
        this.tongGiaSanPham = tongGiaSanPham;
    }

    public ItemChiTietDonHang() {
    }

    public ItemChiTietDonHang(int hinh, String tenSanPham, int soLuongSanPham, String tongGiaSanPham) {
        this.hinh = hinh;
        this.tenSanPham = tenSanPham;
        this.soLuongSanPham = soLuongSanPham;
        this.tongGiaSanPham = tongGiaSanPham;
    }
}
