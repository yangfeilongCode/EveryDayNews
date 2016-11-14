package feicui.edu.everydaynews.view;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import feicui.edu.everydaynews.net.Constants;

/**
 * 数据库管理
 * Created by Administrator on 2016/10/24.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context) {
        super(context, Constants.DB_NAME,null,Constants.DB_VERSION);
    }

    /**
     * 创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlNews="create table news (uid text,password text,icon uri)";
        db.execSQL(sqlNews); //提交sql语句
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
