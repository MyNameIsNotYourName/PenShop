package hcmute.truongtrangiahung.cuoiky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import hcmute.truongtrangiahung.cuoiky.Adapter.ItemChiTietDonHangAdapter;
import hcmute.truongtrangiahung.cuoiky.Adapter.ItemQuanLyDonHangAdapter;
import hcmute.truongtrangiahung.cuoiky.Model.ChiTietHoaDon;
import hcmute.truongtrangiahung.cuoiky.Model.HoaDon;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietDonHang;
import hcmute.truongtrangiahung.cuoiky.Model.ItemQuanLyDonHang;
import hcmute.truongtrangiahung.cuoiky.Model.SanPham;
import hcmute.truongtrangiahung.cuoiky.Model.TaiKhoan;
import hcmute.truongtrangiahung.cuoiky.R;

public class ChiTietDonHang extends AppCompatActivity {
    private ListView listView;
    private TextView txt_MaDonHang, txt_NgayDatHang, txt_Email, txt_DiaChi, txt_TongTien;
    private ArrayList<ItemChiTietDonHang> arrayList;
    private ItemChiTietDonHangAdapter adapter;
    private ImageView imageView, img_Add, img_Edit;

    private HoaDon hoaDon = new HoaDon();
    private ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList = new ArrayList<>();
    private TaiKhoan taiKhoanChinh = new TaiKhoan();
    private ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        SetID();
        GetIntent();
        //GetChiTietHoaDon(2);
        TaiDuLieu();
        Event();
    }

    private void GetIntent() {
        Intent intent = getIntent();
        hoaDon.setId( intent.getIntExtra("hoaDon", -1));
    }

    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("HoaDon");

        String id = String.valueOf(hoaDon.getId());

        Query query = myRef.orderByChild(id);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HoaDon temp = snapshot.getValue(HoaDon.class);
                if(String.valueOf(temp.getId()).equals(id))
                {
                    hoaDon = temp;
                    GetUser(hoaDon.getTenTaiKhoan());
                    GetChiTietHoaDon(hoaDon.getId());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txt_DiaChi.setText(taiKhoanChinh.getDiaChi());
                            txt_Email.setText(taiKhoanChinh.getEmail());
                            String tongTien = hoaDon.getTongTien() + " VND";
                            txt_TongTien.setText(tongTien);
                            txt_MaDonHang.setText(String.valueOf(hoaDon.getId()));
                            txt_NgayDatHang.setText(hoaDon.getNgay());

                            for(int i = 0; i < chiTietHoaDonArrayList.size(); i++){
                                ItemChiTietDonHang itemChiTietDonHang = new ItemChiTietDonHang();
                                itemChiTietDonHang.setHinh(sanPhamArrayList.get(i).getHinhAnh());
                                itemChiTietDonHang.setSoLuongSanPham(chiTietHoaDonArrayList.get(i).getSoLuong());
                                itemChiTietDonHang.setTenSanPham(sanPhamArrayList.get(i).getTenSP());
                                int tongTienSanPham = chiTietHoaDonArrayList.get(i).getSoLuong() * Integer.parseInt(sanPhamArrayList.get(i).getGia());
                                itemChiTietDonHang.setTongGiaSanPham(String.valueOf(tongTienSanPham));
                                arrayList.add(itemChiTietDonHang);
                            }

                            adapter.notifyDataSetChanged();

                        }
                    }, 1500);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GetChiTietHoaDon(int id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ChiTietHoaDon");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        Map<String, Long> temp = (Map<String, Long>) dataSnapshot.getValue();
                        int key = Integer.parseInt(dataSnapshot.getKey());
                        if (key==id) {
                            chiTietHoaDonArrayList.clear();
                            for(DataSnapshot childSnapshot: dataSnapshot.getChildren())
                            {
                                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                                long soLuong =  temp.get(childSnapshot.getKey());
                                int idSanPham = Integer.valueOf(childSnapshot.getKey());

                                chiTietHoaDon.setIdHoaDon(id);
                                chiTietHoaDon.setIdSanPham(idSanPham);
                                chiTietHoaDon.setSoLuong((int) soLuong);

                                chiTietHoaDonArrayList.add(chiTietHoaDon);
                            }

                            GetSanPham();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void GetSanPham() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SanPham");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() > 0) {
                    sanPhamArrayList.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        for(int i = 0; i < chiTietHoaDonArrayList.size(); i++)
                        {
                            String idSnapShot = dataSnapshot.getKey();
                            String idSanPham = String.valueOf(chiTietHoaDonArrayList.get(i).getIdSanPham());
                            if(idSnapShot.equals(idSanPham)){
                                SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                                sanPhamArrayList.add(sanPham);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void GetUser(String tenTaiKhoan) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoan");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                        if (taiKhoan.getTenTaiKhoan().equals(tenTaiKhoan)) {
                            taiKhoanChinh = taiKhoan;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void Event() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_Add.setVisibility(View.INVISIBLE);
        img_Edit.setVisibility(View.INVISIBLE);
    }


    private void SetID() {
        txt_MaDonHang = findViewById(R.id.txt_MaDonHang);
        txt_NgayDatHang = findViewById(R.id.txt_NgayDatHang);
        txt_Email = findViewById(R.id.txt_Email);
        txt_DiaChi = findViewById(R.id.txt_DiaChi);
        txt_TongTien = findViewById(R.id.txt_TongTien);
        listView = findViewById(R.id.list_DanhSachDonHang);
        imageView = findViewById(R.id.img_Back);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        arrayList = new ArrayList<>();
        adapter = new ItemChiTietDonHangAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }
}
