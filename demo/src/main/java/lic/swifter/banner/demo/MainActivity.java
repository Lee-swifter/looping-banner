package lic.swifter.banner.demo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lic.swifter.banner.LoopingPagerAdapter;
import lic.swifter.banner.LoopingViewPager;

public class MainActivity extends AppCompatActivity {

    private LoopingViewPager loopingViewPager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loopingViewPager = (LoopingViewPager) findViewById(R.id.looping_view_pager);
        loopingViewPager.setInterVal(2000);
        loopingViewPager.setAdapter(new MainAdapter());
        loopingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText(String.valueOf(loopingViewPager.getCurrentPage()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Button startButton = (Button) findViewById(R.id.start_scroll_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loopingViewPager.startScroll();
            }
        });

        Button stopButton = (Button) findViewById(R.id.stop_scroll_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loopingViewPager.stopScroll();
            }
        });

        textView = (TextView) findViewById(R.id.looping_text);
        textView.setText(String.valueOf(loopingViewPager.getCurrentPage()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loopingViewPager.release();
    }

    private class MainAdapter extends LoopingPagerAdapter {

        @Override
        public int getPageCount() {
            return 4;
        }

        @Override
        public View getPageView(LayoutInflater inflater, ViewGroup parent, final int position) {
            ImageView view = (ImageView) inflater.inflate(R.layout.page_looping_view, parent, false);
            switch (position) {
                case 0:
                    view.setImageResource(R.drawable.home_group_1_back);
                    break;
                case 1:
                    view.setImageResource(R.drawable.home_group_2_back);
                    break;
                case 2:
                    view.setImageResource(R.drawable.home_group_3_back);
                    break;
                case 3:
                    view.setImageResource(R.drawable.home_group_4_back);
                    break;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "position = "+position, Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }
}
