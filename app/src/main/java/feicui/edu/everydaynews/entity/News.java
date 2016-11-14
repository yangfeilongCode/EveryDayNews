package feicui.edu.everydaynews.entity;

/**
 * 新闻信息类
 * Created by Administrator on 2016/9/27.
 */
public class News {

     private String summary; //新闻摘要
    private String icon;  //新闻图片连接
    private String stamp; //新闻标志 （时间）
    private String title;  //新闻标题
    private int nid;  //新闻 id
    private String link; //新闻链接
    private int type;  //新闻类型


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

//    public int getTime() {
//        return time;
//    }
//
//    public void setTime(int time) {
//        this.time = time;
//    }
}
