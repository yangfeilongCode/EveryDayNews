package feicui.edu.everydaynews.net;

import android.content.Context;
import android.util.Log;

import java.util.Map;

/**
 * 网络协议
 * Created by Administrator on 2016/9/22.
 */
public class MyHttp {
    public static void get(Context context, String url, Map<String,String> params, OnResultFinishListener mListener){
        //进行网络请求
        Request request=new Request(); //初始化一个请求
        request.params=params;  //请求参数
        request.tyle=Constants.GET;  //请求类型  （GET / POST）
        request.url=url+Utils.getUrl(params,Constants.GET);  //请求路径
        Log.e("-----","url-----"+ request.url);
        NetAsyns asyns=new NetAsyns(context,mListener); //初始化一个网络异步任务类
        asyns.execute(request); //执行异步类
    }
    public static void post(Context context,String url,Map<String,String> params,OnResultFinishListener mListener){
        Request request=new Request(); //初始化请求
        request.params=params; //请求参数
        request.tyle=Constants.POST; //请求类型
        request.url=url; //请求路径
        NetAsyns asyns=new NetAsyns(context,mListener);//初始化一个网络异步任务类
        asyns.execute(request); //执行

    }
}
