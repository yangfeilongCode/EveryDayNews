package feicui.edu.everydaynews.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import feicui.edu.everydaynews.R;
import feicui.edu.everydaynews.adapter.LoginLogAdapter;
import feicui.edu.everydaynews.entity.ImageUpArray;
import feicui.edu.everydaynews.entity.UserCenterArray;
import feicui.edu.everydaynews.net.Constants;
import feicui.edu.everydaynews.net.MyHttp;
import feicui.edu.everydaynews.net.OnResultFinishListener;
import feicui.edu.everydaynews.net.Response;
import feicui.edu.everydaynews.view.MySQLiteOpenHelper;
import feicui.edu.everydaynews.view.ServerURL;

/**
 * 用户详细界面
 * Created by Administrator on 2016/10/10.
 */
public class UserDetailActivity extends BaseActivity implements View.OnClickListener {
    ImageView mUserImag; //用户头像图片
    TextView mTvUserName; //用户名
    TextView mTvIntegral;//用户积分
    TextView mTvUserComment;//用户跟帖数
    ListView mLvLoginLog;//登陆日志展示容器ListView
    LinearLayout mLLDetail;  //总的根布局
    LinearLayout mLlTop;  //上面展示布局
    View mView;   //PopupWindow展示的视图
    PopupWindow popupWindow; //弹框--PopupWindow
    LinearLayout mLlCameraOne; //条目1  相机
    LinearLayout mLlCameraTwo; //条目2   图库
    ImageView mIvPhoto;  //拍照图片
    ImageView mIvPicture;    //图库图片
    Button mBtnQuit;  //退出登录按钮
    LoginLogAdapter adapter; //登陆日志适配器

    UserCenterArray array;//用户中心集合
    ImageUpArray mImagUp; //头像上传集合

    SQLiteDatabase database; //SQL数据库

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        MySQLiteOpenHelper helper=new MySQLiteOpenHelper(this);//?????????
        database=helper.getWritableDatabase();
    }

    @Override
    void initView() {
       //更改头条目布局
       mIvLeft.setImageResource(R.mipmap.back); //左边返回图像
        mIvLeft.setOnClickListener(this); //设置点击事件
        mTvTitle.setText("用户详情"); //设置头条文字
        mTvComment.setVisibility(View.GONE); //隐藏跟帖
        mIvRight.setVisibility(View.GONE); //隐藏右边图像

        mView=getLayoutInflater().inflate(R.layout.view_popup,null); //布局填充
        //初始化弹框 并设置宽高
        popupWindow=new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLlTop= (LinearLayout) findViewById(R.id.ll_view_top); //上面展示头像及用户信息的布局
        mLLDetail= (LinearLayout) findViewById(R.id.ll_detail); //整个根布局
        mUserImag= (ImageView) findViewById(R.id.iv_detail_image);  //用户头像控件
        mUserImag.setOnClickListener(this); //用户头像设置点击事件
        mTvUserName= (TextView) findViewById(R.id.tv_user_home_name); //用户名
        mTvIntegral= (TextView) findViewById(R.id.tv_user_home_integral); //用户积分
        mTvUserComment= (TextView) findViewById(R.id.tv_user_detail_comment);//用户跟帖数

        mLlCameraOne= (LinearLayout) mView.findViewById(R.id.ll_item_popup_one); //拍照控件
        mLlCameraTwo= (LinearLayout) mView.findViewById(R.id.ll_item_popup_two);  //相册控件
        mLlCameraOne.setOnClickListener(this);
        mLlCameraTwo.setOnClickListener(this);//点击事件
        mIvPhoto= (ImageView) findViewById(R.id.iv_photo);//拍照图片
        mIvPicture= (ImageView) findViewById(R.id.iv_picture);  //图库图片
        mLvLoginLog= (ListView) findViewById(R.id.lv_user_log); //展示登陆日志容器ListView
        adapter=new LoginLogAdapter(this,null,R.layout.item_loginlog); //初始化登录日志适配器
        mLvLoginLog.setAdapter(adapter); //绑定适配器
        mBtnQuit= (Button) findViewById(R.id.btn_user_quit); //退出登录按钮
        mBtnQuit.setOnClickListener(this);
        getUserHome();  //调用用户信息接口
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_detail_image:  //用户头像
                popupWindow.setOutsideTouchable(true); //设置弹框外部可点击
                popupWindow.setBackgroundDrawable(new BitmapDrawable());  //弹框外部被点击 弹框隐藏
                //展示弹框
                popupWindow.showAtLocation(mLlTop, Gravity.BOTTOM,0,0);  //弹框所展示的相对（头布局的）位置  坐标（0，0）
                 break;

            case R.id.ll_item_popup_one: //拍照
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {  //   M API32
                    //Manifest--------》android 包里的 不是 jar包
                    // checkCallingOrSelfPermission----检查调用或自我许可
                    if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED&& //请求相机权限
                        checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) { //请求创建文件夹权限
                       goToCamera();  //调用跳转相机方法
                    }else {  //没有申请   创建一个请求许可
                        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},Constants.PERMISSION_CAMERA);
                    }
                    }else { //版本<6.0 直接调用
                    goToCamera(); //跳转相机
                }
                break;

            case R.id.ll_item_popup_two: //相册
                //隐式意图
                Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,Constants.GOTO_PICK);  //跳转图库

                break;
            case R.id.iv_base_left: //返回
                Intent intent2=new Intent(this,HomeActivity.class);  //跳转到主界面
                startActivity(intent2);
                finish(); //结束用户详情界面
                break;

            case R.id.btn_user_quit: //退出登录按钮  需要清空数据库
