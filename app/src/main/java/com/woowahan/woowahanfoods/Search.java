package com.woowahan.woowahanfoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.woowahan.woowahanfoods.Adapter.SearchAdapter;
import com.woowahan.woowahanfoods.Dataframe.Restaurant;
import com.woowahan.woowahanfoods.Dataframe.RestaurantSearchResult;
import com.woowahan.woowahanfoods.Dataframe.SearchResultJson;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Search extends Fragment {
    public String TAG = "Search Fragment Debug";
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;

    private List<Restaurant> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터

    public Restaurant restaurant;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionbar.setDisplayHomeAsUpEnabled(true);

        editSearch = view.findViewById(R.id.edit_search);
        listView = view.findViewById(R.id.listView);
        list = new ArrayList<Restaurant>();
        adapter = new SearchAdapter(list, getContext());
        listView.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(listView,true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                restaurant = list.get(i);
                editSearch.setText("");
                list.clear();
                adapter.notifyDataSetChanged();
                ((MainActivity)getActivity()).replaceFragmentFull(FeedViewer.newInstance(restaurant));
            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        return view;
    }

    private void search(String query) {

        list.clear();
        if (query.equals("")) {
            adapter.notifyDataSetChanged();
            return;
        }

        RetrofitAdapter rAdapter = new RetrofitAdapter();
        RetrofitService service = rAdapter.getInstance("http://49.50.164.11:5000", getActivity());
//        Call<RestaurantSearchResult> call = service.searchRestaurant(query, "관악구");
        Call<RestaurantSearchResult> call = service.searchSchoolList(query);
        call.enqueue(new retrofit2.Callback<RestaurantSearchResult>() {
            @Override
            public void onResponse(Call<RestaurantSearchResult> call, retrofit2.Response<RestaurantSearchResult> response) {
                if (response.isSuccessful()) {
                    if(response.body().checkError(getContext()) != 0) {
                        return;
                    }
                    RestaurantSearchResult result = response.body();
                    list.clear();
                    list.addAll(result.body);
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onResponse: Success " + response.body());
                } else {
                    Log.d(TAG, "onResponse: Fail " + response.body());
                }
            }

            @Override
            public void onFailure(Call<RestaurantSearchResult> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
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

}