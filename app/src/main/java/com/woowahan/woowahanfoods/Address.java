package com.woowahan .woowahanfoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Address extends Fragment {
    private Button btn_curlocation;
    private ImageView iv_cancel;
    private ImageView iv_search;
    private EditText edt_adr;

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
                getActivity().getFragmentManager().popBackStack();
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

        return view;
    }
}