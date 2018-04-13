package com.example.sy0317.keke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sy0317.keke.MainActivity;
import com.example.sy0317.keke.R;
import com.example.sy0317.keke.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shushu on 2018/4/10.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.ui
 * 文件名：GuideActivity
 * 描述    KeKe
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener  {
    private ViewPager mViewPager;

    private List<View> mList = new ArrayList<>();
    private View view1,view2,view3;
    private ImageView point1,point2,point3;
    private ImageView iv_break;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.mViewPager);

        view1 = View.inflate(this,R.layout.pager_item_one,null);
        view2 = View.inflate(this,R.layout.pager_item_two,null);
        view3 = View.inflate(this,R.layout.pager_item_three,null);
        iv_break = findViewById(R.id.iv_back);
        iv_break.setOnClickListener(this);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);

        //设置默认的图片中的小圆点
        setPointImg(true,false,false);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //pager切换的监听
            @Override
            public void onPageSelected(int position) {
                L.i("position = "+position);
                switch (position){
                    case 0:
                        setPointImg(true,false,false);
                        iv_break.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false,true,false);
                        iv_break.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false,false,true);
                        iv_break.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.iv_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }


    }


    class  GuideAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view  == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }

    //设置小圆点的选择效果
    private void setPointImg(boolean isCheck1,boolean isCheck2,boolean isCheck3){
        if (isCheck1){
            point1.setBackgroundResource(R.drawable.point_on);
        }else {
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if (isCheck2){
            point2.setBackgroundResource(R.drawable.point_on);
        }else {
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if (isCheck3){
            point3.setBackgroundResource(R.drawable.point_on);
        }else {
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }

}