//-----------------清空数据库-----------------------------------------------------------------------
                String sqlDelete="delete from news where null";
                database.execSQL(sqlDelete); //执行
                database.delete("news",null,null); //清理

                Intent intent1=new Intent(this,HomeActivity.class);  //跳转到主界面
                startActivity(intent1);
                finish();
                break;
        }
    }

    /**
     *  跳转系统相机
     */
    public void goToCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //隐式跳转   需要权限
        //传递此次拍照图片存储路径
        //指定一个路径去存储数据   手机的SD卡路径
        File fileDir=new File(Constants.DIR_PATH); //通过存储路径创建文件目录(夹)
        if (!fileDir.exists()){ //文件不存在
            fileDir.mkdirs();  //创建文件夹
        }
        //给相机传递文件路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.PHOTO_PATH)));
        startActivityForResult(intent,Constants.GOTO_CAMERA); //根据请求码跳转(跳转相机)
    }

    /**
     * 返回结果码
     * @param requestCode  请求码
     * @param resultCode    结果码
     * @param data   数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.GOTO_CAMERA:  //跳转相机
                if (requestCode==RESULT_OK){ //请求成功
                  //读取图片   路径
                  Bitmap bitmap= BitmapFactory.decodeFile(Constants.PHOTO_PATH); //解析文件路径
//                    mIvPhoto.setImageBitmap(bitmap); //通过路径获取资源  设置照片
//                    File file=new File(PHOTO_PATH);
//                    Glide.with(this).load(bitmap).into(mIvPhoto);//设置图片
                    Glide.with(this).load(bitmap).into(mUserImag);//设置用户头像

                }
                break;

            case Constants.GOTO_PICK:  //跳转图库
                if (requestCode==RESULT_OK){   //请求成功
                    //获取图片数据
                    Uri uri=data.getData(); //方法参数的 data----resultCode
                    //文件列路径
                    String[] filePathColumn={MediaStore.Images.Media.DATA};  //MediaStore--媒体库
                    //游标   接收内容 解析 查询 结果
                    Cursor curs=getContentResolver().query(uri,filePathColumn,null,null,null);
                    curs.moveToFirst();//游标移至第一行
                    //通过游标获得对应列的下标
                    int columnIndex=curs.getColumnIndex(filePathColumn[0]);
                    String path=curs.getString(columnIndex); //通过游标获取对应列的路径
                    Log.e("aaa","onActivityResult++++++++"+path);
                    try {
                        //位置工厂 初始化一个文件输入流 进行译码
                      Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(path));
                        mIvPicture.setImageBitmap(bitmap);  //设置对应图片
                        mUserImag.setImageBitmap(bitmap);  //设置设置用户头像
//                        Glide.with(this).load(bitmap).into(mUserImag);

                    } catch (FileNotFoundException e) { //文件路径异常处理
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 请求许可结果
     * @param requestCode   请求码
     * @param permissions   请求权限
     * @param grantResults  请求权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){ //请求码
            case Constants.PERMISSION_CAMERA://相机请求
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED &&   //同意相机权限
                        grantResults[1]==PackageManager.PERMISSION_GRANTED){  //同意创建文件夹权限
                goToCamera();//跳转相机
                }else { //请求未成功
                    Toast.makeText(this,"打开相机需要权限——>设置",Toast.LENGTH_SHORT).show();
                }
        }
    }

    String token=Constants.TOKEN;//从常量池中获得用户令牌
    /**
     * 用户中心
     * user_home?ver=版本号&imei=手机标识符&token =用户令牌
     */
    public void getUserHome(){
        Log.e("aaa", "getUserHome--token----"+token );
        Map<String,String> params=new HashMap<>();
        params.put("ver","0000000");
        params.put("imei",""+000000000000000);
        params.put("token",""+token);
        MyHttp.get(this, ServerURL.USER_HOME, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson=new Gson();
                array=gson.fromJson(response.result.toString(),UserCenterArray.class);
                if (array.status==0){
                    //设置用户头像
                    Glide.with(UserDetailActivity.this).load(array.data.portrait).into(mUserImag);
                    mTvUserName.setText(array.data.uid); //设置用户名
                    mTvIntegral.setText("积分："+array.data.integration); //设置积分
                    mTvUserComment.setText("跟帖数统计："+array.data.comnum); //设置跟帖数
                  //登陆日志是否有数据
                   if (array.data.loginlog!=null&array.data.loginlog.size()>0){
                       adapter.mList=array.data.loginlog; //添加数据
                       adapter.notifyDataSetChanged(); //刷新适配器
                   }else {
                       Toast.makeText(UserDetailActivity.this,"数据加载失败",Toast.LENGTH_SHORT).show();
                   }

                }else {
                    Toast.makeText(UserDetailActivity.this,"失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(UserDetailActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * 上传头像
     * user_image?token=用户令牌& portrait =头像
     */
    public void getImageUp(){
        Map<String,String> params=new HashMap<>();
        params.put("token",""+token);
        params.put("portrait",""+mIvPhoto);
        MyHttp.get(this, ServerURL.UNEWS_IMAGE, params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {

                Gson gson=new Gson();
                mImagUp=gson.fromJson(response.result.toString(),ImageUpArray.class);

                if (mImagUp.status==0){// 0:上传成功  -1:上传失败
                Toast.makeText(UserDetailActivity.this,"头像上传成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(UserDetailActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    String PREFERENCE_NAME="prefrence_settings";   //
//    public boolean getLoginlog(){
//        preferences=getSharedPreferences(PREFERENCE_NAME,MODE_PRIVATE);
//        boolean isLogin=preferences.getBoolean("is_login",true);
//
//        return isLogin;
//    }
}
