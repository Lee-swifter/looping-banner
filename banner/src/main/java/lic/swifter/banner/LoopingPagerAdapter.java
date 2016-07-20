package lic.swifter.banner;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lic on 16-7-20.
 */
public abstract class LoopingPagerAdapter extends PagerAdapter {

    List<View> viewList;

    public abstract List<View> setViews();

    public LoopingPagerAdapter() {
        viewList = setViews();
    }

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
        int outerPosition = position % viewList.size();
        container.addView(viewList.get(outerPosition));
        return viewList.get(outerPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
