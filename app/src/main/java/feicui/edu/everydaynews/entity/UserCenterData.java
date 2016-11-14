package feicui.edu.everydaynews.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/12.
 */
public class UserCenterData {
    public String uid; //用户名
    public String portrait; //用户图标
    public int integration; //用户积分票总数
    public int comnum; //评论总数
    public ArrayList<UserLoginLog> loginlog; //登录记录
}
