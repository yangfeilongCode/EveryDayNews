package feicui.edu.everydaynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.entity.CommentData;

/**
 * 评论界面适配器
 * Created by Administrator on 2016/10/12.
 */
public class CommentAdapter extends MyBaseAdapter<CommentData> {
    public CommentAdapter(Context mContext, ArrayList mList, int mLayoutId) {
        super(mContext, mList, mLayoutId);
    }
    /**
     * 渲染
     * @param holder  对应条目的Holder
     * @param convertView  对应条目的View
     * @param position  对应条目的位置
     */
    @Override
    public void putView(Holder holder, View convertView, int position, CommentData data) {
        ImageView mIvIcon= (ImageView) convertView.findViewById(R.id.iv_comment_icon);//评论头像
        TextView mTvIdName= (TextView) convertView.findViewById(R.id.tv_comment_idName);//用户id名
        TextView mTvTime= (TextView) convertView.findViewById(R.id.tv_comment_time);//评论时间
        TextView mTvContent= (TextView) convertView.findViewById(R.id.tv_comment_content);//评论内容
//        mIvIcon.setImageResource();
        Glide.with(mContext).load(data.portrait).into(mIvIcon);//设置评论头像  《load 装填
        mTvIdName.setText(data.uid); //用户id名
        mTvTime.setText(data.stamp); //评论时间
        mTvContent.setText(data.content); //内容

    }
}
