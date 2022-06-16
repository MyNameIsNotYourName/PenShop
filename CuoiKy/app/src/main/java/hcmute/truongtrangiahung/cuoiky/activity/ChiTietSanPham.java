package hcmute.truongtrangiahung.cuoiky.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import hcmute.truongtrangiahung.cuoiky.Model.SanPham;
import hcmute.truongtrangiahung.cuoiky.R;

public class ChiTietSanPham extends AppCompatActivity {
    private ImageView img_Add, img_Back, img_Edit, img_HinhSanPham;
    private EditText edt_TenSanPham, edt_GiaSanPham, edt_DaBan, edt_ConLai, edt_MoTa;
    private TextView txt_MaSanPham, txt_HuyThayDoi, txt_LuuThayDoi;
    private Spinner spin_DanhMuc, spin_ThuongHieu;
    private TableLayout table_LuuThayDoi;

    private ArrayAdapter<String> adapterThuongHieu;
    private ArrayAdapter<String> adapterDanhMuc;
    private ArrayList<String> arrayListThuongHieu = new ArrayList<>();
    private ArrayList<String> arrayListDanhMuc = new ArrayList<>();
    private SanPham sanPham = new SanPham();
    private int indexThuongHieu = -1;
    private int indexDanhMuc = -1;
    private int tempSpinDanhMucSize = 0;
    private int tempSpinThuongHieuSize = 0;
    private int flagImage = 0; // Kiểm tra xem có thay đổi hình ảnh không.
    private String backgroundImageName = "null";
    private Uri imageUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        SetID();
        GetIntent();
        TaiDuLieu();
        LoadSpinnerData();
        Event();
    }

    private void TaiDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SanPham");

        String id = String.valueOf(sanPham.getId());

        Query query = myRef.orderByChild(id);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String tempID = snapshot.getKey();
                if(tempID.equals(id))
                {
                    sanPham = snapshot.getValue(SanPham.class);

                    txt_MaSanPham.setText(String.valueOf(sanPham.getId()));
                    edt_TenSanPham.setText(sanPham.getTenSP());
                    edt_GiaSanPham.setText(sanPham.getGia());
                    edt_DaBan.setText(String.valueOf(sanPham.getDaBan()));
                    edt_ConLai.setText(String.valueOf(sanPham.getConLai()));
                    edt_MoTa.setText(sanPham.getMoTa());

                    System.out.println("SPDM: " + sanPham.getDanhMuc());
                    System.out.println("SPTH: " + sanPham.getThuongHieu());

                    backgroundImageName = sanPham.getHinhAnh();
                    RetrieveImage();
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

    private int TimViTri(String type, int id) {
        if(type.equals("danhMuc"))
        {
            for(int i = 0; i < arrayListDanhMuc.size(); i++)
            {
                int arrayID = Integer.parseInt(arrayListDanhMuc.get(i));
                if(id == arrayID)
                    return i;
            }
        }
        else
        {
            for(int i = 0; i < arrayListThuongHieu.size(); i++)
            {
                int arrayID = Integer.parseInt(arrayListThuongHieu.get(i));
                if(id == arrayID)
                    return i;
            }
        }
        return -1;
    }

    private void LoadSpinnerData() {
        //arrayListDanhMuc.clear();
        adapterDanhMuc.clear();
        //arrayListThuongHieu.clear();
        adapterThuongHieu.clear();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DanhMuc");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String danhMuc = snapshot.getValue(String.class);
                arrayListDanhMuc.add(snapshot.getKey());
                adapterDanhMuc.add(danhMuc);
                tempSpinDanhMucSize += 1;

                int viTriDanhMuc = TimViTri("danhMuc", sanPham.getDanhMuc());
                spin_DanhMuc.setSelection(viTriDanhMuc);
                //System.out.println("viTriDanhMuc: " + viTriDanhMuc);
                System.out.println("IdDanhMuc: " + sanPham.getDanhMuc());
                System.out.println("tempDanhMuc: " + tempSpinDanhMucSize);
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


        myRef = database.getReference("ThuongHieu");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    String thuongHieu = snapshot.getValue(String.class);
                    arrayListThuongHieu.add(snapshot.getKey());
                    adapterThuongHieu.add(thuongHieu);
                    tempSpinThuongHieuSize += 1;
                    
                    int viTriThuongHieu = TimViTri("thuongHieu", sanPham.getThuongHieu());
                    spin_ThuongHieu.setSelection(viTriThuongHieu);
                    System.out.println("tempThuongHieu: " + tempSpinThuongHieuSize);
                    adapterThuongHieu.notifyDataSetChanged();
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

        adapterDanhMuc.notifyDataSetChanged();
        adapterThuongHieu.notifyDataSetChanged();
    }

    private void GetIntent() {
        Intent intent = getIntent();
        sanPham.setId( intent.getIntExtra("sanPham", -1));
    }

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
                TuyChinhClick(true);
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

        img_HinhSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ChiTietSanPham.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                }
                else {
                    selectImage();
                }
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    private void CaiDatMacDinh() {
       TuyChinhClick(false);

        table_LuuThayDoi.setVisibility(View.INVISIBLE);

        txt_MaSanPham.setText(String.valueOf(sanPham.getId()));
        edt_TenSanPham.setText(sanPham.getTenSP());
        edt_GiaSanPham.setText(sanPham.getGia());
        edt_DaBan.setText(String.valueOf(sanPham.getDaBan()));
        edt_ConLai.setText(String.valueOf(sanPham.getConLai()));
        edt_MoTa.setText(sanPham.getMoTa());
        int viTriDanhMuc = TimViTri("danhMuc", sanPham.getDanhMuc());
        spin_DanhMuc.setSelection(viTriDanhMuc);
        int viTriThuongHieu = TimViTri("thuongHieu", sanPham.getThuongHieu());
        spin_ThuongHieu.setSelection(viTriThuongHieu);
    }

    private void LuuThayDoi() {
        //Lấy id danh mục
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SanPham/"+sanPham.getId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Thêm dữ liệu

                System.out.println("DanhMuc: " + arrayListDanhMuc.size());
                System.out.println("IndexDM: " + indexDanhMuc);
                int danhMuc = Integer.parseInt(arrayListDanhMuc.get(indexDanhMuc));
                int thuongHieu = Integer.parseInt(arrayListThuongHieu.get(indexThuongHieu));

                SanPham temp = new SanPham(sanPham.getId(), edt_TenSanPham.getText().toString(),danhMuc, thuongHieu,
                        edt_GiaSanPham.getText().toString(), Integer.parseInt(edt_DaBan.getText().toString()), Integer.parseInt(edt_ConLai.getText().toString()),
                        edt_MoTa.getText().toString(), backgroundImageName);

                myRef.setValue(temp, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ChiTietSanPham.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                        TuyChinhClick(false);
                        table_LuuThayDoi.setVisibility(View.INVISIBLE);
                        TaiDuLieu();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(flagImage == 1)
        {
            uploadImage();
        }
    }

    private void SetID() {
        img_Add = findViewById(R.id.img_Add);
        img_Back = findViewById(R.id.img_Back);
        img_Edit = findViewById(R.id.img_Edit);
        img_HinhSanPham = findViewById(R.id.img_HinhSanPham);
        table_LuuThayDoi = findViewById(R.id.table_LuuThayDoi);
        edt_TenSanPham = findViewById(R.id.edt_TenSanPham);
        txt_MaSanPham = findViewById(R.id.txt_MaSanPham);
        edt_GiaSanPham = findViewById(R.id.edt_GiaSanPham);
        edt_ConLai = findViewById(R.id.edt_SoLuongSanPhamConLai);
        edt_DaBan = findViewById(R.id.edt_SoLuongSanPhamDaBan);
        edt_MoTa = findViewById(R.id.edt_MoTaSanPham);
        txt_HuyThayDoi = findViewById(R.id.txt_HuyThayDoi);
        txt_LuuThayDoi = findViewById(R.id.txt_LuuThayDoi);
        spin_DanhMuc = findViewById(R.id.spin_DanhMucSanPham);
        spin_ThuongHieu = findViewById(R.id.spin_ThuongHieuSanPham);

        TuyChinhClick(false);

        adapterDanhMuc = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapterThuongHieu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        spin_DanhMuc.setAdapter(adapterDanhMuc);
        spin_ThuongHieu.setAdapter(adapterThuongHieu);
    }

    private void TuyChinhClick(boolean b) {
        edt_TenSanPham.setFocusable(b);
        edt_GiaSanPham.setFocusable(b);
        edt_MoTa.setFocusable(b);
        edt_DaBan.setFocusable(b);
        edt_ConLai.setFocusable(b);
        img_HinhSanPham.setClickable(b);
        spin_ThuongHieu.setEnabled(b);
        spin_DanhMuc.setEnabled(b);
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
                Toast.makeText(ChiTietSanPham.this, "Permission denied!", Toast.LENGTH_SHORT).show();
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
                    /*try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                    }
                    catch (IOException e){
                        Toast.makeText(ThemSanPham.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }*/
                    img_HinhSanPham.setImageURI(imageUri);
                    backgroundImageName = getFileName(imageUri, getApplicationContext());
                    flagImage = 1;
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

    public void RetrieveImage(){
        String name  = backgroundImageName;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Image/ " + name);
        try {
            File localFile = File.createTempFile("tempfile", ".jpg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            img_HinhSanPham.setImageBitmap(bitmap);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}