package feicui.edu.everydaynews.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.security.Guard;
import java.util.ArrayList;
import feicui.edu.everydaynews.entity.News;
import feicui.edu.everydaynews.R;

/**
 *  新闻列表适配器
 * Created by Administrator on 2016/9/27.
 */
public class NewsListAdapter extends MyBaseAdapter<News> {

    public NewsListAdapter(Context mContext, ArrayList mList, int mLayoutId) {
        super(mContext, mList, mLayoutId);
    }
    /**
     * 渲染
     * @param holder  对应条目的Holder
     * @param convertView  对应条目的View
     * @param position  对应条目的位置
     */
    @Override
    public void putView(Holder holder, View convertView, int position, News news) {

        ImageView newsIcon= (ImageView) convertView.findViewById(R.id.iv_home_news_icon);  //新闻图片
        TextView newsTitle= (TextView) convertView.findViewById(R.id.tv_item_home_news_title); //新闻标题
        TextView newsSummary= (TextView) convertView.findViewById(R.id.tv_item_home_news_summary);//摘要
        TextView newsTime= (TextView) convertView.findViewById(R.id.tv_item_home_news_time); //时间
        Glide.with(mContext).load(news.getIcon()).into(newsIcon);  //新闻图片  图片加载用到第三方jar包
        newsTitle.setText(news.getTitle()); //新闻标题
        newsSummary.setText(news.getSummary()); //新闻摘要
        newsTime.setText(news.getStamp()+"");  //时间



    }


}
