package com.woowahan .woowahanfoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.woowahan.woowahanfoods.Dataframe.SearchResultJson;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import retrofit2.Call;

public class Address extends Fragment {
    private Button btn_curlocation;
    private ImageView iv_cancel;
    private ImageView iv_search;
    private RecyclerView recyclerView;
    private EditText edt_adr;
    public String query;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        btn_curlocation = (Button)view.findViewById(R.id.btn_curlocation);
        btn_curlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(new CurAddress());
            }
        });

        iv_cancel = (ImageView)view.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });

        edt_adr = view.findViewById(R.id.edt_adr);
        edt_adr.getText();

        iv_search = (ImageView)view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(DetailAddress.newInstance(edt_adr.getText().toString()));
            }
        });

        //리사이클러뷰
        recyclerView = (RecyclerView)view.findViewById(R.id.rv);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    public static Address newInstance(String str_adr){
        Bundle adr = new Bundle();
        adr.putString("str_adr", str_adr);
        Address address = new Address();
        address.setArguments(adr);
        return address;
    }
}