package hcmute.truongtrangiahung.cuoiky.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import hcmute.truongtrangiahung.cuoiky.Model.IdLib;
import hcmute.truongtrangiahung.cuoiky.Model.SanPham;
import hcmute.truongtrangiahung.cuoiky.R;

public class ThemSanPham extends AppCompatActivity {
    private EditText edtTenSanPham, edtGia, edtDaBan, edtConLai, edtMoTa;
    private Spinner spin_DanhMuc, spin_ThuongHieu;
    private ImageView imageView, img_Add, img_Edit, img_Back;
    private CardView btnChonAnh, btnLuu;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri imageUri;

    private ArrayAdapter<String> adapterThuongHieu;
    private ArrayAdapter<String> adapterDanhMuc;
    private final ArrayList<String> arrayListThuongHieu = new ArrayList<>();
    private final ArrayList<String> arrayListDanhMuc = new ArrayList<>();
    private String backgroundImageName = "null";
    private int indexThuongHieu = -1;
    private int indexDanhMuc = -1;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        SetID();
        LoadSpinnerData();
        Event();
    }

    private void LoadSpinnerData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DanhMuc");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String danhMuc = dataSnapshot.getValue(String.class);
                    arrayListDanhMuc.add(dataSnapshot.getKey());
                    adapterDanhMuc.add(danhMuc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef = database.getReference("ThuongHieu");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String thuongHieu = dataSnapshot.getValue(String.class);
                    arrayListThuongHieu.add(dataSnapshot.getKey());
                    adapterThuongHieu.add(thuongHieu);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ThemSanPham.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                }
                else {
                    selectImage();
                }
            }
        });

        spin_DanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexDanhMuc = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                indexDanhMuc = 0;
            }
        });

        spin_ThuongHieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexThuongHieu = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                indexThuongHieu = 0;
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HopLe())
                    LuuDuLieu();
            }
        });
    }

    private boolean HopLe() {
        String ten = edtTenSanPham.getText().toString().trim();
        String gia = edtGia.getText().toString().trim();
        String daBan = edtDaBan.getText().toString().trim();
        String conLai = edtConLai.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();
        if(ten.equals("") || gia.equals("") || daBan.equals("") || conLai.equals("") || moTa.equals("") || backgroundImageName.equals("null")){
            Toast.makeText(getApplicationContext(), "Điền vào chỗ trống hoặc chọn ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void LuuDuLieu() {
        int danhMuc = Integer.parseInt(arrayListDanhMuc.get(indexDanhMuc));
        int thuongHieu = Integer.parseInt(arrayListThuongHieu.get(indexThuongHieu));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("IdLib");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                IdLib idLib = snapshot.getValue(IdLib.class);

                //Thêm dữ liệu
                SanPham sanPham = new SanPham(idLib.getSanPham(), edtTenSanPham.getText().toString(),danhMuc, thuongHieu,
                        edtGia.getText().toString(), Integer.parseInt(edtDaBan.getText().toString()), Integer.parseInt(edtConLai.getText().toString()),
                        edtMoTa.getText().toString(), backgroundImageName);

                DatabaseReference myRef = database.getReference("SanPham/" + sanPham.getId());
                DatabaseReference myRef1 = database.getReference("IdLib/sanPham");

                myRef.setValue(sanPham, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {  //Tăng id
                        Toast.makeText(ThemSanPham.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                        CaiDatMacDinh();
                    }
                });
                uploadImage();
                myRef1.setValue(idLib.getSanPham()+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CaiDatMacDinh() {
        edtTenSanPham.setText("");
        edtMoTa.setText("");
        edtConLai.setText("");
        edtGia.setText("");
        edtDaBan.setText("");
        backgroundImageName = "null";
        imageView.setImageResource(R.drawable.user_icon);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                selectImage();
            }
            else {
                Toast.makeText(ThemSanPham.this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                imageUri = data.getData();
                if(imageUri != null){
                    imageView.setImageURI(imageUri);
                    backgroundImageName = getFileName(imageUri, getApplicationContext());
                }
            }
        }
    }


    private void uploadImage() {
       /* final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();*/

        final String randomKey = backgroundImageName;
        StorageReference riverRef = storageReference.child("Image/ " + randomKey);

        riverRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //pd.dismiss();
                        //Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss();
                        //Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        /*double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");*/
                    }
                });
    }

    private String getFileName (Uri uri, Context context){
        String res = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null );
            try {
                if(cursor != null && cursor.moveToFirst()){
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            }finally {
                cursor.close();
            }
            if (res == null){
                res = uri.getPath();
                int cutt = res.lastIndexOf('/');
                if (cutt != -1){
                    res = res.substring(cutt+1);
                }
            }
        }
        return res;
    }


    private void SetID() {
        edtTenSanPham = findViewById(R.id.edt_TenSanPham);
        spin_DanhMuc = findViewById(R.id.spin_DanhMucSanPham);
        spin_ThuongHieu = findViewById(R.id.spin_ThuongHieuSanPham);
        edtGia = findViewById(R.id.edt_GiaSanPham);
        edtDaBan = findViewById(R.id.edt_SoLuongSanPhamDaBan);
        edtConLai = findViewById(R.id.edt_SoLuongSanPhamConLai);
        edtMoTa = findViewById(R.id.edt_MoTaSanPham);
        btnChonAnh = findViewById(R.id.btn_ChonAnh);
        btnLuu = findViewById(R.id.btn_Luu);
        imageView = findViewById(R.id.img_HinhSanPham);
        img_Add = findViewById(R.id.img_Add);
        img_Edit = findViewById(R.id.img_Edit);
        img_Back = findViewById(R.id.img_Back);

        adapterDanhMuc = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapterThuongHieu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        spin_DanhMuc.setAdapter(adapterDanhMuc);
        spin_ThuongHieu.setAdapter(adapterThuongHieu);
    }

}