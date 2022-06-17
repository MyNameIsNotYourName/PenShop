package hcmute.truongtrangiahung.cuoiky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
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
import java.util.List;

import hcmute.truongtrangiahung.cuoiky.Adapter.ItemChiTietQuanLyAdapter;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietQuanLy;
import hcmute.truongtrangiahung.cuoiky.Model.LoadingDialog;
import hcmute.truongtrangiahung.cuoiky.Model.SanPham;
import hcmute.truongtrangiahung.cuoiky.R;

public class QuanLySanPham extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ItemChiTietQuanLy> arrayList;
    private ItemChiTietQuanLyAdapter adapter;
    private ImageView imageView, img_Add, img_Edit;

    private ArrayList<SanPham> arraySanPham = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        SetID();
        TaiDuLieu();
        Event();
    }

    // thực thi các sự kiến khi người dùng thao tác
    private void Event() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLySanPham.this, ThemSanPham.class);
                startActivity(intent);
            }
        });

        img_Edit.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(QuanLySanPham.this, ChiTietSanPham.class);
                intent.putExtra("sanPham", arraySanPham.get(i).getId());
                startActivity(intent);
            }
        });
    }

    // Lấy dữ liệu từ Firebase để gán vào các biến
    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SanPham");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                arraySanPham.clear();
                if(snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        final Handler handler = new Handler();
                        final LoadingDialog dialog = new LoadingDialog(QuanLySanPham.this);
                        dialog.startLoadingDialog();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                                ItemChiTietQuanLy item = new ItemChiTietQuanLy(sanPham.getHinhAnh(), sanPham);
                                arrayList.add(item);
                                arraySanPham.add(sanPham);
                                adapter.notifyDataSetChanged();
                                dialog.dismissLoadingDialog();
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

    // Gán id vào các biến, cài đặt adapter và thiết lập ban đầu
    private void SetID() {
        listView = findViewById(R.id.list_QuanLySanPham);
        imageView = findViewById(R.id.img_Back);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        arrayList = new ArrayList<>();
        adapter = new ItemChiTietQuanLyAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }

}