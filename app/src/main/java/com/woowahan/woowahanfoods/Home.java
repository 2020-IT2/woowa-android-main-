package com.woowahan.woowahanfoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Home extends Fragment  {
    public ArrayList<ImageButton> img_btns = new ArrayList<ImageButton>();
    public int [] icon_nams = new int[]{
            R.id.ib_icon1, R.id.ib_icon2, R.id.ib_icon3, R.id.ib_icon4,
            R.id.ib_icon5, R.id.ib_icon6, R.id.ib_icon7, R.id.ib_icon8,
            R.id.ib_icon9, R.id.ib_icon10, R.id.ib_icon11, R.id.ib_icon12
    };

    private LinearLayout search, address;
    private TextView tv_homeaddress;
    private String homeaddress;

    private String road;

    public static Home newInstance(String road) {
        Bundle adr = new Bundle();
        adr.putString("road", road);
        Home home = new Home();
        home.setArguments(adr);
        return home;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        search = view.findViewById(R.id.search);
        address = view.findViewById(R.id.address);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(new Search());
            }
        });
        address.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(new Address());
            }
        });

        tv_homeaddress = view.findViewById(R.id.tv_homeaddress);
        Bundle bundle = getArguments();
        if(bundle!=null){
            this.homeaddress = bundle.getString("road");
            tv_homeaddress.setText(homeaddress);
        }


        for(int id : icon_nams){
            ImageButton btn = (ImageButton)view.findViewById(id);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).replaceFragmentFull(new RestaurantList());
                }
            });
            img_btns.add(btn);

        }




        return view;
    }

}
