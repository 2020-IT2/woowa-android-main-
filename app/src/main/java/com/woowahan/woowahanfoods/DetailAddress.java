package com.woowahan.woowahanfoods;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.woowahan.woowahanfoods.Adapter.AddressAdapter;
import com.woowahan.woowahanfoods.Adapter.AddressData;
import com.woowahan.woowahanfoods.Dataframe.Juso;
import com.woowahan.woowahanfoods.Dataframe.SearchResultJson;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;

public class DetailAddress extends Fragment implements AddressAdapter.OnListItemSelectedInterface {

    private ImageView iv_search;
    private RecyclerView recyclerView;
    private ArrayList<Juso> arrayList;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;
    private LinearLayoutManager linearLayoutManager;
    private AddressAdapter addressAdapter;

    public EditText editText;

    //주소 검색
    final String key = "devU01TX0FVVEgyMDIwMTExMTE1MjY1NzExMDQwMjc=";
    int currentPage = 1;
    final int countPerPage = 15;
    final String resultType = "json";
    public String query;

    public static DetailAddress newInstance(String query){
        Bundle adr = new Bundle();
        adr.putString("query", query);
        DetailAddress detailAddress = new DetailAddress();
        detailAddress.setArguments(adr);
        return detailAddress;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_address, container, false);
        setHasOptionsMenu(true);

        editText = view.findViewById(R.id.et_search);


        Bundle bundle = getArguments();
        if(bundle!=null){
            this.query = bundle.getString("query");
        }
        editText.setText(query);

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionbar.setDisplayHomeAsUpEnabled(true);

        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        addressAdapter = new AddressAdapter(arrayList, this);
        recyclerView.setAdapter(addressAdapter);

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
                        search();
                    }
                }
            }

        });

        iv_search = view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("a", "editText.getText().toString()"+editText.getText().toString());
                query = editText.getText().toString();
                currentPage = 1;
                search();
            }
        });
        search();
        return view;
    }

    public void search(){
        RetrofitAdapter rAdapter = new RetrofitAdapter();
        RetrofitService service = rAdapter.getInstance("http://www.juso.go.kr/", getContext());
        final String query = this.query;
        Call<SearchResultJson> call = service.searchAddress(key, currentPage, countPerPage, query, resultType);

        call.enqueue(new retrofit2.Callback<SearchResultJson>() {
            @Override
            public void onResponse(Call<SearchResultJson> call, retrofit2.Response<SearchResultJson> response) {
                if (response.isSuccessful()) {
                    SearchResultJson result = response.body();
                    if(result.results.juso == null){
                        return ;
                    }
                    currentPage++;
                    arrayList.addAll(result.results.juso);
                    addressAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Search", "onResponse: Success but parsing fails " + response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResultJson> call, Throwable t) {
                Log.d("Search", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //select back button
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemSelected(View v, int position) {
        AddressAdapter.CustomViewHolder holder = (AddressAdapter.CustomViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        String jibun = holder.tv_dong.getText().toString();
        String road = holder.tv_road.getText().toString();
        String admCd= arrayList.get(position).admCd;
        String rnMgtSn= arrayList.get(position).rnMgtSn;
        String udrtYn= arrayList.get(position).udrtYn;
        int buldMnnm= arrayList.get(position).buldMnnm;
        int buldSlno = arrayList.get(position).buldSlno;
        ((MainActivity)getActivity()).replaceFragmentFull(AddressMap.newInstance(jibun, road, admCd, rnMgtSn, udrtYn, buldMnnm, buldSlno));
    }
}
