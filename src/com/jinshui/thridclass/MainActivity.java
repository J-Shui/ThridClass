package com.jinshui.thridclass;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import com.jinshui.thridclass.utils.MyLog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

public class MainActivity extends Activity implements PlatformActionListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //必须先要进行初始化
        ShareSDK.initSDK(this);
    }

    /**
     * 一键分享
     * @param view
     */
    public void btnShareClick(View view) {
        showShare();
    }

    /**
     * 第三方登录
     * @param view
     */
    public void btnAuth(View view) {
        Platform platform = ShareSDK.getPlatform(this,SinaWeibo.NAME);

        //进行用户的授权，授权的时候需要获取用户ID
        platform.setPlatformActionListener(this);

        platform.authorize();
    }

    /**
     * 获取用户资料
     * @param view
     */
    public void btnGetUserInfo(View view) {
        //获取指定的平台，通过字符串获取
        Platform platform = ShareSDK.getPlatform(this,SinaWeibo.NAME);

        //设置当获取用户分享完成时回调接口
        platform.setPlatformActionListener(this);

        //获取指定平台下，指定账号的信息
        //account为null时，代表获取当前授权用户
        platform.showUser(null);

    }
    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        //设置是否显示内容编辑框，true表示隐藏编辑框
        oks.setSilent(true);

        //设置分享接口回调
        oks.setCallback(this);

        //设置只是用一个平台进行分享
        oks.setPlatform(SinaWeibo.NAME);

        // 启动分享GUI
        oks.show(this);
    }

    /**
     * 分享成功
     * @param platform 回调的平台
     * @param i 操作的类型，这个整数代表当前会动的操作，是分享还是获取用户信息
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //TODO 处理成功的情况

        if(i == Platform.ACTION_SHARE){
            String pName = platform.getName();

            MyLog.d("Share","Platform name = "+pName);

            Toast.makeText(this, "分享成功：" + pName, Toast.LENGTH_LONG).show();
        }else if (i == Platform.ACTION_USER_INFOR){
            PlatformDb db = platform.getDb();

            String userName = db.getUserName();
            String userId = db.getUserId();
            String userIcon = db.getUserIcon();
            String token = db.getToken();

            MyLog.d("Share", "UserName = " + userName);
            MyLog.d("Share","UserId = "+userId);
            MyLog.d("Share","UserToken = "+token);
            MyLog.d("Share","UserIcon = "+userIcon);
        }else if(i == Platform.ACTION_AUTHORIZING){
            //获取用户ID方式的第三方登录
            PlatformDb db = platform.getDb();

            db.getUserId();
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
