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

import hcmute.truongtrangiahung.cuoiky.Model.LoadingDialog;
import hcmute.truongtrangiahung.cuoiky.Model.ThuongHieu;
import hcmute.truongtrangiahung.cuoiky.R;

public class ChiTietThuongHieu extends AppCompatActivity {
    private ImageView img_Add, img_Back, img_Edit;
    private EditText edt_TenThuongHieu;
    private TextView txt_MaThuongHieu, txt_HuyThayDoi, txt_LuuThayDoi;
    private TableLayout table_LuuThayDoi;

    private ThuongHieu thuongHieu = new ThuongHieu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thuong_hieu);
        SetID();
        GetIntent();
        TaiDuLieu();
        Event();
    }

    // Lấy dữ liệu từ Firebase và lưu vào các biến
    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ThuongHieu");

        String id = String.valueOf(thuongHieu.getId());

        Query query = myRef.orderByChild(id);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                final Handler handler = new Handler();
                final LoadingDialog dialog = new LoadingDialog(ChiTietThuongHieu.this);
                dialog.startLoadingDialog();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String tempID = snapshot.getKey();
                        System.out.println("Id: " + tempID);
                        if(tempID.equals(id))
                        {
                            String temp = snapshot.getValue(String.class);
                            thuongHieu.setTenThuongHieu(temp);

                            txt_MaThuongHieu.setText(String.valueOf(thuongHieu.getId()));
                            edt_TenThuongHieu.setText(thuongHieu.getTenThuongHieu());
                            //System.exit(0);
                        }
                        dialog.dismissLoadingDialog();
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

    // Lấy dữ liệu từ QuanLyThuongHieu
    private void GetIntent() {
        Intent intent = getIntent();
        thuongHieu.setId( intent.getIntExtra("thuongHieu", -1));
    }

    // thực thi các sự kiện khi người dùng thao tác
    private void Event() {
        img_Add.setVisibility(View.INVISIBLE);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_TenThuongHieu.setEnabled(true);
                edt_TenThuongHieu.setFocusable(true);
                table_LuuThayDoi.setVisibility(View.VISIBLE);
            }
        });

        txt_HuyThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CaiDatMacDinh();
            }
        });

        txt_LuuThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LuuThayDoi();
            }
        });
    }

    // Cài đặt dữ liệu và hiển thị mặc định
    private void CaiDatMacDinh() {
        edt_TenThuongHieu.setEnabled(false);
        table_LuuThayDoi.setVisibility(View.INVISIBLE);
        edt_TenThuongHieu.setText(thuongHieu.getTenThuongHieu());
    }

    // lưu những thay đổi và cập nhật lên Firebase
    private void LuuThayDoi() {
        //Lấy id danh mục
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ThuongHieu/"+thuongHieu.getId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Thêm dữ liệu
                thuongHieu.setTenThuongHieu(edt_TenThuongHieu.getText().toString());
                myRef.setValue(thuongHieu.getTenThuongHieu(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ChiTietThuongHieu.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                        edt_TenThuongHieu.setFocusable(false);
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

    // Gán id cho các biến
    private void SetID() {
        img_Add = findViewById(R.id.img_Add);
        img_Back = findViewById(R.id.img_Back);
        img_Edit = findViewById(R.id.img_Edit);
        table_LuuThayDoi = findViewById(R.id.table_LuuThayDoi);
        edt_TenThuongHieu = findViewById(R.id.edt_TenThuongHieu);
        edt_TenThuongHieu.setFocusable(false);
        txt_MaThuongHieu = findViewById(R.id.txt_MaThuongHieu);
        txt_HuyThayDoi = findViewById(R.id.txt_HuyThayDoi);
        txt_LuuThayDoi = findViewById(R.id.txt_LuuThayDoi);

    }
}