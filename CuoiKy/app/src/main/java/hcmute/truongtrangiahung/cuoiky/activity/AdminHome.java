package hcmute.truongtrangiahung.cuoiky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hcmute.truongtrangiahung.cuoiky.Model.ChiTietHoaDon;
import hcmute.truongtrangiahung.cuoiky.Model.HoaDon;
import hcmute.truongtrangiahung.cuoiky.Model.IdLib;
import hcmute.truongtrangiahung.cuoiky.Model.TaiKhoan;
import hcmute.truongtrangiahung.cuoiky.R;

public class AdminHome extends AppCompatActivity {
    private CardView quanLyDonHang, quanLySanPham, quanLyDanhMuc, quanLyThuongHieu, quanLyTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        SetID();
        Event();

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoan/user1");
        TaiKhoan taiKhoan = new TaiKhoan("user1", "1", "1", "1", "1", "1", false);
        myRef.setValue(taiKhoan);
        myRef = database.getReference("TaiKhoan/user2");
        TaiKhoan taiKhoan2 = new TaiKhoan("user2", "2", "2", "2", "2", "2", false);
        myRef.setValue(taiKhoan2);*/


      /*  FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("HoaDon/1");
        HoaDon hoaDon = new HoaDon(1, "user1", "15/1/2022", 1, 1, "1", "1", "1", "1", "1", 0);
        myRef.setValue(hoaDon);
        myRef = database.getReference("HoaDon/2");
        HoaDon hoaDon2 = new HoaDon(2, "user2", "52/21/2022", 2, 2, "2", "2", "2", "2", "2",0);
        myRef.setValue(hoaDon2);*/

        /*myRef = database.getReference("ChiTietHoaDon/1/2");
        myRef.setValue(2);
        myRef = database.getReference("ChiTietHoaDon/2/4");
        myRef.setValue(1);
        myRef = database.getReference("ChiTietHoaDon/2/5");
        myRef.setValue(3);*/

    }

    private void Event() {
        HieuUngKhiClick();
        ChuyenTrang();

    }

    private void ChuyenTrang() {
        quanLyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLyDonHang.class);
                startActivity(intent);
            }
        });
        quanLySanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLySanPham.class);
                startActivity(intent);
            }
        });
        quanLyThuongHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLyThuongHieu.class);
                startActivity(intent);
            }
        });
        quanLyDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLyDanhMuc.class);
                startActivity(intent);
            }
        });
        quanLyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, QuanLyTaiKhoan.class);
                startActivity(intent);
            }
        });
    }


    private void SetID() {
        quanLyDonHang = findViewById(R.id.card_QuanLyDonHang);
        quanLySanPham = findViewById(R.id.card_QuanLySanPham);
        quanLyDanhMuc = findViewById(R.id.card_QuanLyDanhMuc);
        quanLyThuongHieu = findViewById(R.id.card_QuanLyThuongHieu);
        quanLyTaiKhoan = findViewById(R.id.card_QuanLyTaiKhoan);
    }

    private void HieuUngKhiClick() {
        quanLyDonHang.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.getBackground().setColorFilter(0xe033ccff, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        });
        quanLySanPham.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.getBackground().setColorFilter(0xe033ccff, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        });
        quanLyDanhMuc.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.getBackground().setColorFilter(0xe033ccff, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        });
        quanLyThuongHieu.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.getBackground().setColorFilter(0xe033ccff, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        });
        quanLyTaiKhoan.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.getBackground().setColorFilter(0xe033ccff, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        });

    }
}