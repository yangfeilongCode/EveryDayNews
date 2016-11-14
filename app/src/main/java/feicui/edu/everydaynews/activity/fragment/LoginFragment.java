package feicui.edu.everydaynews.activity.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.activity.HomeActivity;
import feicui.edu.everydaynews.activity.UserDetailActivity;
import feicui.edu.everydaynews.entity.LoginArray;
import feicui.edu.everydaynews.net.Constants;
import feicui.edu.everydaynews.net.MyHttp;
import feicui.edu.everydaynews.net.OnResultFinishListener;
import feicui.edu.everydaynews.net.Response;
import feicui.edu.everydaynews.view.RegixInput;
import feicui.edu.everydaynews.view.ServerURL;
import feicui.edu.everydaynews.view.MySQLiteOpenHelper;

/**
 * 登录碎片
 * Created by Administrator on 2016/10/13.
 */
public class LoginFragment extends Fragment implements TextWatcher, View.OnClickListener {

    TextInputLayout mTilName; //用户名输入框的父容器
    TextInputLayout mTilWord; //密码输入框的父容器
    TextInputEditText mEtUserName;  //用户名
    TextInputEditText mEtPassword;  //登录密码
    TextView mTvRegister;  //注册按钮
    TextView mTvForget; //忘记密码按钮
    TextView mTvLong;  //登录按钮
    String userName;  //接收的用户名
    String userWord;  //接收的登录密码
    LoginArray array; //登陆集合
//    SharedPreferences preferences;//存储小数据

    SQLiteDatabase database; //数据库的执行者

    View mView;
    HomeActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_login,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("aaa","Constants.TOKEN-----Login-----"+Constants.TOKEN);

            mView=getView();
            activity= (HomeActivity) getActivity();
            mTilName= (TextInputLayout) mView.findViewById(R.id.til_login_name); //用户名输入框的父容器
            mTilWord= (TextInputLayout) mView.findViewById(R.id.til_login_word); //密码输入框的父容器
            mEtUserName= (TextInputEditText)mView. findViewById(R.id.et_user_name); //用户名输入框
            mEtPassword= (TextInputEditText) mView.findViewById(R.id.et_user_password); //密码输入框
            mTvRegister= (TextView)mView. findViewById(R.id.tv_user_login_register);//注册
            mTvForget= (TextView) mView.findViewById(R.id.tv_user_login_forget); //忘记密码
            mTvLong= (TextView) mView.findViewById(R.id.tv_user_login_login); //登陆
            mEtUserName.addTextChangedListener(this); //用户名输入框文字改变监听事件(新控件的监听方法）
            mEtPassword.addTextChangedListener(this); //密码输入框文字改变监听事件
            mTvRegister.setOnClickListener(this); //注册监听
            mTvForget.setOnClickListener(this);  //忘记密码监听
            mTvLong.setOnClickListener(this);  //登陆监听

//          SQLiteOpenHelper helper=new MySQLiteOpenHelper(activity);
//          database=helper.getWritableDatabase();

             MySQLiteOpenHelper helper=new MySQLiteOpenHelper(activity);
             database=helper.getWritableDatabase();

//        mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
        mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏 密码

    }

    /**
     * 新控件监听方法
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEtUserName.length()>3){
            mEtUserName.setError(null);
        }else{
            mEtUserName.setError("你所输入用户名的长度不够"); //警示文字
        }
        if (mEtPassword.length()>6&&mEtPassword.length()<16){
            mEtPassword.setError(null);
        }else{
            mEtPassword.setError("密码：6到16位个字符");
        }
    }
//-----------------------------点击事件方法---------------------------------------------------------
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_user_login_register: //注册
                activity.replaceFragment(0);
                break;

            case R.id.tv_user_login_forget: //忘记密码
                activity.replaceFragment(1);
                break;

            case R.id.tv_user_login_login: //登陆

                userName=mEtUserName.getText().toString();
                userWord=mEtPassword.getText().toString();

                if (userName.equals("")||userWord.equals("")){
                    Toast.makeText(activity,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }else if (RegixInput.passWordLenth(userWord)){ //正则判断
                    getHttpData();

                }else {
                    Toast.makeText(activity,"密码格式不正确！",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }

    /**
     * 登录接口----获取网络数据
     *  ver=0000000&ver=版本号&uid=用户名&pwd=密码&device=0
     */
    public void getHttpData(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000"); //版本

//----------判断静态池里是否有数据（实际是判断数据库里是否有数据）----------------------------------

        if (Constants.USER_NAME!=null&&Constants.PASSWORD!=null){
            params.put("uid",""+Constants.USER_NAME);
            params.put("pwd",""+Constants.PASSWORD);
            Log.e("SFD-----","Constants.USER_NAME----"+Constants.USER_NAME+"---Constants.PASSWORD----"+Constants.PASSWORD);
        }else { //静态池无数据 用新输入数据
            params.put("uid",""+userName);
            params.put("pwd",""+userWord);
            Log.e("aaa--","userName------"+userName+"-------userWord--------"+userWord);
        }
//--------------------------------------------------------------------------------------------------
        params.put("device","0");
        MyHttp.get(activity, ServerURL.USER_LOGIN, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {  //成功
                Log.e("aaa","response.toString()----------------"+response.result);

                Gson gson=new Gson();
                array=gson.fromJson( response.result,LoginArray.class);
                Log.e("aaa","array00000000"+array.toString());
                switch (array.getStatus()) {
                    case 0: //正常登陆
                        Toast.makeText(activity,array.data.getExplain(), Toast.LENGTH_SHORT).show();
                       //每登录成功一次 给TOKEN赋一次值
                        Constants.TOKEN=array.data.token;  //token值会变
 //--------------------------数据库添加数据-------------------------------------------------------
                        //数据库无数据 需要将数据库添加数据
                        if (Constants.USER_NAME==null||Constants.PASSWORD==null) {
                            ContentValues values = new ContentValues();
                            values.put("uid", userName);//用户名
                            values.put("password", userWord); //密码
                            database.insert("news", null, values);
                            Log.e("aaaa---", "database------------" + database); // 数据库存储路径
  //aaaa---: database------------SQLiteDatabase: /data/data/feicui.edu.everydaynews/databases/news_db
                        }
//--------------------------------------------------------------------------------------------------
                      //跳转用户中心
                        Intent intent = new Intent(activity, UserDetailActivity.class);
                        startActivity(intent);

                        break;
                    case -1://用户名或密码错误
                        Toast.makeText(activity, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    case -2: //限制登陆(禁言,封IP)
                        Toast.makeText(activity, "限制登陆(禁言,封IP)", Toast.LENGTH_SHORT).show();
                        return;
                    case -3://限制登陆(异地登陆等异常)
                        Toast.makeText(activity, array.data.getExplain(), Toast.LENGTH_SHORT).show();
                        return;
                }
            }

            @Override
            public void failed(Response response) {//失败
                Toast.makeText(activity,"登录失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
