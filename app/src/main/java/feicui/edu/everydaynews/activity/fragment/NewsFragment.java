package feicui.edu.everydaynews.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.activity.HomeActivity;
import feicui.edu.everydaynews.activity.NewsDetailActivity;
import feicui.edu.everydaynews.adapter.NewsListAdapter;
import feicui.edu.everydaynews.entity.News;
import feicui.edu.everydaynews.entity.NewsArray;
import feicui.edu.everydaynews.entity.NewsSortArray;
import feicui.edu.everydaynews.net.MyHttp;
import feicui.edu.everydaynews.net.OnResultFinishListener;
import feicui.edu.everydaynews.net.Response;
import feicui.edu.everydaynews.view.ServerURL;
import feicui.edu.everydaynews.view.xlist.XListView;

/**
 * 新闻列表碎片
 * Created by Administrator on 2016/9/29.
 */
public class NewsFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {

    XListView mListNews;  //内容展示布局
    View mView; //定义一个Viewe
    HomeActivity activity; //定义一个碎片Activity
    NewsListAdapter mAdapter; //新闻列表适配器
    NewsArray array;
    /**
     * 是否刷新
     *  1  刷新    2  不刷新
     */
    int dir=1;
    /**
     * 最后条的id 用于上拉加载
     */
    int nid=1;
    //时间格式
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list,container,false);//??
    }
    public View  findViewById(int id){

        return mView.findViewById(id);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView=getView();  //获得一个View
        activity= (HomeActivity) getActivity();  //获得一个Activity
        mListNews= (XListView) findViewById(R.id.lv_new_fragment); //找到对象布局
        mAdapter=new NewsListAdapter(activity,null,R.layout.item_news);//初始化适配器
        mListNews.setAdapter(mAdapter); //绑定适配器
        //可以进行上拉加载
        mListNews.setPullLoadEnable(true);
        //可以进行下拉刷新
        mListNews.setPullRefreshEnable(true);
        //绑定的自定义接口
        mListNews.setXListViewListener(this);
     //   getHttp(); //调用下面方法
        //绑定的listView点击事件
        mListNews.setOnItemClickListener(this);
        getHttpData();
    }


    /**
     * 获取网络数据
     *  ver=0000000&subid=1&dir=1&nid=1&stamp=20140321000000&cnt20
     */
    public void getHttpData(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000"); //版本
        params.put("subid","1"); //标识
        params.put("dir",""+dir);
        params.put("nid",""+nid);
        params.put("stamp","20140321000000");
        params.put("cnt","20");
        MyHttp.get(activity, ServerURL.NEWS_LIST, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {  //成功
                Log.e("aaa","response.toString()-------News---------"+response.result);

                Gson gson=new Gson();
                array=gson.fromJson( response.result.toString(),NewsArray.class);
                /**
                 * 有数据  进行添加并刷新
                 */
                if (array.data!=null&&array.data.size()>0){
                    mAdapter.mList.addAll(array.data);
                    mAdapter.notifyDataSetChanged();
                }
                if (dir==1){ //刷新
                    Date date=new Date();
                    mListNews.setRefreshTime(format.format(date)); //刷新时间
                }
                //关闭上拉加载以及下拉刷新框
                mListNews.stopRefresh();
                mListNews.stopLoadMore();
            }

            @Override
            public void failed(Response response) {//失败
                Toast.makeText(activity,"加载失败",Toast.LENGTH_SHORT).show();
                //关闭上拉加载以及下拉刷新框（失败也要加载）
                mListNews.stopRefresh();
                mListNews.stopLoadMore();
            }
        });
    }


    @Override
    public void onRefresh() {  //更新
        //进行下拉刷新
        dir=1;
        //清空之前
        mAdapter.mList.clear();
        getHttpData();
    }

    @Override
    public void onLoadMore() { //加载
        //进行上拉加载
        dir=2;
        //拿到最后一条id
        if (mAdapter.mList.size()>0){
            News news=mAdapter.mList.get(mAdapter.mList.size()-1);
            nid=news.getNid(); //获取最后一列的id
        }
        getHttpData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News news=mAdapter.mList.get(position);
        Intent intent=new Intent(activity, NewsDetailActivity.class); //跳转新闻加载页面

        intent.putExtra("link",news.getLink());
        intent.putExtra("nid",news.getNid()+"");
        startActivity(intent);
    }

    /**
     * 新闻分类
     * news_sort?ver=版本号&imei=手机标识符
     */
    public void getNewsSort(){
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000");
        params.put("imei",""+000000000000000);
        MyHttp.get(activity, ServerURL.NEWS_SORT, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson=new Gson();
                NewsSortArray sortArray=gson.fromJson(response.result.toString(),NewsSortArray.class);
                if (sortArray.status==0){

                }
            }

            @Override
            public void failed(Response response) {

            }
        });
    }
}
/**
  *   XListView 步骤使用
  *      1.导入xListView 所需要的代码
  *      2.布局中使用xListView替代列表
  *      3.代码中进行设置
 *           设置可以进行上拉加载  mListView.setPulloadEnable(true);
 *           设置可以进行下拉刷新  mListView.setpullRefreshEnable(true);
  *      4.绑定  加载  刷新  事件监听
  *        mListNews.setXListViewListener(this);
  *  注意：刷新或者加载结束之后，需要停止刷新以及加载
  *       mListNews.setopRefresh();
  *       mListNews.stopLoadMore();
  */
