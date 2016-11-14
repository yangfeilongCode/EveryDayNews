package feicui.edu.everydaynews.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 *  网络异步任务类
 * Created by Administrator on 2016/9/22.
 */
public class NetAsyns extends AsyncTask<Request,Object,Response>{ //继承异步任务（线程）
    ProgressDialog mDialog;  //加载圈（进度条）
    OnResultFinishListener mListener;  //完成结果监听器
    public NetAsyns(Context context,OnResultFinishListener mListener){ //构造方法
        mDialog=ProgressDialog.show(context,"","加载中....."); //展示加载圈
        this.mListener=mListener; //结果监听
    }

    /**
     * 重写响应方法
     * @param params  参数
     * @return
     */
    @Override
    protected Response doInBackground(Request... params) {
        Request request=params[0]; //请求 ？？？？？？
        Response response=new Response(); //初始化响应

        HttpURLConnection httpURLConnection=null;
        try {
            URL url=new URL(request.url);//(全球资源定位器)
            httpURLConnection= (HttpURLConnection) url.openConnection(); //开启连接
            httpURLConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT); //设置连接时间
            httpURLConnection.setReadTimeout(Constants.READ_TIMEOUT); //设置请求时间
            if (request.tyle==Constants.GET){
                httpURLConnection.setRequestMethod("GET");  //设置请求方法  get（） 可省略
            }else{
                httpURLConnection.setRequestMethod("POST");//设置请求方法  post（）
                httpURLConnection.setDoOutput(true);
                OutputStream out=httpURLConnection.getOutputStream(); //获得一个输出流
                out.write(Utils.getUrl(request.params,Constants.POST).getBytes()); //写数据
            }

            int code=httpURLConnection.getResponseCode(); //返回结果码
            Log.e("-----","code----"+code);
            response.code=code; //相应结果
            if (code==HttpURLConnection.HTTP_OK){ //请求成功
                InputStream in=httpURLConnection.getInputStream(); //获得一个输入流
                byte[] bytes=new byte[1024];
                int len; //字节长度
                StringBuffer buffer=new StringBuffer(); //初始化一个缓冲字符串类
                while ((len=in.read(bytes))!=-1){ //判断是否读读完
                    buffer.append(new String(bytes,0,len));
                }
                //拿到结果
                response.result=buffer.toString();
                Log.e("-----","response----"+response.result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection!=null){
                httpURLConnection.disconnect();  //断开网络连接
            }
        }
        return response;  //返回结果

    }
    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        //加载圈拿到结果
        mDialog.dismiss();
        Response response1 = response; //响应结果
        if (response1.code != HttpURLConnection.HTTP_OK) {//请求失败
            mListener.failed(response1);//请求结果失败
        } else {  //请求成功
            mListener.success(response1); //成功
        }
    }
}
