package feicui.edu.everydaynews.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入模式
 * Created by Administrator on 2016/10/3.
 */
public class RegixInput {
    /**
     *  邮箱长度的判断   6-------31
     * @param emailAdress    传入的邮箱
     * @return
     */
    public static boolean emailLength(String emailAdress) {
        if (emailAdress.length() < 31&&emailAdress.length()>=6) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param emailAdress   传入的邮箱
     * @return
     */
    public static boolean emailAdressFormat(String emailAdress) {//邮箱格式的正则
       // Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//正则格式
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+[-\\.]?)*[a-zA-Z0-9]@([a-zA-Z0-9]+(-[a-zA-Z0-9]+)?\\.)+[a-zA-Z]{2,}$");//正则格式
        Matcher mc = pattern.matcher(emailAdress);//进行匹配
        return mc.matches();//输出返回值
    }

    /**
     *   密码长度的判断
     * @param psw  传入的密码
     * @return
     */
    public static boolean passWordLenth(String psw) {
        if (psw.length() >=6 && psw.length() <= 16) {
            return true;
        }
        return false;
    }

    public static boolean IdNameLength(String idName) {//账号长度的判断
        if (idName.length() >= 3 && idName.length() <= 15) {
            return true;
        }
        return false;
    }

    public static boolean judgeEmpty(String inputString) {//三个公用的  判断是否为空
        if (inputString != null && inputString.length() > 0) {
            return true;
        }
        return false;
    }
}
