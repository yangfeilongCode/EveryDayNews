package feicui.edu.everydaynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.adapter.CommentAdapter;
import feicui.edu.everydaynews.entity.CommentArray;
import feicui.edu.everydaynews.entity.CommentData;
import feicui.edu.everydaynews.entity.SendCommentInfo;
import feicui.edu.everydaynews.net.Constants;
import feicui.edu.everydaynews.net.MyHttp;
import feicui.edu.everydaynews.net.OnResultFinishListener;
import feicui.edu.everydaynews.net.Response;
import feicui.edu.everydaynews.view.ServerURL;
import feicui.edu.everydaynews.view.xlist.XListView;

/**
 * 评论界面
 * Created by Administrator on 2016/9/30.
 */
public class CommentActivity extends BaseActivity implements XListView.IXListViewListener, AdapterView.OnItemClickListener, View.OnClickListener {

    XListView mXLVContent;  //内容容器
    EditText mEtComment; //评论
    ImageView mIvSend; //发送评论图标
    CommentAdapter adapter;//评论适配器
    CommentArray array;  //评论实体
    SendCommentInfo info; //发送评论实体
    String mComment; //接受的评论内容
    String nid; //接收的新闻id（新闻详情界面传过来的）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    }

    @Override
    void initView() {
        mIvLeft.setImageResource(R.mipmap.back); //返回
        mIvLeft.setOnClickListener(this);
        mTvTitle.setText("评论");
        mTvComment.setVisibility(View.GONE);
        mIvRight.setVisibility(View.GONE);

        mXLVContent= (XListView) findViewById(R.id.xl_comment_content); //评论内容展示容器
        mEtComment= (EditText) findViewById(R.id.et_comment); //输入的评论
        mIvSend= (ImageView) findViewById(R.id.iv_comment_send);//发表评论图标
        mIvSend.setOnClickListener(this);
        adapter=new CommentAdapter(this,null,R.layout.item_comment);// 初始化评论适配器
        mXLVContent.setAdapter(adapter);//绑定适配器

        //接收数据 详情界面传值
        Intent intent=getIntent();
        nid=intent.getStringExtra("nid");

        mXLVContent.setPullLoadEnable(true);//可以进行上拉加载
        mXLVContent.setPullRefreshEnable(true);//可以进行下拉刷新
        mXLVContent.setXListViewListener(this);//绑定的自定义接口
        mXLVContent.setOnItemClickListener(this);//绑定的listView点击事件
        getHttpData();//评论接口
    }


    //评论子条目点击
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"666666666666",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_comment_send: //提交评论
                mComment=mEtComment.getText().toString(); //获取评论内容
                if (mComment.equals("")){ //是否为null
                    Toast.makeText(CommentActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }else { //不为null 调发表评论接口接口
                    getSendData();
                }

                break;
            case R.id.iv_base_left://返回上一界面
                finish();
                break;
        }
    }

    int cid=1; //最后一条评论Id
    int dir=1; //1.刷新   2.加载
    //日期格式
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     *  显示评论接口
     *  ?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
     */
    public void getHttpData(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000");
        params.put("nid",""+nid);
        params.put("type","1");
        params.put("stamp","20161018000000"); //时间格式
        params.put("cid",""+cid);
        params.put("dir",""+dir);
        params.put("cnt","20");  //最大更新条目

        MyHttp.get(this, ServerURL.CMT_LIST, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {

                Gson gson=new Gson();
                array=gson.fromJson(response.result.toString(),CommentArray.class);
                if (array.data!=null&&array.data.size()>0){ //是否有数据
                    adapter.mList=array.data; //数据加入集合
                    adapter.notifyDataSetChanged();//刷新适配器
                    if (dir==1) {//刷新
                        Date date=new Date(); //初始化日期
                        mXLVContent.setRefreshTime(format.format(date));//更新日期
                    }
                    Toast.makeText(CommentActivity.this,"加载评论成功",Toast.LENGTH_SHORT).show();
                    //关闭刷拉加载下拉关闭
                    mXLVContent.stopRefresh(); //关闭刷新
                    mXLVContent.stopLoadMore(); //关闭加载
                } else {
                    Toast.makeText(CommentActivity.this,"加载评论失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(CommentActivity.this,"请求加载评论失败",Toast.LENGTH_SHORT).show();
                //关闭刷拉加载下拉关闭  失败也要关闭
                mXLVContent.stopRefresh(); //关闭刷新
                mXLVContent.stopLoadMore(); //关闭加载
            }
        });

    }


    @Override
    public void onRefresh() { //刷新
        dir=1;
        adapter.mList.clear(); //清除之前内容
        getHttpData(); //再调一次评论接口
    }

    @Override
    public void onLoadMore() { //加载
        dir=2;
        if (adapter.mList!=null&&adapter.mList.size()>0){//集合是否用数据
            CommentData data=adapter.mList.get(adapter.mList.size()-1);
            cid=data.cid;//改变评论编号
        }
        getHttpData(); //显示评论接口

    }


    /**
     * 发布评论接口
     * cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
     */
   public void getSendData(){


       String token= Constants.TOKEN;  //用户令牌
              Log.e("aaa", "getSendData:--token-- "+token);

       Map<String,String> params=new HashMap<>();
       params.put("ver","0000000");
       params.put("nid",""+nid);
       params.put("token",""+token);
       params.put("imei",""+000000000000000);  //手机标示符
       params.put("ctx",""+mComment); //评论内容
       MyHttp.get(this, ServerURL.CMT_COMMIT, params, new OnResultFinishListener() {
           @Override
           public void success(Response response) {
               Gson gson=new Gson();
               info=gson.fromJson(response.result.toString(),SendCommentInfo.class);

               if (info.status==0){
                   Toast.makeText(CommentActivity.this, "评论已发送", Toast.LENGTH_SHORT).show();

                   adapter.mList.clear(); //清空评论集合
                   getHttpData();
                   mEtComment.setText(""); //清空评论框
                   adapter.notifyDataSetChanged();//刷新适配器

               }else {
                   Toast.makeText(CommentActivity.this, "评论未提交成功", Toast.LENGTH_SHORT).show();
               }

//               switch (info.status) {
//                   case 0: //评论已发送
//                       Toast.makeText(CommentActivity.this, "评论已发送", Toast.LENGTH_SHORT).show();
//                       Intent intent = new Intent(CommentActivity.this, CommentActivity.class);
//                       adapter.notifyDataSetChanged();//刷新适配器
//                       startActivity(intent);
//                       break;
//                   case -1://用户名或密码错误
//                       Toast.makeText(CommentActivity.this, "非法关键字", Toast.LENGTH_SHORT).show();
//                       return;
//                   case -2: //禁止评论(政治敏感新闻)
//                       Toast.makeText(CommentActivity.this, "禁止评论(政治敏感新闻)", Toast.LENGTH_SHORT).show();
//                       return;
//                   case -3://禁止评论(用户被禁言)
//                       Toast.makeText(CommentActivity.this, info.data.explain, Toast.LENGTH_SHORT).show();
//                       return;
//               }

           }

           @Override
           public void failed(Response response) {
               Toast.makeText(CommentActivity.this,"评论发送失败",Toast.LENGTH_SHORT).show();
           }
       });
   }
}
