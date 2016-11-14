package feicui.edu.everydaynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import feicui.edu.everydaynews.R;

/**
 * 加载页面
 * Created by Administrator on 2016/10/17.
 */
public class LoadingActivity extends BaseActivity{
    ImageView mIvLoading; //加载图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    @Override
    void initView() {
        mLlBase.setVisibility(ImageView.GONE);
        mIvLoading= (ImageView) findViewById(R.id.iv_loading_logo);
       /**
        * 补间动画  渐变
        */
        Animation animation=new AlphaAnimation(0.1f,1.0f); //透明图由0.1f--1.0f
        animation.setDuration(3000); //持续时间
        animation.setRepeatCount(Animation.ABSOLUTE); //重复次数（1次）
        mIvLoading.startAnimation(animation); //启动动画

        /**
         * 延时跳转
         */
        Timer timer=new Timer(); //初始化一个计时器
        timer.schedule(new TimerTask() { //设置时间表  new 一个匿名内部类时间任务
            @Override
            public void run() { //运行
                Intent intent=new Intent(LoadingActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();//结束加载页面
            }
        },3000);//延时3s

    }

}
