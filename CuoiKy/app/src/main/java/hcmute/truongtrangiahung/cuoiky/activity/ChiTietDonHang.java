package hcmute.truongtrangiahung.cuoiky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import hcmute.truongtrangiahung.cuoiky.Model.LoadingDialog;
import hcmute.truongtrangiahung.cuoiky.Model.SanPham;
import hcmute.truongtrangiahung.cuoiky.Model.TaiKhoan;
import hcmute.truongtrangiahung.cuoiky.R;

public class ChiTietDonHang extends AppCompatActivity {
    private ListView listView;
    private TextView txt_MaDonHang, txt_NgayDatHang, txt_Email, txt_DiaChi, txt_TongTien;
    private TextView txt_HuyDonHang, txt_XacNhanThayDoi;
    private Spinner spin_TrangThaiGiaoHang;
    private ArrayList<ItemChiTietDonHang> arrayList;
    private ItemChiTietDonHangAdapter adapter;
    private ImageView imageView, img_Add, img_Edit;
    private TableLayout tableLayout;

    private HoaDon hoaDon = new HoaDon();
    private ArrayList<ChiTietHoaDon> chiTietHoaDonArrayList = new ArrayList<>();
    private ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();
    private ArrayAdapter<String> adapterSpinner;
    private ArrayList<String> arrayListSpinner = new ArrayList<>();
    private int indexSpinner = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        SetID();
        GetIntent();
        TaiDuLieu();
        LoadSpinnerData();
        Event();
    }

    private void LoadSpinnerData() {

    }

    private void GetIntent() {
        Intent intent = getIntent();
        hoaDon.setId( intent.getIntExtra("hoaDon", -1));
    }

    private void TaiDuLieu() {
        chiTietHoaDonArrayList.clear();
        sanPhamArrayList.clear();
        arrayList.clear();
        adapter.notifyDataSetChanged();
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
                    GetChiTietHoaDon(hoaDon.getId());
                    final Handler handler = new Handler();
                    final LoadingDialog dialog = new LoadingDialog(ChiTietDonHang.this);
                    dialog.startLoadingDialog();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txt_DiaChi.setText(hoaDon.getDiaChi());
                            txt_Email.setText(hoaDon.getEmail());
                            String tongTien = hoaDon.getTongTien() + " VND";
                            txt_TongTien.setText(tongTien);
                            txt_MaDonHang.setText(String.valueOf(hoaDon.getId()));
                            txt_NgayDatHang.setText(hoaDon.getNgay());
                            if(hoaDon.getTrangThai() < 4)
                                spin_TrangThaiGiaoHang.setSelection(hoaDon.getTrangThai());
                            else {
                                arrayListSpinner.clear();
                                adapterSpinner.clear();
                                arrayListSpinner.add("Đã hủy");
                                adapterSpinner.notifyDataSetChanged();
                                spin_TrangThaiGiaoHang.setSelection(0);
                                spin_TrangThaiGiaoHang.setEnabled(false);
                                tableLayout.setVisibility(View.GONE);
                            }

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
                            dialog.dismissLoadingDialog();
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


    private void Event() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_Add.setVisibility(View.INVISIBLE);
        img_Edit.setVisibility(View.INVISIBLE);

        spin_TrangThaiGiaoHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexSpinner = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                indexSpinner = hoaDon.getTrangThai();
            }
        });

        txt_XacNhanThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoaDon.setTrangThai(indexSpinner);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("HoaDon/" + hoaDon.getId()+"/trangThai");
                myRef.setValue(hoaDon.getTrangThai(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getApplicationContext(), "Đã lưu", Toast.LENGTH_SHORT).show();
                        TaiDuLieu();
                    }
                });
            }
        });

        txt_HuyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoaDon.setTrangThai(4);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("HoaDon/" + hoaDon.getId()+"/trangThai");
                myRef.setValue(hoaDon.getTrangThai(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getApplicationContext(), "Đã hủy", Toast.LENGTH_SHORT).show();
                        TaiDuLieu();
                    }
                });
            }
        });
    }


    private void SetID() {
        txt_MaDonHang = findViewById(R.id.txt_MaDonHang);
        txt_NgayDatHang = findViewById(R.id.txt_NgayDatHang);
        txt_Email = findViewById(R.id.txt_Email);
        txt_DiaChi = findViewById(R.id.txt_DiaChi);
        txt_TongTien = findViewById(R.id.txt_TongTien);
        txt_HuyDonHang = findViewById(R.id.txt_HuyDonHang);
        txt_XacNhanThayDoi = findViewById(R.id.txt_XacNhanThayDoi);
        spin_TrangThaiGiaoHang = findViewById(R.id.spin_TrangThaiGiaoHang);
        listView = findViewById(R.id.list_DanhSachDonHang);
        imageView = findViewById(R.id.img_Back);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        tableLayout = findViewById(R.id.temp1);
        arrayList = new ArrayList<>();
        adapter = new ItemChiTietDonHangAdapter(this, arrayList);
        listView.setAdapter(adapter);

        ThemDuLieuArraySpinner();
        adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListSpinner);
        spin_TrangThaiGiaoHang.setAdapter(adapterSpinner);
    }

    private void ThemDuLieuArraySpinner() {
        arrayListSpinner.add("Chờ xác nhận");
        arrayListSpinner.add("Đã xác nhận");
        arrayListSpinner.add("Đang vận chuyển");
        arrayListSpinner.add("Giao hàng thành công");
    }
}
