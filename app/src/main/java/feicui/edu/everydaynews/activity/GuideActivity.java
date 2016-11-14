package feicui.edu.everydaynews.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.adapter.GuideAdapter;

/**
 * 引导界面
 * Created by Administrator on 2016/9/30.
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    /**
     * 图片源
     */
    int[]photo={R.mipmap.small,R.mipmap.welcome,R.mipmap.wy};
    Button mBtnJump; //跳转按钮
    String PREFERENCE_NAME="prefrence_settings";   //选择设置
    SharedPreferences preference;  //用来存储小数据
    ViewPager pager; //左右滑动控件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        if (!getData()){ //不是第一次进入 直接跳转加载页面
            Intent intent=new Intent(this,LoadingActivity.class);
            startActivity(intent);
            finish(); //结束引导界面
        }
    }

    @Override
    void initView() {

        mLlBase.setVisibility(View.GONE); //隐藏继承基类的头条目布局

        pager= (ViewPager) findViewById(R.id.vp_guide); //滑动布局
        mBtnJump= (Button) findViewById(R.id.btn_guide); //跳转按钮
        //数据源 图片
        ArrayList<ImageView> listImg=new ArrayList<>();
        for (int i = 0; i <photo.length; i++) { //遍历图片
            ImageView img=new ImageView(this); //实例化一个ImageView（代码布局）
            img.setImageResource(photo[i]); //依次设置图片
            listImg.add(img); //添加到视图集合
        }
        GuideAdapter adapter=new GuideAdapter(listImg); //初始化引导界面适配器
         //左右滑动控件绑定监听事件
        pager.setAdapter(adapter); //绑定适配器
        pager.addOnPageChangeListener(this); //记录改变监听方法
        mBtnJump.setOnClickListener(this); //跳转按钮点击监听事件

    }

    /**
     * 获取登录数据
     * @return  返回结果 true/ false
     */
    public boolean getData(){
        preference=getSharedPreferences(PREFERENCE_NAME,MODE_PRIVATE); //获取数据存储
        boolean isFrist=preference.getBoolean("is_first",true);//设置初始状态
        return isFrist; //返回初始状态
    }

    /**
     * 页面弯曲
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 页面选择
     * @param position  对应下标
     */
    @Override
    public void onPageSelected(int position) {
        if (position==0){
            mBtnJump.setVisibility(View.GONE); //隐藏跳转按钮
        }else if (position==1){
            mBtnJump.setVisibility(View.GONE);//隐藏跳转按钮
        }else if(position==2){ //最后一页图片
           mBtnJump.setVisibility(View.VISIBLE); //显示跳转按钮
       }
    }

    /**
     * 页面滚动状态发生的变化
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 点击响应事件
     * @param v  对象所对应的View
     */
    @Override
    public void onClick(View v) {
		/*
		 * 1.拿到编译器
		 * 2。写数据
		 * 3.提交  是否生效
		 */
        SharedPreferences.Editor edt=preference.edit(); //编译
        edt.putBoolean("is_first", false); //改变初始状态
        edt.commit(); //提交

       //首次进入从此处跳转
        Intent intent=new Intent(this,LoadingActivity.class);
        startActivity(intent);
        finish(); //结束
    }
}
