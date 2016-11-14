package feicui.edu.everydaynews.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.activity.ClauseActivity;
import feicui.edu.everydaynews.activity.HomeActivity;
import feicui.edu.everydaynews.entity.RegisterArray;
import feicui.edu.everydaynews.net.MyHttp;
import feicui.edu.everydaynews.net.OnResultFinishListener;
import feicui.edu.everydaynews.net.Response;
import feicui.edu.everydaynews.view.RegixInput;
import feicui.edu.everydaynews.view.ServerURL;

/**
 * 注册碎片
 * Created by Administrator on 2016/10/13.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    EditText mEtMailbox;  //邮箱地址
    EditText mEtUserName;  //用户名
    EditText mEtPassword;  //密码
    Button mBtnRegister; //注册按钮
 //  CheckBox mChBox;    //是否同意条款勾选框
    TextView mTvClause;  //条款文字
    String mailbox;  //接收的邮箱地址
    String userName;  //接收的用户名
    String password;  //接收的密码
    RegisterArray array;  //注册集合
    HomeActivity activity;
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView=getView();
        activity= (HomeActivity) getActivity();
        mEtMailbox = (EditText) mView.findViewById(R.id.et_login_register_mailbox); //邮箱
        mEtUserName = (EditText) mView.findViewById(R.id.et_login_register_name);//用户名
        mEtPassword = (EditText) mView.findViewById(R.id.et_login_register_password);//密码
        mBtnRegister = (Button) mView.findViewById(R.id.btn_login_register);// 注册按钮
     // mChBox = (CheckBox) mView.findViewById(R.id.ch_login_register);//勾选框
        mTvClause = (TextView) mView.findViewById(R.id.tv_login_register_clause); //条款文字
        mBtnRegister.setOnClickListener(this);
        mTvClause.setOnClickListener(this); //条款点击事件
   //    mChBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login_register: //注册
                //获得输入内容
                userName = mEtUserName.getText().toString();
                mailbox = mEtMailbox.getText().toString();
                password = mEtPassword.getText().toString();
                //判断格式是否正确
                if (userName.equals("")|| mailbox.equals("") || password.equals("")) { //输入不能为空
                    Toast.makeText(activity, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (RegixInput.IdNameLength(userName) && RegixInput.emailLength(mailbox)
                        && RegixInput.emailAdressFormat(mailbox) && RegixInput.passWordLenth(password)) {//正则
                    getHttpData(); //调接口

                } else {
                    Toast.makeText(activity, "输入的格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;

            case R.id.tv_login_register_clause: //条款
                Intent intent = new Intent();
                intent.setClass(activity, ClauseActivity.class); //跳到条款界面
                startActivity(intent);
                break;
        }

    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//    }

    /**
     * 注册接口
     * ver=0000000&uid=用户名&email=邮箱&pwd=登陆密码
     */
    public void getHttpData(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000"); //版本
        params.put("uid",""+userName);
        params.put("email",""+mailbox);
        params.put("pwd",""+password);

        MyHttp.get(activity, ServerURL.USER_REGISTER, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {  //成功
                Log.e("aaa","response.toString()----------------"+response.result);
                Gson gson=new Gson();
                array=gson.fromJson( response.result.toString(),RegisterArray.class);

                switch (array.data.result){
                    case 0: //正常注册
                        Toast.makeText(activity,"正常注册",Toast.LENGTH_SHORT).show();
                        activity.replaceFragment(2); //切换碎片方法 切换到登陆界面
                        break;
                    case -1://服务器不允许注册(用户数量已满)
                        Toast.makeText(activity,"服务器不允许注册(用户数量已满)",Toast.LENGTH_SHORT).show();
                        break;
                    case -2: //用户名重复
                        Toast.makeText(activity,"用户名重复",Toast.LENGTH_SHORT).show();
                        return;
                    case -3: //邮箱重复
                        Toast.makeText(activity,"邮箱重复",Toast.LENGTH_SHORT).show();
                        return;
                }
            }
            @Override
            public void failed(Response response) {//失败
                Toast.makeText(activity,"注册失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
