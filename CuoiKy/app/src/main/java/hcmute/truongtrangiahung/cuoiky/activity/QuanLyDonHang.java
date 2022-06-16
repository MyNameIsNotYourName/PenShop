package hcmute.truongtrangiahung.cuoiky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import hcmute.truongtrangiahung.cuoiky.Adapter.ItemQuanLyDonHangAdapter;
import hcmute.truongtrangiahung.cuoiky.Model.HoaDon;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietQuanLy;
import hcmute.truongtrangiahung.cuoiky.Model.ItemQuanLyDonHang;
import hcmute.truongtrangiahung.cuoiky.Model.SanPham;
import hcmute.truongtrangiahung.cuoiky.Model.TaiKhoan;
import hcmute.truongtrangiahung.cuoiky.R;

public class QuanLyDonHang extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ItemQuanLyDonHang> arrayList;
    private ItemQuanLyDonHangAdapter adapter;
    private ImageView imageView, img_Add, img_Edit;

    private ArrayList<HoaDon> arrayHoaDon = new ArrayList<>();
    private TaiKhoan taiKhoanChinh = new TaiKhoan();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        SetID();
        TaiDuLieu();
        Event();
    }

    private void TaiDuLieu() {
        GetUser("user1");



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("HoaDon");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                arrayHoaDon.clear();
                if(snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        HoaDon hoaDon = dataSnapshot.getValue(HoaDon.class);

                        GetUser(hoaDon.getTenTaiKhoan());
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ItemQuanLyDonHang item = new ItemQuanLyDonHang(hoaDon.getId(), taiKhoanChinh.getEmail(), hoaDon.getNgay());
                                arrayList.add(item);
                                arrayHoaDon.add(hoaDon);
                                adapter.notifyDataSetChanged();
                            }
                        }, 1500);
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
                            System.out.println("Success");
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(QuanLyDonHang.this, ChiTietDonHang.class);
                startActivity(intent);
            }
        });

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
        listView = findViewById(R.id.list_QuanLyDonHang);
        imageView = findViewById(R.id.img_Back);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        arrayList = new ArrayList<>();
        adapter = new ItemQuanLyDonHangAdapter(this, arrayList);

        listView.setAdapter(adapter);
    }
}