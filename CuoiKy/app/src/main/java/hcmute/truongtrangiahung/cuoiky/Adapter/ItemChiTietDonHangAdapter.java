package hcmute.truongtrangiahung.cuoiky.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        hinhSanPham.setImageResource(arrayList.get(i).getHinh());
        tenSanPham.setText(arrayList.get(i).getTenSanPham());

        String sSoLuong= "Số lượng: " + arrayList.get(i).getSoLuongSanPham();
        soLuongSanPham.setText(sSoLuong);

        String sTongGiaSanPham = "Tổng giá: " + arrayList.get(i).getTongGiaSanPham() + " VND";
        tongGiaSanPham.setText(sTongGiaSanPham);

        return view;
    }
}
