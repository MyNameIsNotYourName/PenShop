package hcmute.truongtrangiahung.cuoiky.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hcmute.truongtrangiahung.cuoiky.Model.DanhMuc;
import hcmute.truongtrangiahung.cuoiky.Model.IdLib;
import hcmute.truongtrangiahung.cuoiky.Model.ThuongHieu;
import hcmute.truongtrangiahung.cuoiky.R;

public class ThemThuongHieu extends AppCompatActivity {
    private EditText edt_TenThuongHieu;
    private CardView btn_Luu;
    private ImageView img_Add, img_Edit, img_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thuong_hieu);
        SetID();
        Event();
    }
    private void Event() {
        img_Add.setVisibility(View.INVISIBLE);
        img_Edit.setVisibility(View.INVISIBLE);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_Luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemDuLieu();
            }
        });
    }

    private void ThemDuLieu() {
        //Lấy id danh mục
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("IdLib");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                IdLib idLib = snapshot.getValue(IdLib.class);
                //Thêm dữ liệu
                ThuongHieu thuongHieu = new ThuongHieu(idLib.getThuongHieu(), edt_TenThuongHieu.getText().toString());
                DatabaseReference myRef = database.getReference("ThuongHieu/"+thuongHieu.getId());
                DatabaseReference myRef1 = database.getReference("IdLib/thuongHieu");
                myRef.setValue(thuongHieu.getTenThuongHieu(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ThemThuongHieu.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                        CaiDatMacDinh();
                    }
                });
                myRef1.setValue(idLib.getThuongHieu()+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CaiDatMacDinh() {
        edt_TenThuongHieu.setText("");
    }

    private void SetID() {
        edt_TenThuongHieu = findViewById(R.id.edt_TenThuongHieu);
        btn_Luu = findViewById(R.id.btn_Luu);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        img_Back = findViewById(R.id.img_Back);
    }
}