package lic.swifter.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by lic on 16-7-12.
 */
public class LoopingViewPager extends ViewPager {

    private int LOOP_INTERVAL ;
    private int innerPosition ;

    private ScheduledExecutorService service;
    private ScheduledFuture future;

    private Runnable looper = new Runnable() {
        @Override
        public void run() {
            Log.i("swifter", "looooooooooooooooop.");
            setCurrentItem((getCurrentItem() + 1) % getAdapter().getCount(), true);
        }
    };

    public LoopingViewPager(Context context) {
        this(context, null);
    }

    public LoopingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        LOOP_INTERVAL = 2000;
    }


    @Override
    public void setAdapter(PagerAdapter adapter) {
        if(!(adapter instanceof LoopingPagerAdapter))
            throw new IllegalArgumentException("adapter must be a subclass of LoopingViewPager.Adapter");

        super.setAdapter(adapter);

        innerPosition = Integer.MAX_VALUE / 2;
        while (innerPosition % ((LoopingPagerAdapter) adapter).viewList.size() != 0) {
            innerPosition++;
        }
        setCurrentItem(innerPosition);

        startScroll();
    }

    public void startScroll() {
        if(service == null)
            service = Executors.newScheduledThreadPool(1);
        future = service.scheduleWithFixedDelay(looper, LOOP_INTERVAL, LOOP_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void stopScroll() {
        future.cancel(true);
    }

    public void setInterVal(int interVal) {
        LOOP_INTERVAL = interVal;
    }

}