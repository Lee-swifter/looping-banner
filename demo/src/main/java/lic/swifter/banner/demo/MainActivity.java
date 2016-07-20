package lic.swifter.banner.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lic.swifter.banner.LoopingPagerAdapter;
import lic.swifter.banner.LoopingViewPager;

public class MainActivity extends AppCompatActivity {

    private LoopingViewPager loopingViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loopingViewPager = (LoopingViewPager) findViewById(R.id.looping_view_pager);
        loopingViewPager.setAdapter(new MainAdapter());

    }

    private class MainAdapter extends LoopingPagerAdapter {

        @Override
        public List<View> setViews() {
            List<View> viewList = new ArrayList<>();
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

            View view_1 = inflater.inflate(R.layout.page_looping_view, loopingViewPager, false);
            view_1.setBackgroundResource(R.drawable.home_group_1_back);
            viewList.add(view_1);

            View view_2 = inflater.inflate(R.layout.page_looping_view, loopingViewPager, false);
            view_2.setBackgroundResource(R.drawable.home_group_2_back);
            viewList.add(view_2);

            View view_3 = inflater.inflate(R.layout.page_looping_view, loopingViewPager, false);
            view_3.setBackgroundResource(R.drawable.home_group_3_back);
            viewList.add(view_3);

            View view_4 = inflater.inflate(R.layout.page_looping_view, loopingViewPager, false);
            view_4.setBackgroundResource(R.drawable.home_group_4_back);
            viewList.add(view_4);

            return viewList;
        }
    }
}
