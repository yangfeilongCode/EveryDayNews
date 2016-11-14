package feicui.edu.everydaynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
/**
 *   我的基类适配器
 * Created by Administrator on 2016/9/27.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    Context mContext;  //上下文
    public  ArrayList<T> mList; //数据源
    int mLayoutId;//    Id
    LayoutInflater mInflater;  //布局填充器

    public MyBaseAdapter(Context mContext,ArrayList<T> mList, int mLayoutId){
        this.mContext=mContext;
        this.mList=mList;
        this.mLayoutId=mLayoutId;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        /**
         * 
         */
        if (this.mList==null){
            this.mList=new ArrayList<>(); //初始化集合
        }
    }
    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView==null){
            convertView= mInflater.inflate(mLayoutId,null); //布局填充
            holder=new Holder();
            convertView.setTag(holder); //设置标签

        }else {
            holder= (Holder) convertView.getTag(); //获得标签
        }
        /**
         * 将条目View的渲染具体过程交给子类去做
         * 因为只有子类知道具体的条目View或者控件
         */
        //调渲染方法
        putView(holder, convertView, position,mList.get(position)); //抽象方法  子类重写
        return convertView;
    }

    /**
     *  子条目渲染抽象方法
     * @param holder  对应条目的Holder
     * @param convertView  对应条目的View
     * @param position  对应条目的位置
     * @param t   对应条目的数据
     */
    public abstract void putView(Holder holder,View convertView,int position,T t);

    class Holder{

    }

}
