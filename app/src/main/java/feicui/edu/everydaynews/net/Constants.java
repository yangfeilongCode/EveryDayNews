package feicui.edu.everydaynews.net;

import android.os.Environment;

import java.io.File;

/**
 * 静态常量
 * Created by Administrator on 2016/9/22.
 */
public class Constants {
    public static final int POST=11; //post
    public static final int GET=10;
    public static final int CONNECT_TIMEOUT=5000; //连接时间
    public static final int READ_TIMEOUT=5000;  //读取时间

    /**
     * 照片存储文件夹路径（用户中心）
     */
    //获得外部存储的绝对路径  File.separator  分隔符
    public static final String DIR_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"EveryDayNews";
    public static final String PHOTO_PATH =DIR_PATH + File.separator+"phpto.jpg";  //照片存储路径
    public static final int PERMISSION_CAMERA=200;  //相机请求常量
    public static final int GOTO_CAMERA=201;  //跳转相机请求码
    public static final int GOTO_PICK=202;  //跳转图库请求码

    public static String TOKEN;//token  用户令牌
//    public static boolean IS_LOGIN;//登录管理,是否登录

    public static String DB_NAME="news_db"; // 数据库名
    public static int DB_VERSION=1;//数据库版本

    public static String USER_NAME; //用户名
    public static String PASSWORD; //用户密码

    //进入服务器地址码
   public static final String SERVER_LOGIN="http://192.168.199.239:8080/TestWeb/abc";
  //  public static final String SERVER_LOGIN="http://118.244.212.82:9092/newsClient";
}
