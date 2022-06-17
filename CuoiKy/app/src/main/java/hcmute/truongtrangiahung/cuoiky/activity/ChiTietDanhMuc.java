package hcmute.truongtrangiahung.cuoiky.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import hcmute.truongtrangiahung.cuoiky.Model.DanhMuc;
import hcmute.truongtrangiahung.cuoiky.Model.IdLib;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietQuanLy;
import hcmute.truongtrangiahung.cuoiky.Model.LoadingDialog;
import hcmute.truongtrangiahung.cuoiky.R;

public class ChiTietDanhMuc extends AppCompatActivity {
    private ImageView img_Add, img_Back, img_Edit;
    private EditText edt_TenDanhMuc;
    private TextView txt_MaDanhMuc, txt_HuyThayDoi, txt_LuuThayDoi;
    private TableLayout table_LuuThayDoi;

    private DanhMuc danhMuc = new DanhMuc(); // Chứa dữ liệu từ intent chuyển qua

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_danh_muc);
        SetID();
        GetIntent();
        TaiDuLieu();
        Event();
    }

    // Tải dữ liệu lưu vào các View từ firebase
    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DanhMuc");

        String id = String.valueOf(danhMuc.getId());

        Query query = myRef.orderByChild(id);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                final Handler handler = new Handler();
                final LoadingDialog dialog = new LoadingDialog(ChiTietDanhMuc.this);
                dialog.startLoadingDialog(); // Hiển thị hoạt ảnh loading
                handler.postDelayed(new Runnable() { // thực thi các lệnh trong hàm run sau 1,5s
                    @Override
                    public void run() {
                        String tempID = snapshot.getKey();
                        if(tempID.equals(id))
                        {
                            String temp = snapshot.getValue(String.class);
                            danhMuc.setTenDanhMuc(temp);

                            txt_MaDanhMuc.setText(String.valueOf(danhMuc.getId()));
                            edt_TenDanhMuc.setText(danhMuc.getTenDanhMuc());
                        }
                        dialog.dismissLoadingDialog(); // tắt hoạt ảnh loading
                    }
                }, 1500);
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

    // Nhận giá trị DanhMuc từ activity QuanLyDanhMuc
    private void GetIntent() {
        Intent intent = getIntent();
        danhMuc.setId( intent.getIntExtra("danhMuc", -1));
    }

    // Thực thi các sự kiện khi người dùng thao tác với các View
    private void Event() {
        // Ẩn img_Add
        img_Add.setVisibility(View.GONE);

        // Đóng Activity
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Cho phép thao tác với các View
        img_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_TenDanhMuc.setEnabled(true);
                edt_TenDanhMuc.setFocusable(true);
                table_LuuThayDoi.setVisibility(View.VISIBLE);
            }
        });

        // Thực thi hàm CaiDatMacDinh
        txt_HuyThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaiDatMacDinh();
            }
        });

        // Thực thi hàm LuuThayDoi
        txt_LuuThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LuuThayDoi();
            }
        });
    }

    // Trở về cài đăt mặc định, các View không thể thao tác
    private void CaiDatMacDinh() {
        edt_TenDanhMuc.setEnabled(false);
        table_LuuThayDoi.setVisibility(View.INVISIBLE);
        edt_TenDanhMuc.setText(danhMuc.getTenDanhMuc());
    }

    //Lưu dữ liệu thay đổi vào firebase
    private void LuuThayDoi() {
        //Lấy id danh mục
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DanhMuc/"+danhMuc.getId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Thêm dữ liệu
                danhMuc.setTenDanhMuc(edt_TenDanhMuc.getText().toString());
                myRef.setValue(danhMuc.getTenDanhMuc(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ChiTietDanhMuc.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                        edt_TenDanhMuc.setFocusable(false);
                        table_LuuThayDoi.setVisibility(View.INVISIBLE);
                        TaiDuLieu();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Gán id vào các biến
    private void SetID() {
        img_Add = findViewById(R.id.img_Add);
        img_Back = findViewById(R.id.img_Back);
        img_Edit = findViewById(R.id.img_Edit);
        table_LuuThayDoi = findViewById(R.id.table_LuuThayDoi);
        edt_TenDanhMuc = findViewById(R.id.edt_TenDanhMuc);
        edt_TenDanhMuc.setFocusable(false);
        txt_MaDanhMuc = findViewById(R.id.txt_MaDanhMuc);
        txt_HuyThayDoi = findViewById(R.id.txt_HuyThayDoi);
        txt_LuuThayDoi = findViewById(R.id.txt_LuuThayDoi);
    }
}