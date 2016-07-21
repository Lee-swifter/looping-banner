package lic.swifter.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lee-swifter on 16-7-12.
 */
public class LoopingViewPager extends ViewPager {

    private long loopInterVal;
    private boolean autoScroll;
    private boolean scrolling;

    private ScheduledExecutorService service;
    private ScheduledFuture future;

    private Runnable looper = new Runnable() {
        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {
                    setCurrentItem(getCurrentItem() + 1, true);
                }
            });
        }
    };

    public LoopingViewPager(Context context) {
        this(context, null);
    }

    public LoopingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.looping_banner);
        loopInterVal = typedArray.getInt(R.styleable.looping_banner_loop_interval, 3000);
        autoScroll = typedArray.getBoolean(R.styleable.looping_banner_auto_scroll, false);
        typedArray.recycle();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (!(adapter instanceof LoopingPagerAdapter))
            throw new IllegalArgumentException("adapter must be a subclass of LoopingViewPager.Adapter");

        super.setAdapter(adapter);

        int innerPosition = Integer.MAX_VALUE / 2;
        while (innerPosition % ((LoopingPagerAdapter) adapter).getPageCount() != 0) {
            innerPosition++;
        }
        setCurrentItem(innerPosition);

        if (autoScroll)
            startScroll();
    }

    public int getCurrentPage() {
        return getCurrentItem() % ((LoopingPagerAdapter) getAdapter()).getPageCount();
    }

    public void startScroll() {
        if (scrolling)
            return;

        if (service == null)
            service = Executors.newScheduledThreadPool(1);
        future = service.scheduleWithFixedDelay(looper, loopInterVal, loopInterVal, TimeUnit.MILLISECONDS);
        scrolling = true;
    }

    public void stopScroll() {
        if (!scrolling)
            return;

        if (future != null)
            future.cancel(true);
        scrolling = false;
    }

    public void setInterVal(long interVal) {
        loopInterVal = interVal;
    }

    public void release() {
        if (future != null)
            future.cancel(true);
        if (service != null) {
            service.shutdownNow();
            service = null;
        }
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