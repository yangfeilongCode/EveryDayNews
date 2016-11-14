package feicui.edu.everydaynews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/29.
 */
public class GuideAdapter extends PagerAdapter {

    ArrayList<ImageView> listImg;
    public GuideAdapter(ArrayList<ImageView> listImg){
        this.listImg=listImg;

    }
    @Override
    public int getCount() {
        return listImg==null?0:listImg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 列出项目
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //添加视图（view  数据源）-----》ViewPager
        ImageView img=listImg.get(position);
//		img.setScaleType(ScaleType.FIT_XY);
        container.addView(img);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(listImg.get(position));
    }
}
