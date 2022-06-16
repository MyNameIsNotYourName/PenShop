package hcmute.truongtrangiahung.cuoiky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hcmute.truongtrangiahung.cuoiky.Adapter.ItemChiTietQuanLyAdapter;
import hcmute.truongtrangiahung.cuoiky.Model.DanhMuc;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietQuanLy;
import hcmute.truongtrangiahung.cuoiky.Model.SanPham;
import hcmute.truongtrangiahung.cuoiky.Model.TaiKhoan;
import hcmute.truongtrangiahung.cuoiky.R;

public class QuanLyTaiKhoan extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ItemChiTietQuanLy> arrayList;
    private ItemChiTietQuanLyAdapter adapter;
    private ImageView imageView,  img_Add, img_Edit;

    private ArrayList<TaiKhoan> arrayTaiKhoan  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);
        SetID();
        Event();
    }

    private void Event() {
        TaiDuLieu();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(QuanLyTaiKhoan.this, ChiTietTaiKhoan.class);
                intent.putExtra("taiKhoan", arrayTaiKhoan.get(i).getTenTaiKhoan());
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img_Add.setVisibility(View.GONE);
        img_Edit.setVisibility(View.GONE);
    }

    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoan");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                arrayTaiKhoan.clear();
                if(snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        TaiKhoan taiKhoan = dataSnapshot.getValue(TaiKhoan.class);
                        ItemChiTietQuanLy item = new ItemChiTietQuanLy("0", taiKhoan);
                        arrayList.add(item);
                        arrayTaiKhoan.add(taiKhoan);
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void SetID() {
        listView = findViewById(R.id.list_QuanLySanPham);
        imageView = findViewById(R.id.img_Back);
        arrayList = new ArrayList<>();
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        adapter = new ItemChiTietQuanLyAdapter(this, arrayList, -1);
        listView.setAdapter(adapter);
    }
}