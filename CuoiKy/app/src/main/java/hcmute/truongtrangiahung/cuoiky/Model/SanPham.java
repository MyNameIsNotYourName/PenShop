package hcmute.truongtrangiahung.cuoiky.Model;

public class SanPham {
    private int id;
    private String tenSP;
    private int danhMuc;
    private int thuongHieu;
    private String gia;
    private int daBan;
    private int conLai;
    private String moTa;
    private String hinhAnh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

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

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getDaBan() {
        return daBan;
    }

    public void setDaBan(int daBan) {
        this.daBan = daBan;
    }

    public int getConLai() {
        return conLai;
    }

    public void setConLai(int conLai) {
        this.conLai = conLai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public SanPham() {
    }

    public SanPham(int id, String tenSP, int danhMuc, int thuongHieu, String gia, int daBan, int conLai, String moTa, String hinhAnh) {
        this.id = id;
        this.tenSP = tenSP;
        this.danhMuc = danhMuc;
        this.thuongHieu = thuongHieu;
        this.gia = gia;
        this.daBan = daBan;
        this.conLai = conLai;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
    }
}
