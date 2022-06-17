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

import java.util.ArrayList;

import hcmute.truongtrangiahung.cuoiky.Adapter.ItemChiTietQuanLyAdapter;
import hcmute.truongtrangiahung.cuoiky.Model.DanhMuc;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietQuanLy;
import hcmute.truongtrangiahung.cuoiky.Model.LoadingDialog;
import hcmute.truongtrangiahung.cuoiky.Model.ThuongHieu;
import hcmute.truongtrangiahung.cuoiky.R;

public class QuanLyThuongHieu extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ItemChiTietQuanLy> arrayList;
    private ItemChiTietQuanLyAdapter adapter;
    private ImageView imageView, img_Add, img_Edit;

    private ArrayList<ThuongHieu> arrayThuongHieu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_thuong_hieu);

        SetID();
        Event();
    }

    private void Event() {
        TaiDuLieu();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(QuanLyThuongHieu.this, ChiTietThuongHieu.class);
                intent.putExtra("thuongHieu", arrayThuongHieu.get(i).getId());
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyThuongHieu.this, ThemThuongHieu.class);
                startActivity(intent);
            }
        });

        img_Edit.setVisibility(View.GONE);
    }

    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ThuongHieu");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                arrayThuongHieu.clear();
                if(snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        final Handler handler = new Handler();
                        final LoadingDialog dialog = new LoadingDialog(QuanLyThuongHieu.this);
                        dialog.startLoadingDialog();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ThuongHieu thuongHieu = new ThuongHieu();
                                thuongHieu.setTenThuongHieu(dataSnapshot.getValue(String.class));
                                thuongHieu.setId(Integer.parseInt(dataSnapshot.getKey()));
                                ItemChiTietQuanLy item = new ItemChiTietQuanLy("0", thuongHieu);
                                arrayList.add(item);
                                arrayThuongHieu.add(thuongHieu);
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
        adapter.notifyDataSetChanged();
    }

    private void SetID() {
        listView = findViewById(R.id.list_QuanLySanPham);
        imageView = findViewById(R.id.img_Back);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        arrayList = new ArrayList<>();
        adapter = new ItemChiTietQuanLyAdapter(this, arrayList, -1);
        listView.setAdapter(adapter);
    }
}