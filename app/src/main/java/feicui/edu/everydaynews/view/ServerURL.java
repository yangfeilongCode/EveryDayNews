package feicui.edu.everydaynews.view;

/**
 * 接口
 * Created by Administrator on 2016/9/28.
 */
public class ServerURL {
   public static final String BASE_URL="http://118.244.212.82:9092/newsClient";
  //用户登录
   public static final String USER_LOGIN=BASE_URL+"/user_login";
    //新闻列表
   public static final String NEWS_LIST=BASE_URL+"/news_list";
    //新闻分类
   public static final String NEWS_SORT=BASE_URL+"/news_sort";

    //新闻内容
    public static final String UNEWS_IMAGE=BASE_URL+"/news_image";
    //发布评论
    public static final String CMT_COMMIT=BASE_URL+"/cmt_commit";
    //显示评论
    public static final String CMT_LIST=BASE_URL+"/cmt_list";

    //评论数量
    public static final String CMT_NUM=BASE_URL+"/cmt_num";
    //用户中心
    public static final String USER_HOME=BASE_URL+"/user_home";
    //头像上传
    public static final String USER_IMAGE=BASE_URL+"/user_image";

    //用户注册
    public static final String USER_REGISTER=BASE_URL+"/user_register";
    //找回密码
    public static final String USER_FORGETPASS=BASE_URL+"/user_forgetpass";
    //版本升级
    public static final String UPDATE=BASE_URL+"/update";

}
