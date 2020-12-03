package com.woowahan.woowahanfoods.Home.Fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.woowahan.woowahanfoods.Address.Fragment.Address;
import com.woowahan.woowahanfoods.DataModel.Feed;
import com.woowahan.woowahanfoods.Home.Adapter.viewPageAdapter;
import com.woowahan.woowahanfoods.Dataframe.FeedResult;
import com.woowahan.woowahanfoods.DataModel.MyAddress;
import com.woowahan.woowahanfoods.DataModel.Restaurant;
import com.woowahan.woowahanfoods.Home.Dataframe.RandomRecommendResponse;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;
import com.woowahan.woowahanfoods.RestaurantList.Fragment.RestaurantList;
import com.woowahan.woowahanfoods.Search.Fragment.Search;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import retrofit2.Call;

public class Home extends Fragment {
    private static final int DP = 24;
    public String fields = "media_url";
    public String access_token = "IGQVJYVFRlT1VRMDg3QWRQQ3ZAOTGNlbGQ2VGE3YWVvYUNaLWF0MWJaZA1VvQjlwX3lPZAEF3cHVvZAXEzWlhzOHMwSG10SXpmX1lPYXlldGpYZAFhCZAS1aSVR6WTN1ci1oU2FyV2V6OXl3";
    public String sampleFeedID = "17922299383454246";
    public viewPageAdapter adapter;
    public viewPageAdapter adapter2;
    public ArrayList<Feed> randomRecommendList;

    public ArrayList<ImageButton> img_btns = new ArrayList<ImageButton>();
    public int[] icon_nams = new int[]{
            R.id.ib_icon1, R.id.ib_icon2, R.id.ib_icon3, R.id.ib_icon4,
            R.id.ib_icon5, R.id.ib_icon6, R.id.ib_icon7, R.id.ib_icon8,
            R.id.ib_icon9, R.id.ib_icon10, R.id.ib_icon11, R.id.ib_icon12
    };

    private LinearLayout search, address;
    private TextView tv_homeaddress;

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
                ((MainActivity) getActivity()).replaceFragmentFull(new Search());
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragmentFull(new Address());
            }
        });

        if (((MainActivity) getActivity()).user.myAddresses.size() == 0) {
            MyAddress myAddress = new MyAddress("위치를 입력해주세요.", "", 0, 0);
            ((MainActivity) getActivity()).user.myAddresses.add(0, myAddress);
            ((MainActivity) getActivity()).user.curAddress = myAddress;
        }
        tv_homeaddress = view.findViewById(R.id.tv_homeaddress);
        tv_homeaddress.setText(((MainActivity) getActivity()).user.curAddress.dongAddress);

        for (int id = 0; id < icon_nams.length; id++) {
            ImageButton btn = (ImageButton) view.findViewById(icon_nams[id]);
            final int idx = id;

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).replaceFragmentFull(RestaurantList.newInstance(idx));
                }
            });
            img_btns.add(btn);

        }

        randomRecommendList = new ArrayList<>();


        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);
        ViewPager viewPager2 = view.findViewById(R.id.viewPager2);
        viewPager2.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);
        adapter = new viewPageAdapter(getContext(), randomRecommendList);
        viewPager.setAdapter(adapter);

        viewPager2.setPadding(margin, 0, margin, 0);
        viewPager2.setPageMargin(margin/2);
        adapter2 = new viewPageAdapter(getContext(), randomRecommendList);
        viewPager2.setAdapter(adapter2);

        getPhotos();
        getHashKey();

        return view;
    }

    public void getPhotos() {
        // Retrofit 삽질 4시간 경험 한 후기
        // 1. Retrofit 의 Service는 BaseURL과는 연관이 없다.
        // 2. Retrofit은 여러개를 만들어서 사용해도 상관 없다.
        // 3. Retrofit의 Parameter는 반드시 URL encoded 되지 않은것으로 사용해야한다.
        // 4. Retrofit의 Parameter는 절대 getString(R.string.name) 으로 가지고 온것을 사용하면 안된다.
        RetrofitService service = RetrofitAdapter.getInstance(getActivity());
        Call<RandomRecommendResponse> call = service.recommendRandom();

        call.enqueue(new retrofit2.Callback<RandomRecommendResponse>() {
            @Override
            public void onResponse(Call<RandomRecommendResponse> call, retrofit2.Response<RandomRecommendResponse> response) {
                if (response.isSuccessful()) {
                    response.body().checkError(getContext());
                    randomRecommendList.clear();
                    randomRecommendList.addAll(response.body().body.feeds);
                    adapter.refresh(randomRecommendList); //pass update list
                    adapter2.refresh(randomRecommendList); //pass update list
                } else {
                    Log.d("GridViewAdapter", "onResponse: Fail " + response.body());
                }
            }

            @Override
            public void onFailure(Call<RandomRecommendResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Please reloading", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }


        }
    }
}