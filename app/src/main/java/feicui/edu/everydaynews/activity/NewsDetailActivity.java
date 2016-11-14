package feicui.edu.everydaynews.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.entity.CommentNumber;
import feicui.edu.everydaynews.net.MyHttp;
import feicui.edu.everydaynews.net.OnResultFinishListener;
import feicui.edu.everydaynews.net.Response;
import feicui.edu.everydaynews.view.ServerURL;

/**
 * 新闻详情
 * Created by Administrator on 2016/9/29.
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    View view; //接收popupWindow弹框 View
    PopupWindow popupWindow; //弹框
    TextView mTvPop; //收藏弹框文字
    WebView mWebView; //web视图  展示新闻详情
//    NewsBigArray array; //大图新闻集合
    CommentNumber num; //评论数量
    String uri; //接收新闻链接
    String nid;  //接收新闻编号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        getHttpData();
    }

    @Override
    void initView() {
        mIvLeft.setImageResource(R.mipmap.back); //返回图标
        mIvLeft.setOnClickListener(this);
        mTvTitle.setText("资讯"); //设值头条目文字
        mTvComment.setOnClickListener(this);
        mIvRight.setImageResource(R.mipmap.news_menu); //收藏图标
        mIvRight.setOnClickListener(this);

        view=getLayoutInflater().inflate(R.layout.favorite_popup,null); //布局填充
        //初始化一个弹框  设置宽高  自适应
        popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mTvPop= (TextView)view.findViewById(R.id.tv_pop_favorite); //收藏文字
        mTvPop.setOnClickListener(this);
        //从新闻列表接收数据
        Intent intent=getIntent();
        nid=intent.getStringExtra("nid");  //新闻编号
        uri=intent.getStringExtra("link");  //新闻链接

        mWebView= (WebView) findViewById(R.id.web_news_detail);
        //   1.加载链接
        mWebView.loadUrl(uri);
//--------------------------------------------------------------------------------------------------
//        //2.加载本地文件  HTML gift
//        mWebView.loadUrl("file://*******************.jpg.gif");
//        3.加载文本存在问题
//        mWebView.loadData("<html><内容></html>","aaa/html","uft-8");
//        mWebView.loadDataWithBaseURL(null,"<html>\n" + "   <body>我是男孩</body>\n" + "  </html>","aaa/html","uft-8",null);
//        使用当前应用作为 web视图的展示界面 返回值true 并且 重新加载
//--------------------------------------------------------------------------------------------------
        mWebView.setWebViewClient(new WebViewClient(){ //设置网页视图客户端
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //应该重写Url加载
                mWebView.loadUrl(url);
                return super.shouldOverrideUrlLoading(view,url);
            }
        });

        //添加设置
        WebSettings settings=mWebView.getSettings();
        //支持JavaScript写的网页
        settings.setJavaScriptEnabled(true);
        //设置使用宽视图端口
        settings.setUseWideViewPort(true);
        //可以按照任意比例缩放
        settings.setLoadWithOverviewMode(true);
        //支持缩放功能
        settings.setSupportZoom(true);
        //显示缩放视图
        settings.setBuiltInZoomControls(true);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //1.返回键  2.可以返回
        if (keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_base_comment: //评论
                Intent intent=new Intent(this,CommentActivity.class);
                intent.putExtra("nid",nid); //向评论界面传递新闻编号数据
                startActivity(intent);

                break;
            case R.id.iv_base_left://返回
                finish();

                break;
            case R.id.iv_base_right://收藏
                popupWindow.setOutsideTouchable(true); //设置弹框外部可点击
                popupWindow.setBackgroundDrawable(new BitmapDrawable()); //设置弹框可隐藏
             //   popupWindow.showAtLocation(mIvRight, Gravity.TOP,0,0);//弹框所展示的相对（头布局的）位置  坐标（0，0）
                popupWindow.showAsDropDown(mIvRight,0,0);

                break;
            case R.id.tv_pop_favorite://弹框收藏
                Toast.makeText(NewsDetailActivity.this,"目前还不能收藏",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 跟帖数量
     * cmt_num?ver=版本号& nid=新闻编号
     */
    public void getHttpData(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000");
        params.put("nid",""+nid);
        Log.e("aaa","nid-------"+nid);
        MyHttp.get(this, ServerURL.CMT_NUM, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson=new Gson();
                num=gson.fromJson(response.result.toString(),CommentNumber.class);
                Log.e("aaa","num------"+num);
                if (num.status==0) {
                    mTvComment.setText(num.data+"跟帖" );

                }else {
                    Toast.makeText(NewsDetailActivity.this,"跟帖加载失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(NewsDetailActivity.this,"跟帖失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
