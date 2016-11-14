package feicui.edu.everydaynews.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.activity.HomeActivity;
import feicui.edu.everydaynews.entity.ForgetPasswordArray;
import feicui.edu.everydaynews.net.MyHttp;
import feicui.edu.everydaynews.net.OnResultFinishListener;
import feicui.edu.everydaynews.net.Response;
import feicui.edu.everydaynews.view.RegixInput;
import feicui.edu.everydaynews.view.ServerURL;

/**
 * 找回密码碎片
 * Created by Administrator on 2016/10/13.
 */
public class PasswordFragment extends Fragment implements View.OnClickListener {
    EditText mEtMailBox;  //邮箱
    String mailBox;  //接收的邮箱
    Button mBtnOK; //确定按钮
    View mView;
    ForgetPasswordArray array;
    HomeActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView=getView();
        activity= (HomeActivity) getActivity();
        mEtMailBox= (EditText) mView.findViewById(R.id.et_login_forget_password_mailbox);
        mBtnOK= (Button) mView.findViewById(R.id.btn_login_forget_password_OK);
        mBtnOK.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_forget_password_OK: //确定按钮
                mailBox=mEtMailBox.getText().toString();
                Log.e("aaa","mailBox---------"+mailBox);

                if (mailBox.equals("")){
                    Toast.makeText(activity,"邮箱不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }else if (RegixInput.emailLength(mailBox)||RegixInput.emailAdressFormat(mailBox)){
                    getHttpData();
                }

                break;
        }
    }

    /**
     * forgetpass?ver=版本号&email=邮箱
     */
    public void getHttpData(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0");
        params.put("email",""+mailBox);

        MyHttp.get(activity, ServerURL.USER_FORGETPASS, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson=new Gson();
                array=gson.fromJson(response.toString(),ForgetPasswordArray.class);
                switch (array.data.result){
                    case 0: //发送邮箱成功
                      if( array.data.result==0) {
                          Toast.makeText(activity,"发送邮箱成功",Toast.LENGTH_SHORT).show();
                          activity.replaceFragment(2);
                      }
                        break;

                    case -1://发送失败（该邮箱未注册）
                        Toast.makeText(activity,"发送失败（该邮箱未注册）",Toast.LENGTH_SHORT).show();
                        break;

                    case -2: //发送失败（邮箱不存在或被封号）
                        Toast.makeText(activity,"发送失败（邮箱不存在或被封号）",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(activity,"密码找回失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
