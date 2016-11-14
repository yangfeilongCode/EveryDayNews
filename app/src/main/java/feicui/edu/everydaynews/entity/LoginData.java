package feicui.edu.everydaynews.entity;

/**
 * Created by Administrator on 2016/10/9.
 */
public class LoginData {
    public int result;  //登录状态
    public String explain;  //登陆成功
    public String token;  //用户令牌

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "result=" + result +
                ", explain='" + explain + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
