package feicui.edu.everydaynews.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.entity.UserLoginLog;

/**
 * 登陆日志适配器
 * Created by Administrator on 2016/10/19.
 */
public class LoginLogAdapter extends MyBaseAdapter<UserLoginLog> {
    public LoginLogAdapter(Context mContext, ArrayList mList, int mLayoutId) {
        super(mContext, mList, mLayoutId);
    }

    @Override
    public void putView(Holder holder, View convertView, int position, UserLoginLog userLoginLog) {
        TextView mTvTime= (TextView) convertView.findViewById(R.id.tv_login_log_time);  //登录时间
        TextView mTvAddress= (TextView) convertView.findViewById(R.id.tv_login_log_address);//登录地址
        TextView mTvDevice= (TextView) convertView.findViewById(R.id.tv_login_log_device);//登录装置
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //时间格式
        Date date=new Date(); //初始化日期
        mTvTime.setText(format.format(date)); //设置登录时间
        mTvAddress.setText(userLoginLog.address);
        if (userLoginLog.device==0){ //0  移动端
            mTvDevice.setText("移动端登录");
        }else { // 1   PC端
            mTvDevice.setText("PC端登录");
        }

    }
}
