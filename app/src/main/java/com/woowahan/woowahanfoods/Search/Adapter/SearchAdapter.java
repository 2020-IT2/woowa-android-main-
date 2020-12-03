package com.woowahan.woowahanfoods.Search.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.woowahan.woowahanfoods.DataModel.Hashtag;
import com.woowahan.woowahanfoods.DataModel.Restaurant;
import com.woowahan.woowahanfoods.R;
import com.woowahan.woowahanfoods.Utils.TextUtils;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Hashtag> list;
    private List<Restaurant> list2;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;
    private TextView tvSearchText;

    public SearchAdapter(List<Hashtag> list, List<Restaurant> list2, Context context, TextView searchText){
        this.list = list;
        this.list2 = list2;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
        this.tvSearchText = searchText;
    }
    @Override
    public int getCount() {
        Log.d("searchAdapter", "getCount()");
        if (list.size() > 0)
            return list.size();
        else
            return list2.size();
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
            viewHolder.subLabel = (TextView) convertView.findViewById(R.id.subLabel);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Log.d("searchAdapter", "Here we go");
        if (list.size() > 0){
            Log.d("searchAdapter", "To list 1");
            viewHolder.label.setText(list.get(position).hashtag);
            viewHolder.subLabel.setText(list.get(position).sim);
            float sim = Float.parseFloat(list.get(position).sim);
            if(sim > 0.8){
                viewHolder.subLabel.setText("매우유사");
            } else if (sim > 0.6){
                viewHolder.subLabel.setText("조금유사");
            } else if (sim > 0.4){
                viewHolder.subLabel.setText("덜유사");
            } else if (sim > 0.2){
                viewHolder.subLabel.setText("소소");
            } else {
                viewHolder.subLabel.setText("안유사");
            }
        } else {
            Log.d("searchAdapter", "To list 2");
            viewHolder.label.setText(list2.get(position).restaurantName);
            if(list2.get(position).feedNum > 5000){
                viewHolder.subLabel.setText("매우유명");
            } else if (list2.get(position).feedNum > 2500){
                viewHolder.subLabel.setText("조금유명");
            } else if (list2.get(position).feedNum > 1000){
                viewHolder.subLabel.setText("덜유명");
            } else if (list2.get(position).feedNum > 100){
                viewHolder.subLabel.setText("소소");
            } else {
                viewHolder.subLabel.setText("안유명");
            }

            // 검색한 쿼리와 동일한 부분을 강조한다.
            String query = tvSearchText.getText().toString();
            viewHolder.label.setText("");
            int start = list2.get(position).restaurantName.indexOf(query);
            if (start != -1){
                TextUtils.setColorInPartitial(list2.get(position).restaurantName, start, start+query.length(), "#03DAC5", viewHolder.label);
            } else {
                viewHolder.label.setText(list2.get(position).restaurantName);
            }
        }




        return convertView;
    }

    class ViewHolder{
        public TextView label;
        public TextView subLabel;
    }

}
