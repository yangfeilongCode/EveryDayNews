package feicui.edu.everydaynews.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/9.
 */
public class LoginArray {
    public String message; //消息
    public int status;  //状态
    public LoginData data=new LoginData(); //新闻集合

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginArray{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
