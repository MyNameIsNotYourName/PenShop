package hcmute.truongtrangiahung.cuoiky.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


import hcmute.truongtrangiahung.cuoiky.Adapter.ItemChiTietDonHangAdapter;
import hcmute.truongtrangiahung.cuoiky.Adapter.ItemQuanLyDonHangAdapter;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietDonHang;
import hcmute.truongtrangiahung.cuoiky.R;

public class ChiTietDonHang extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ItemChiTietDonHang> arrayList;
    private ItemChiTietDonHangAdapter adapter;
    private ImageView imageView, img_Add, img_Edit;

    //sample data
    private String[] tenSanPham = {"Hả cảo", "Pizza", "Vịt nướng chui"};
    private int[] soLuong = {1, 3, 2};
    private String[] gia = {"100.000", "75.000", "200.000"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        SetID();
        Event();
    }

    private void Event() {
        ThemDuLieu();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_Add.setVisibility(View.INVISIBLE);
        img_Edit.setVisibility(View.INVISIBLE);
    }

    private void ThemDuLieu() {
        for(int i = 0; i < 3; i++){
            ItemChiTietDonHang item = new ItemChiTietDonHang();
            item.setHinh(R.drawable.user_icon);
            item.setTenSanPham(tenSanPham[i]);
            item.setSoLuongSanPham(soLuong[i]);
            item.setTongGiaSanPham(gia[i]);

            arrayList.add(item);
        }
    }

    private void SetID() {
        listView = findViewById(R.id.list_DanhSachDonHang);
        imageView = findViewById(R.id.img_Back);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        arrayList = new ArrayList<>();
        adapter = new ItemChiTietDonHangAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }
}
