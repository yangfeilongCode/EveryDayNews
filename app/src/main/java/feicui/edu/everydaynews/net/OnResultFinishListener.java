package feicui.edu.everydaynews.net;

/**
 * 接口   请求完成结果
 * Created by Administrator on 2016/9/22.
 */
public interface  OnResultFinishListener {
    void success(Response response);   //请求成功
    void failed(Response response);   //请求失败

}
