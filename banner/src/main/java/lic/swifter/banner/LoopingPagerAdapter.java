package lic.swifter.banner;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lee-swifter on 16-7-20.
 */
public abstract class LoopingPagerAdapter extends PagerAdapter {

    public abstract int getPageCount();

    public abstract View getPageView(LayoutInflater inflater, ViewGroup parent, int position);

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int outerPosition = position % getPageCount();

        View pageView = getPageView(LayoutInflater.from(container.getContext()), container, outerPosition);
        container.addView(pageView);
        return pageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
