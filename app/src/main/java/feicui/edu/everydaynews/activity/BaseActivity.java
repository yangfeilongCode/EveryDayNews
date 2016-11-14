package feicui.edu.everydaynews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import feicui.edu.everydaynews.R;

/**
 * 基类Activity
 * Created by Administrator on 2016/9/29.
 */
public abstract class BaseActivity extends FragmentActivity {
    public  LinearLayout mLlBase; //基类的头条布局
    public  RelativeLayout mRlContent; //子布局内容
    public  ImageView mIvLeft;  //左图标
    public ImageView mIvRight;  //右图标
    public TextView mTvTitle;  //中间头条目文字
    public TextView mTvComment; //跟帖数
    public LayoutInflater mInflater;  //布局填充器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base); //父类调

        mLlBase= (LinearLayout) findViewById(R.id.ll_base);  //头条目布局
        mIvLeft = (ImageView) findViewById(R.id.iv_base_left); //左图标
        mIvRight = (ImageView) findViewById(R.id.iv_base_right); //右图标
        mTvTitle = (TextView) findViewById(R.id.tv_base_title); //中间头条文字
        mRlContent = (RelativeLayout) findViewById(R.id.rl_base_content);//子布局内容容器
        mTvComment= (TextView) findViewById(R.id.tv_base_comment); //评论
    }

    /**
     * 传id
     * 模板设计  在加载布局之后  自动调用initView（）
     */
    public void setContentView(int id) {
        mInflater = this.getLayoutInflater(); //获得布局填充器
        mInflater.inflate(id, mRlContent);

        initView();
    }

    /**
     * 传view
     * 模板设计  在加载布局之后  自动调用initView（）
     */
    public void setContentView(View view) {
        mRlContent.addView(view);
       initView();
    }

    /**
     * 用于子类初始化工作的抽象方法
     */
     abstract void initView();

    /**
     * listener绑定的监听器
     * view所要绑定的点击事件的view
     */
    protected void setOnClickListeners(View.OnClickListener listener, View... views) {

        if (listener == null) {
            return;
        }
        //给每个view 绑定监听事件
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * listener绑定的监听器
     * view所要绑定的点击事件的id
     */
   protected void  setOnClickListeners(View.OnClickListener listener,int... ids){
       if(listener==null){
           return;
       }
       for (int id:ids){
           findViewById(id).setOnClickListener(listener);
       }
    }

}
