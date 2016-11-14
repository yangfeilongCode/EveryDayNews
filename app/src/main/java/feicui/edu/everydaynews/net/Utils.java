package feicui.edu.everydaynews.net;

import java.util.Map;
import java.util.Set;

/**
 * 工具类
 * Created by Administrator on 2016/9/22.
 */
public class Utils {
    /**
     * @param params {"name":"zs","pwd":"123"}---JSON     参数
     * @param type  ?name=zs&&pwd=123
     * @return
     */
   public static String getUrl(Map<String,String> params, int type){
       StringBuffer buffer=new StringBuffer();  //初始化一个缓存字符串类
       if(params!=null&&params.size()!=0) {//参数不为空并且大小不为0
           if (type == Constants.GET) { //判断类型 （get 、post）
               buffer.append("?"); //拼接？
           }
           Set<String> keySet = params.keySet(); //键集合
           for (String key : keySet) { //遍历键集合
               buffer.append(key)//拼接
                       .append("=") //拼接“=”
                       .append(params.get(key)).append("&");
           }
           buffer.deleteCharAt(buffer.length() - 1);//删除指定数据  删除最后一个字符
       }
           return buffer.toString();//将结果转化为字符串
       }
}
