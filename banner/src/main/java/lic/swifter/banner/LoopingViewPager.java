package lic.swifter.banner;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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

    private long loopInterVal;

    private boolean scrolling;

    private ScheduledExecutorService service;
    private ScheduledFuture future;

    private Runnable looper = new Runnable() {
        @Override
        public void run() {
            Log.i("swifter", "loop called ...");
            post(new Runnable() {
                @Override
                public void run() {
                    setCurrentItem(getCurrentItem()+1, true);
                }
            });
        }
    };

    public LoopingViewPager(Context context) {
        this(context, null);
    }

    public LoopingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        loopInterVal = 3000L;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if(!(adapter instanceof LoopingPagerAdapter))
            throw new IllegalArgumentException("adapter must be a subclass of LoopingViewPager.Adapter");

        super.setAdapter(adapter);

        int innerPosition = Integer.MAX_VALUE / 2;
        while (innerPosition % ((LoopingPagerAdapter) adapter).getPageCount() != 0) {
            innerPosition++;
        }
        setCurrentItem(innerPosition);
    }

    public int getCurrentPage() {
        return getCurrentItem() % ((LoopingPagerAdapter) getAdapter()).getPageCount();
    }

    public void startScroll() {
        if(service == null)
            service = Executors.newScheduledThreadPool(1);
        future = service.scheduleWithFixedDelay(looper, loopInterVal, loopInterVal, TimeUnit.MILLISECONDS);
    }

    public void stopScroll() {
        future.cancel(true);
    }

    public void setInterVal(long interVal) {
        loopInterVal = interVal;
    }

    public void release() {
        Log.i("swifter", "release called ....");
        future.cancel(true);
        service.shutdownNow();
        service = null;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            scrolling = bundle.getBoolean("scrolling");
            state = bundle.getParcelable("parcelableState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("parcelableState", super.onSaveInstanceState());
        bundle.putBoolean("scrolling", scrolling);
        return super.onSaveInstanceState();
    }

}