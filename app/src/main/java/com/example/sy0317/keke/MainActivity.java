package com.example.sy0317.keke;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.sy0317.keke.fragment.ButlerFragment;
import com.example.sy0317.keke.fragment.DownFragment;
import com.example.sy0317.keke.fragment.FindFragment;
import com.example.sy0317.keke.fragment.UserFragment;
import com.example.sy0317.keke.ui.SettingActivity;
import com.example.sy0317.keke.utils.L;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1234;
    private static String TAG = "KeKe_MainActivity";
    //Tablayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mviewPager;
    //TitleList
    private List<String> mTitle;
    //Fragment
    private List<Fragment>mFragment;
    //悬浮窗
    private FloatingActionButton fab_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar 即隐藏标题栏
        getSupportActionBar().hide();// 隐藏ActionBar
        setContentView(R.layout.activity_main);
//        loadTutorial();

        L.e("test");
        L.d("test");
        L.i("test");
        L.w("test");
        //取代阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();

    }


    //初始化data
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.title_name1));
        mTitle.add(getString(R.string.title_name2));
        mTitle.add(getString(R.string.title_name3));
        mTitle.add(getString(R.string.title_name4));

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new DownFragment());
        mFragment.add(new FindFragment());
        mFragment.add(new UserFragment());
    }
    //初始化view
    private void initView() {
        fab_setting = findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        //默认隐藏
        fab_setting.setVisibility(View.GONE);
        mTabLayout = findViewById(R.id.mTabLayout);
        mviewPager = findViewById(R.id.mViewPager);
        //预加载
        mviewPager.setOffscreenPageLimit(mFragment.size());

        mviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG,"position = "+position);
                if (position == 0){
                    fab_setting.setVisibility(View.GONE);
                }else {
                    fab_setting.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mviewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mviewPager);

    }


    public void loadTutorial() {
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);

    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem(
                R.string.slide_1_african_story_books,
                R.string.slide_1_african_story_books,
                R.color.slide_1, R.drawable.tut_page_1_front,
                R.drawable.tut_page_1_background);

        TutorialItem tutorialItem2 = new TutorialItem(
                R.string.slide_2_volunteer_professionals,
                R.string.slide_2_volunteer_professionals_subtitle,
                R.color.slide_2,
                R.drawable.tut_page_2_front,
                R.drawable.tut_page_2_background);

        TutorialItem tutorialItem3 = new TutorialItem(context.getString(
                R.string.slide_3_download_and_go), null,
                R.color.slide_3,
                R.drawable.tut_page_3_foreground);

        TutorialItem tutorialItem4 = new TutorialItem(
                R.string.slide_4_different_languages,
                R.string.slide_4_different_languages_subtitle,
                R.color.slide_4,
                R.drawable.tut_page_4_foreground,
                R.drawable.tut_page_4_background);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        tutorialItems.add(tutorialItem4);

        return tutorialItems;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
