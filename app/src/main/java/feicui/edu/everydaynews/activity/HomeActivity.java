package feicui.edu.everydaynews.activity;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import cn.jpush.android.api.JPushInterface;
import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.activity.fragment.FavoriteFragment;
import feicui.edu.everydaynews.activity.fragment.LeftFragment;
import feicui.edu.everydaynews.activity.fragment.LoginFragment;
import feicui.edu.everydaynews.activity.fragment.NewsFragment;
import feicui.edu.everydaynews.activity.fragment.PasswordFragment;
import feicui.edu.everydaynews.activity.fragment.RegisterFragment;
import feicui.edu.everydaynews.activity.fragment.RightFragment;
import feicui.edu.everydaynews.view.MySQLiteOpenHelper;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

      DrawerLayout mDrawerLayout; //左右滑动布局
      LinearLayout mLLNewsTittle; //新闻分类头布局
      ImageView mIvNewsRight; //新闻右边的箭头图片
//      public String name;//接收的用户名
//      public Icon icon;///接收的用户图标

      NewsFragment mNewsFragment=new NewsFragment(); //新闻碎片
      LeftFragment mLeftFragment=new LeftFragment(); //左边展示列表碎片
      RightFragment mRightFragment=new RightFragment(); //右边登录及应用更新碎片
      public  LoginFragment mLoginFragment=new LoginFragment();//登录碎片
      RegisterFragment mRegisterFragment=new RegisterFragment(); //注册碎片
      PasswordFragment mPasswordFragment=new PasswordFragment();//忘记密码碎片
      FavoriteFragment mFavoriteFragment=new FavoriteFragment();//收藏碎片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        JPushInterface.setDebugMode(true); //设置极光推送调试模式
        JPushInterface.init(this);  //初始化
        /**
         * 左右滑动
         *  新布局 DrawerLayout
         *    里面需要一个控件  充满全屏
         */
    }
    @Override
    void initView() {

        mTvTitle.setText("资讯"); //更改头条布局文字
        mTvComment.setVisibility(View.GONE); //隐藏跟帖文字
        mIvLeft.setOnClickListener(this);//头条目布局左侧图标
        mIvRight.setOnClickListener(this);//头条目布局右侧图标

        mIvNewsRight= (ImageView) findViewById(R.id.iv_home_right_image); //新闻头条目右侧箭头
        mIvNewsRight.setOnClickListener(this); //绑定监听

        mDrawerLayout= (DrawerLayout) findViewById(R.id.dl_home); //左右滑动总布局
        mLLNewsTittle= (LinearLayout) findViewById(R.id.ll_home_tittle);//新闻分类条目

        FragmentManager fm=getSupportFragmentManager(); //获得支持碎片管理
        FragmentTransaction transaction=fm.beginTransaction(); //启动事务
        transaction.add(R.id.ll_home_centre,mNewsFragment); //将新闻碎片添加到中间布局中
        transaction.add(R.id.ll_home_left,mLeftFragment);  //添加左布局
        transaction.add(R.id.ll_home_right,mRightFragment); //添加右布局
        transaction.add(R.id.ll_home_centre,mLoginFragment);//添加登录
        transaction.add(R.id.ll_home_centre,mRegisterFragment); //注册碎片加入中间布局中
        transaction.add(R.id.ll_home_centre,mPasswordFragment);//忘记密码碎片加入中间布局中
        transaction.add(R.id.ll_home_centre,mFavoriteFragment);//添加收藏
        transaction.show(mNewsFragment);//显示新闻主界面
        transaction.hide(mLoginFragment);
        transaction.hide(mRegisterFragment); //隐藏
        transaction.hide(mPasswordFragment);
        transaction.hide(mFavoriteFragment);
        transaction.commit(); //提交

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_base_left: //头条目左侧图标
                mDrawerLayout.openDrawer(Gravity.LEFT);
                mDrawerLayout.closeDrawer(Gravity.RIGHT);

                break;
            case R.id.iv_base_right: //头条目右侧图标
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.iv_home_right_image: //右侧箭头
                Toast.makeText(this,"完善中，敬请期待......",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     *  替换碎片方法
     * @param flag
     */
    public void replaceFragment(int flag){
        switch (flag){
            case 0: //展示注册碎片
                mTvTitle.setText("用户注册");
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();//启动事务
                transaction.show(mRegisterFragment);  //展示
                transaction.hide(mLoginFragment);
                transaction.hide(mPasswordFragment); //隐藏
                transaction.hide(mNewsFragment);
                transaction.hide(mFavoriteFragment);
                transaction.commit(); //提交
                mLLNewsTittle.setVisibility(View.GONE); //隐藏新闻分类头条目

                break;
            case 1://展示忘记密码碎片
                mTvTitle.setText("忘记密码");
                FragmentManager manager1=getSupportFragmentManager();
                FragmentTransaction transaction1=manager1.beginTransaction();//启动事务
                transaction1.hide(mRegisterFragment);
                transaction1.hide(mLoginFragment);
                transaction1.show(mPasswordFragment);
                transaction1.hide(mNewsFragment);
                transaction1.hide(mFavoriteFragment);
                transaction1.commit();
                mLLNewsTittle.setVisibility(View.GONE);
                break;

            case 2://展示登录主界面碎片
                mTvTitle.setText("用户登录");
                FragmentManager manager2=getSupportFragmentManager();
                FragmentTransaction transaction2=manager2.beginTransaction();//启动事务
                transaction2.show(mLoginFragment);
                transaction2.hide(mRegisterFragment);
                transaction2.hide(mPasswordFragment);
                transaction2.hide(mNewsFragment);
                transaction2.hide(mFavoriteFragment);
                transaction2.commit();
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                mLLNewsTittle.setVisibility(View.GONE);
                break;
            case 3://展示收藏碎片
                mTvTitle.setText("收藏");
                FragmentManager manager3=getSupportFragmentManager();
                FragmentTransaction transaction3=manager3.beginTransaction();//启动事务
                transaction3.hide(mLoginFragment);
                transaction3.hide(mRegisterFragment);
                transaction3.hide(mPasswordFragment);
                transaction3.hide(mNewsFragment);
                transaction3.show(mFavoriteFragment);
                transaction3.commit();
                mDrawerLayout.closeDrawer(Gravity.LEFT); //收回左边碎片
                mLLNewsTittle.setVisibility(View.GONE);
                break;
        }

    }
}
/**
 *    transaction.hide(two);//隐藏
 *    transaction.show(one);//显示
 *    ransaction.replace(R.id.ll_home_centre,mNewsFragment);//替换
 */

/**
 * 推送
 *     服务端有最新消息 需要将消息发送给客户端
 *   1.轮循：  在客户端写死循环 去时刻监听服务端是否有新消息
 *           不停的调用服务端暴露地接口消息
 *   2.消息： 耗资金
 *   3.第三方推送平台： 极光推送   个推
 *   4.更换其他协议
 */
