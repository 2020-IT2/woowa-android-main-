package com.woowahan.woowahanfoods.Search.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.woowahan.woowahanfoods.DataModel.Restaurant;
import com.woowahan.woowahanfoods.R;
import com.woowahan.woowahanfoods.Utils.TextUtils;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Restaurant> list;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;
    private TextView tvSearchText;

    public SearchAdapter(List<Restaurant> list, Context context, TextView searchText){
        this.list = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
        this.tvSearchText = searchText;
    }
    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.search_list,null);

            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.label);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 검색한 쿼리와 동일한 부분을 강조한다.
        String query = tvSearchText.getText().toString();
        viewHolder.label.setText("");
        int start = list.get(position).schoolName.indexOf(query);
        if (start != -1){
            TextUtils.setColorInPartitial(list.get(position).schoolName, start, start+query.length(), "#03DAC5", viewHolder.label);
        } else {
            viewHolder.label.setText(list.get(position).schoolName);
        }

        return convertView;
    }

    class ViewHolder{
        public TextView label;
    }

}
