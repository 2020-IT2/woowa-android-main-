package com.woowahan .woowahanfoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import com.woowahan.woowahanfoods.Adapter.MyAddressAdapter;
import com.woowahan.woowahanfoods.Dataframe.MyAddress;

import java.util.ArrayList;


public class Address extends Fragment implements MyAddressAdapter.OnListItemSelectedInterface{
    private Button btn_curlocation;
    private ImageView iv_cancel;
    private ImageView iv_search;
    private EditText edt_adr;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyAddressAdapter myaddressAdapter;
    private ArrayList<MyAddress> userData;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        btn_curlocation = view.findViewById(R.id.btn_curlocation);
        btn_curlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(new CurAddress());
            }
        });

        iv_cancel = view.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        edt_adr = view.findViewById(R.id.edt_adr);
        edt_adr.getText();

        iv_search = view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(DetailAddress.newInstance(edt_adr.getText().toString()));
            }
        });

        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        userData = ((MainActivity)getActivity()).user.myAddresses;
        if(userData.size() > 0){
            if(userData.get(userData.size()-1).dongAddress.equals("위치를 입력해주세요.")){
                userData.remove(userData.size()-1);
            }
        }

        myaddressAdapter = new MyAddressAdapter(getContext(), userData, this);
        recyclerView.setAdapter(myaddressAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return;
                }

                getNewPosition(recyclerView);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            private void getNewPosition(@NonNull final RecyclerView recyclerView) {
                final LinearLayoutManager layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());
                if (layoutManager != null) {
                    int curPosition = recyclerView.getAdapter().getItemCount() - 1;
                    int recyclerVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                    if((recyclerVisiblePosition >= 10) && (curPosition>=recyclerVisiblePosition-3)){
                        Log.d("d", "error");
                    }
                }
            }

        });

        return view;
    }

    @Override
    public void onItemSelected(View v, int position) {

    }
}