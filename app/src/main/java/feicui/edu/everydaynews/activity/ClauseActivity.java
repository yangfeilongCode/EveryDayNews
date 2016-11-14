package feicui.edu.everydaynews.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import feicui.edu.everydaynews.R;

/**
 * 条款界面
 * Created by Administrator on 2016/10/10.
 */
public class ClauseActivity extends BaseActivity implements View.OnClickListener {
    WebView mWvClause;  //条款
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clause);
    }

    @Override
    void initView() {

        mIvLeft.setImageResource(R.mipmap.back);
        mTvTitle.setText("条款");
        mTvComment.setVisibility(View.GONE);
        mIvRight.setVisibility(View.GONE);
        mIvLeft.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_base_left://返回
                finish();
                break;
        }
    }
}
