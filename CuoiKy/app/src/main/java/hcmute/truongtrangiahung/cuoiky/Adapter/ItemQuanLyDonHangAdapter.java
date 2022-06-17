package hcmute.truongtrangiahung.cuoiky.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import hcmute.truongtrangiahung.cuoiky.Model.ItemQuanLyDonHang;
import hcmute.truongtrangiahung.cuoiky.R;

public class ItemQuanLyDonHangAdapter extends BaseAdapter {
    private Context context; //lấy context của activity sử dụng adapter
    private ArrayList<ItemQuanLyDonHang> arrayList; //lưu dữ liệu để hiên thị
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

    public ItemQuanLyDonHangAdapter(Context context, ArrayList<ItemQuanLyDonHang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    // Lấy dữ liệu và hiển thị view ra màn hình
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_item_quan_ly_don_hang, null);

        TextView maDonHang = view.findViewById(R.id.txt_MaDonHang);
        TextView email = view.findViewById(R.id.txt_Email);
        TextView ngayDatHang = view.findViewById(R.id.txt_NgayDatHang);

        maDonHang.setText(String.valueOf(arrayList.get(i).getMaDonHang()));
        email.setText(arrayList.get(i).getEmail());
        ngayDatHang.setText(arrayList.get(i).getNgayDatHang());

        return view;
    }
}
