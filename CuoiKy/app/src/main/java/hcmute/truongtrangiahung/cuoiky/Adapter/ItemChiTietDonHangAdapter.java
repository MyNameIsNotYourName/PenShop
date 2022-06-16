package hcmute.truongtrangiahung.cuoiky.Adapter;

import android.content.Context;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import hcmute.truongtrangiahung.cuoiky.Model.ItemChiTietDonHang;
import hcmute.truongtrangiahung.cuoiky.R;

public class ItemChiTietDonHangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemChiTietDonHang> arrayList;

    public ItemChiTietDonHangAdapter(Context context, ArrayList<ItemChiTietDonHang> arrayList) {
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
        view = inflater.inflate(R.layout.layout_item_chi_tiet_don_hang, null);

        ImageView hinhSanPham = view.findViewById(R.id.img_HinhSanPham);
        TextView tenSanPham = view.findViewById(R.id.txt_TenSanPham);
        TextView soLuongSanPham = view.findViewById(R.id.txt_SoLuongSanPham);
        TextView tongGiaSanPham = view.findViewById(R.id.txt_GiaSanPham);

        RetrieveImage(view, hinhSanPham, i);
        tenSanPham.setText(arrayList.get(i).getTenSanPham());

        String sSoLuong= "Số lượng: " + arrayList.get(i).getSoLuongSanPham();
        soLuongSanPham.setText(sSoLuong);

        String sTongGiaSanPham = "Tổng giá: " + arrayList.get(i).getTongGiaSanPham() + " VND";
        tongGiaSanPham.setText(sTongGiaSanPham);

        return view;
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
