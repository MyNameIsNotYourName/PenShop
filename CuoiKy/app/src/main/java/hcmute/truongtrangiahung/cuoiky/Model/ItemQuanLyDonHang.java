package hcmute.truongtrangiahung.cuoiky.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemQuanLyDonHang {
    private int maDonHang;
    private String email;
    private String ngayDatHang;

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public ItemQuanLyDonHang() {
    }

    public ItemQuanLyDonHang(int maDonHang, String email, String ngayDataHang) {
        this.maDonHang = maDonHang;
        this.email = email;
        this.ngayDatHang = ngayDataHang;
    }
}
