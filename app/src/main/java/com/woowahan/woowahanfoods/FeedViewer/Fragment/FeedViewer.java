package com.woowahan.woowahanfoods.FeedViewer.Fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.woowahan.woowahanfoods.Address.Adapter.MyAddressAdapter;
import com.woowahan.woowahanfoods.FeedViewer.Adapter.FeedAapter;
import com.woowahan.woowahanfoods.FeedViewer.Adapter.GridViewAdapter;
import com.woowahan.woowahanfoods.Dataframe.FeedResult;
import com.woowahan.woowahanfoods.DataModel.Restaurant;
import com.woowahan.woowahanfoods.Home.Adapter.viewPageAdapter;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;

public class FeedViewer extends Fragment implements GridViewAdapter.OnListItemSelectedInterface {
    // gridView
    public ArrayList<Restaurant> imgList;
    public GridViewAdapter adapter;
    public ProgressBar progressBar;
    private ActionBar actionbar;
    public String sampleFeedID = "17922299383454246";
    public String fields = "id,media_type,caption,media_url";
    public String access_token = "IGQVJYVFRlT1VRMDg3QWRQQ3ZAOTGNlbGQ2VGE3YWVvYUNaLWF0MWJaZA1VvQjlwX3lPZAEF3cHVvZAXEzWlhzOHMwSG10SXpmX1lPYXlldGpYZAFhCZAS1aSVR6WTN1ci1oU2FyV2V6OXl3";

    // FeedView
    public FeedAapter feedAapter;
    public Restaurant imageData;
    public ArrayList<Restaurant> imageDataList;

    // animation
    public LinearLayout linear_view;
    public boolean togle = false;
    public TextView title;
    public LinearLayout hoverLayout;
    public ImageView like;
    public ImageView comment;
    public TextView hashTag;
    public TextView caption;
    public TextView numComment;
    public TextView numLike;
    public CardView cvFeed;
    public LinearLayout infoLayout;
    public boolean hoverMode;
    float downX;
    float upX;


    public static FeedViewer newInstance(Restaurant restaurant) {
        FeedViewer fragment = new FeedViewer();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        args.putString("restaurant", gson.toJson(restaurant));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_viewer, container, false);
        view.setClickable(true);

        // 호출
        Bundle bundle = getArguments();
        if(bundle!=null){
            Gson gson = new Gson();
            this.imageData = gson.fromJson(bundle.getString("restaurant"), Restaurant.class);
        }

        // grid view for recommendation
        progressBar = view.findViewById(R.id.profileProgressBar);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext().getApplicationContext(), 2);
        imgList = new ArrayList<>();
        adapter = new GridViewAdapter(getContext(), imgList, this);
        RecyclerView gv = view.findViewById(R.id.rv);
        gv.setLayoutManager(layoutManager);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용

        // viewPager
        imageDataList = new ArrayList<>();
        final ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);

        feedAapter = new FeedAapter(getContext(), imageDataList);
        viewPager.setAdapter(feedAapter);

        // 애니메이션
        final LinearLayout linearLayout = view.findViewById(R.id.lin_visible);
        final TextView textView = view.findViewById(R.id.location);

        hoverMode = false;
        infoLayout = view.findViewById(R.id.ll_item);
        cvFeed = view.findViewById(R.id.cv_feed);
        title = view.findViewById(R.id.feed_title);
        comment = view.findViewById(R.id.iv_comment);
        hashTag = view.findViewById(R.id.tv_hashtag);
        caption = view.findViewById(R.id.tv_caption);
        numLike = view.findViewById(R.id.tv_num_like);
        numComment = view.findViewById(R.id.tv_num_comment);
        like = view.findViewById(R.id.iv_like);
        infoLayout.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        hoverLayout = ((LinearLayout)view.findViewById(R.id.ll_hover));
        hoverLayout.setClickable(true);
        hoverLayout.setBackgroundColor(Color.TRANSPARENT);
        hoverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        hoverLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //ACTION_DOWN is constant that means "A person's hand is put on touch panel".
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = motionEvent.getX();
                }
                //ACTION_UP is constant that means "A person's hand is put off on touch panel".
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    upX = motionEvent.getX();
                    // 그냥 터치로 인식
                    if (Math.abs(downX-upX)<5){
                        if(hoverMode){
                            // card view
                            cvFeed.setRadius(0);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                cvFeed.setElevation(1);
                            }
                            //visibility gone
                            hoverLayout.setBackgroundColor(Color.TRANSPARENT);
                            title.setVisibility(View.GONE);
                            infoLayout.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.GONE);
                            // animation
                            hoverLayout.animate().alpha(0.0f);
                            title.animate().alpha(0.0f);
                            infoLayout.animate().alpha(0.0f);
                            linearLayout.animate().alpha(0.0f);
