package hcmute.truongtrangiahung.cuoiky.Model;

public class ChiTietHoaDon {
    private int idHoaDon;
    private int idSanPham;
    private int soLuong;

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int idHoaDon, int idSanPham, int soLuong) {
        this.idHoaDon = idHoaDon;
        this.idSanPham = idSanPham;
        this.soLuong = soLuong;
    }
}
