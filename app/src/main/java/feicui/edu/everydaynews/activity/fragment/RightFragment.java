package feicui.edu.everydaynews.activity.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.activity.HomeActivity;
import feicui.edu.everydaynews.activity.UserDetailActivity;
import feicui.edu.everydaynews.entity.UpDateInfo;
import feicui.edu.everydaynews.net.Constants;
import feicui.edu.everydaynews.net.MyHttp;
import feicui.edu.everydaynews.net.OnResultFinishListener;
import feicui.edu.everydaynews.net.Response;
import feicui.edu.everydaynews.view.MySQLiteOpenHelper;
import feicui.edu.everydaynews.view.ServerURL;

/**
 * 右边列表碎片
 * Created by Administrator on 2016/10/8.
 */
public class RightFragment extends Fragment implements View.OnClickListener {
    ImageView mIvLogin; //登录圆图
    TextView mTvLogin; // 登录文字
    TextView mTvUpdate;  //应用更新
    View mView;
    HomeActivity activity;
    UpDateInfo info;  //应用更新信息

    SQLiteDatabase database;
    String userName; //接收查询数据库得到的用户名
    String userPass; //接收查询数据库得到的用户密码


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_right,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView=getView();
        activity= (HomeActivity) getActivity();
        mIvLogin= (ImageView) mView.findViewById(R.id.iv_login);//登录圆图
        mTvLogin= (TextView) mView.findViewById(R.id.tv_login_home_login);// 登录文字
        mTvUpdate= (TextView) mView.findViewById(R.id.tv_app_update); //应用更新文字
        //绑定点击监听事件
        mTvUpdate.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mIvLogin.setOnClickListener(this);

//----------------------查询数据库------------------------------------------------------------------
        MySQLiteOpenHelper helper=new MySQLiteOpenHelper(activity);
        database=helper.getWritableDatabase();
        //游标
        Cursor cursor=database.query("news",null,null,null,null,null,"uid desc");
        while(cursor.moveToNext()){//移动到下一列（是判断）
            //接收数据库数据
            userName=cursor.getString(cursor.getColumnIndex("uid"));
            userPass=cursor.getString(cursor.getColumnIndex("password"));
            Log.e("aaa---数据库查询---", "onClick:-------"+userName+"-----"+userPass );
            //判断数据库是否有数据
            if (userName!=null&&userPass!=null){
                //数据库有数据给静态池的对用属性赋值
                Constants.USER_NAME=userName; //用户名
                Constants.PASSWORD=userPass;//用户密码
                mTvLogin.setText(userName); //设置用户名文字
            }
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_login: //立即登陆(图片)

                //数据库是否为null
                if (userName==null||userPass==null){ //为null 进登录碎片
                    activity.replaceFragment(2);
                }else { //不为null
                    activity.mLoginFragment.getHttpData(); //调登录接口
                }
                break;

            case R.id.tv_login_home_login: //立即登陆(文字)
                //数据库是否为null
                if (userName==null||userPass==null){ //为null 进登录碎片
                    activity.replaceFragment(2);
                }else { //不为null
                    activity.mLoginFragment.getHttpData(); //调登录接口
                }
                break;

            case R.id.tv_app_update: //应用更新

//                upDate();
                Toast.makeText(activity,"已是最新版本",Toast.LENGTH_SHORT).show();
                break;
        }
    }
//-------------------------接口---------------------------------------------------------------------
    /**
     * 应用更新
     * update?imei=唯一识别号&pkg=包名&ver=版本
     */
    public void upDate() {
        Map<String, String> params = new HashMap<>();
        params.put("imei", ""+000000000000000);
        params.put("pkg", "");
        params.put("ver", "0000000");
        MyHttp.get(activity, ServerURL.UPDATE, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson=new Gson();
                info=gson.fromJson(response.result.toString(),UpDateInfo.class);

            }

            @Override
            public void failed(Response response) {

            }
        });
    }
}
