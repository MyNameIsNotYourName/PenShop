package hcmute.truongtrangiahung.cuoiky.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import hcmute.truongtrangiahung.cuoiky.Model.DanhMuc;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietDonHang;
import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietQuanLy;
import hcmute.truongtrangiahung.cuoiky.R;
import hcmute.truongtrangiahung.cuoiky.activity.ThemSanPham;

public class ItemChiTietQuanLyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemChiTietQuanLy> arrayList;
    private int flag = 1;


    public ItemChiTietQuanLyAdapter(Context context, ArrayList<ItemChiTietQuanLy> arrayList, int flag) {
        this.context = context;
        this.arrayList = arrayList;
        this.flag = flag;
    }

    public ItemChiTietQuanLyAdapter(Context context, ArrayList<ItemChiTietQuanLy> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_item_chi_tiet_quan_ly, null);

        ImageView hinhSanPham = view.findViewById(R.id.img_HinhSanPham);
        ImageView img_Xoa = view.findViewById(R.id.img_NutXoa);
        TextView tenSanPham = view.findViewById(R.id.txt_TenSanPham);

        int typeFlag = arrayList.get(i).getFlag();
        int index = i;

        if(flag == 1)
        {
            RetrieveImage(view, hinhSanPham, i);
        }
        else {
            hinhSanPham.setVisibility(View.GONE);
        }

        if(typeFlag == 1){// SanPham
            tenSanPham.setText(arrayList.get(i).getSanPham().getTenSP());
        }

        if(typeFlag == 2){// DanhMuc
            tenSanPham.setText(arrayList.get(i).getDanhMuc().getTenDanhMuc());
        }

        if(typeFlag == 3){// ThuongHieu
            tenSanPham.setText(arrayList.get(i).getThuongHieu().getTenThuongHieu());
        }

        if(typeFlag == 4){// TaiKhoan
            tenSanPham.setText(arrayList.get(i).getTaiKhoan().getTenTaiKhoan());
        }

        img_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Xác nhận xóa");
                alertDialog.setMessage("Bạn có muốn xóa?");

                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        XoaDuLieu(view, typeFlag, index);
                    }
                });

                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialog.show();

            }
        });

        return view;
    }

    private void XoaDuLieu(View view, int typeFlag, int i) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // flag = 2 => DanhMuc; flag = 3 => ThuongHieu
        if(typeFlag == 1)
        {
            DatabaseReference myRef = database.getReference("SanPham/"+arrayList.get(i).getSanPham().getId());
            myRef.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(view.getContext(), "Đã Xóa", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else if(typeFlag == 2)
        {
            DatabaseReference myRef = database.getReference("DanhMuc/"+arrayList.get(i).getDanhMuc().getId());
            myRef.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(view.getContext(), "Đã Xóa", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            DatabaseReference myRef = database.getReference("ThuongHieu/"+arrayList.get(i).getThuongHieu().getId());
            myRef.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(view.getContext(), "Đã Xóa", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public void RetrieveImage(View view, ImageView imageView, int i){
        String name  = arrayList.get(i).getHinh();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Image/ " + name);
        try {
            File localFile = File.createTempFile("tempfile", ".jpg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
