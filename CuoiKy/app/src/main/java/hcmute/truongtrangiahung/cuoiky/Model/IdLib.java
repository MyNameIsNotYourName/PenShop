package hcmute.truongtrangiahung.cuoiky.Model;

public class IdLib {
    private int danhMuc;
    private int thuongHieu;
    private int sanPham;

    public int getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(int danhMuc) {
        this.danhMuc = danhMuc;
    }

    public int getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(int thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public int getSanPham() {
        return sanPham;
    }

    public void setSanPham(int sanPham) {
        this.sanPham = sanPham;
    }

    public IdLib() {
    }

    public IdLib(int danhMuc, int thuongHieu, int sanPham) {
        this.danhMuc = danhMuc;
        this.thuongHieu = thuongHieu;
        this.sanPham = sanPham;
    }
}
