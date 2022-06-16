package hcmute.truongtrangiahung.cuoiky.Model;

public class ThuongHieu {
    private int id;
    private String tenThuongHieu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public ThuongHieu() {
    }

    public ThuongHieu(int id, String tenThuongHieu) {
        this.id = id;
        this.tenThuongHieu = tenThuongHieu;
    }
}