//                    comment.animate().alpha(0.0f);
//                    like.animate().alpha(0.0f);
//                    numComment.animate().alpha(0.0f);
//                    numLike.animate().alpha(0.0f);
                            hoverMode = false;
                        } else {
                            // card view
                            cvFeed.setRadius(50);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                cvFeed.setElevation(10);
                            }
                            Paint paint = new Paint();
                            paint.setColor(Color.BLACK);
                            paint.setAlpha(65);
                            hoverLayout.setBackgroundColor(paint.getColor());
                            title.setVisibility(View.VISIBLE);
                            infoLayout.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.VISIBLE);
                            // animation
                            title.animate().alpha(1.0f).setDuration(999);
                            linearLayout.animate().alpha(1.0f).setDuration(999);
                            hoverLayout.animate().alpha(1.0f).setDuration(999);
                            infoLayout.animate().alpha(1.0f).setDuration(999);
                            hoverMode = true;
                        }
                    }
                    // 오른쪽에서 왼쪽으로 스크롤
                    if (upX < downX) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                    }
                    // 왼쪽에서 오른쪽으로 스크롤
                    else if (upX > downX) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
                    }
                    updateText(viewPager.getCurrentItem());
                    return true;
                }
                return true;
            }
        });

        title.setClickable(true);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(RestaurantMap.newInstance(
                        imageDataList.get(0).restaurantName, imageDataList.get(0).address, (float)imageDataList.get(0).lat,
                        (float)imageDataList.get(0).lon));
            }
        });

        getFeed();
        getPhotos();

        return view;
    }

    public void getFeed(){
        // Retrofit 삽질 4시간 경험 한 후기
        // 1. Retrofit 의 Service는 BaseURL과는 연관이 없다.
        // 2. Retrofit은 여러개를 만들어서 사용해도 상관 없다.
        // 3. Retrofit의 Parameter는 반드시 URL encoded 되지 않은것으로 사용해야한다.
        // 4. Retrofit의 Parameter는 절대 getString(R.string.name) 으로 가지고 온것을 사용하면 안된다.
        RetrofitService service = RetrofitAdapter.getInstance("https://graph.instagram.com/", getContext());
        Call<FeedResult> call = service.getFeeds(fields, access_token);

        call.enqueue(new retrofit2.Callback<FeedResult>() {
            @Override
            public void onResponse(Call<FeedResult> call, retrofit2.Response<FeedResult> response) {
                if (response.isSuccessful()) {
                    Log.d("GridViewAdapter", "onResponse: Success " + response.body().data.size());
                    imgList.clear();
                    imgList.addAll(response.body().data);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.d("GridViewAdapter", "onResponse: Fail " + response.body());
                }
            }

            @Override
            public void onFailure(Call<FeedResult> call, Throwable t) {
                Toast.makeText(getContext(), "Please reloading", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPhotos(){
        // Retrofit 삽질 4시간 경험 한 후기
        // 1. Retrofit 의 Service는 BaseURL과는 연관이 없다.
        // 2. Retrofit은 여러개를 만들어서 사용해도 상관 없다.
        // 3. Retrofit의 Parameter는 반드시 URL encoded 되지 않은것으로 사용해야한다.
        // 4. Retrofit의 Parameter는 절대 getString(R.string.name) 으로 가지고 온것을 사용하면 안된다.
        RetrofitService service = RetrofitAdapter.getInstance("https://graph.instagram.com/", getContext());
        Call<FeedResult> call = service.getFeedDetails(sampleFeedID, fields, access_token);

        call.enqueue(new retrofit2.Callback<FeedResult>() {
            @Override
            public void onResponse(Call<FeedResult> call, retrofit2.Response<FeedResult> response) {
                if (response.isSuccessful()) {
                    Log.d("GridViewAdapter", "onResponse: Success " + response.body().data.size());
                    imageDataList.clear();
                    imageDataList.addAll(response.body().data);
                    feedAapter.refresh(imageDataList); //pass update list
                } else {
                    imageDataList.add(new Restaurant("한결이네", "치킨집", "어딨는지 나도 몰라", 45, 45, 50, 50, "https://i.pinimg.com/736x/0b/2f/8a/0b2f8a51314ab1ebe0505aee843a33b1.jpg"));
                    imageDataList.add(new Restaurant("한결이네", "치킨집", "어딨는지 나도 몰라", 45, 45, 50, 50, "https://post-phinf.pstatic.net/MjAxOTEyMDRfOSAg/MDAxNTc1NDI1MTg2MDE2.b1S1g-yhSiy6hxFJOoMsO7-PlMTc2iWAdznJ2xZwTxQg.3kTCo5pOPX6G3wtYR1AAYeGetDTkOXO2xTCM0SU4bNcg.JPEG/253-%EC%95%84%EC%9D%B4%EC%9C%A04.jpg?type=w1200"));
                    feedAapter.refresh(imageDataList);
                    Log.d("GridViewAdapter", "onResponse: Fail " + response.body());
                }
            }

            @Override
            public void onFailure(Call<FeedResult> call, Throwable t) {
                Toast.makeText(getContext(), "Please reloading", Toast.LENGTH_SHORT).show();
            }
        });
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
    public void onItemSelected(View v, int position) {
        ((MainActivity)getActivity()).replaceFragmentFull(FeedViewer.newInstance(imgList.get(position)));
    }

    public void updateText(int position){
        title.setText(imageDataList.get(position).restaurantName);
        numLike.setText("종아요 " + imageDataList.get(position).likes);
        numComment.setText("댓글 " + imageDataList.get(position).replys);
        caption.setText(imageDataList.get(position).restaurantDetail);
        hashTag.setText(imageDataList.get(position).address);
    }

}
