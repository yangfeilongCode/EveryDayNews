package feicui.edu.everydaynews.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.activity.HomeActivity;

/**
 * 左侧抽屉列表
 * Created by Administrator on 2016/10/8.
 */
public class LeftFragment extends Fragment implements View.OnClickListener {
    LinearLayout mLinNews; //新闻
    LinearLayout mLinFavorite;  //收藏
    LinearLayout mLinLooal; //本地
    LinearLayout mLinComment; //跟帖
    LinearLayout mLinPhoto; //图片

    View mView;
    HomeActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_left,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView=getView();
        activity= (HomeActivity) getActivity();
        mLinNews= (LinearLayout) mView.findViewById(R.id.ll_list_show_news);
        mLinFavorite= (LinearLayout) mView.findViewById(R.id.ll_list_show_favorite);
        mLinLooal= (LinearLayout)mView. findViewById(R.id.ll_list_show_looal);
        mLinComment= (LinearLayout)mView. findViewById(R.id.ll_list_show_comment);
        mLinPhoto= (LinearLayout) mView.findViewById(R.id.ll_list_show_photo);
        mLinNews.setOnClickListener(this);  //绑定监听事件
        mLinFavorite.setOnClickListener(this);
        mLinLooal.setOnClickListener(this);
        mLinComment.setOnClickListener(this);
        mLinPhoto.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_list_show_news: //新闻
                Intent intent=new Intent();
                intent.setClass(activity,HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_list_show_favorite: //收藏
               activity.replaceFragment(3);
                break;

            case R.id.ll_list_show_looal: //本地
                Toast.makeText(activity,"looal。。。",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_list_show_comment: //跟帖
                Toast.makeText(activity,"comment。。。",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_list_show_photo:  //图片
                Toast.makeText(activity,"photo。。。",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
