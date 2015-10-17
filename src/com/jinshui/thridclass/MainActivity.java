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

        //������Ҫ���г�ʼ��
        ShareSDK.initSDK(this);
    }

    /**
     * һ������
     * @param view
     */
    public void btnShareClick(View view) {
        showShare();
    }

    /**
     * ��������¼
     * @param view
     */
    public void btnAuth(View view) {
        Platform platform = ShareSDK.getPlatform(this,SinaWeibo.NAME);

        //�����û�����Ȩ����Ȩ��ʱ����Ҫ��ȡ�û�ID
        platform.setPlatformActionListener(this);

        platform.authorize();
    }

    /**
     * ��ȡ�û�����
     * @param view
     */
    public void btnGetUserInfo(View view) {
        //��ȡָ����ƽ̨��ͨ���ַ�����ȡ
        Platform platform = ShareSDK.getPlatform(this,SinaWeibo.NAME);

        //���õ���ȡ�û��������ʱ�ص��ӿ�
        platform.setPlatformActionListener(this);

        //��ȡָ��ƽ̨�£�ָ���˺ŵ���Ϣ
        //accountΪnullʱ�������ȡ��ǰ��Ȩ�û�
        platform.showUser(null);

    }
    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //�ر�sso��Ȩ
        oks.disableSSOWhenAuthorize();

        // ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
        oks.setTitle(getString(R.string.share));
        // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
        oks.setTitleUrl("http://sharesdk.cn");
        // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
        oks.setText("���Ƿ����ı�");
        // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
//        oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
        // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
        oks.setUrl("http://sharesdk.cn");
        // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
        oks.setComment("���ǲ��������ı�");
        // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
        oks.setSite(getString(R.string.app_name));
        // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
        oks.setSiteUrl("http://sharesdk.cn");

        //�����Ƿ���ʾ���ݱ༭��true��ʾ���ر༭��
        oks.setSilent(true);

        //���÷���ӿڻص�
        oks.setCallback(this);

        //����ֻ����һ��ƽ̨���з���
        oks.setPlatform(SinaWeibo.NAME);

        // ��������GUI
        oks.show(this);
    }

    /**
     * ����ɹ�
     * @param platform �ص���ƽ̨
     * @param i ���������ͣ������������ǰ�ᶯ�Ĳ������Ƿ����ǻ�ȡ�û���Ϣ
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //TODO ����ɹ������

        if(i == Platform.ACTION_SHARE){
            String pName = platform.getName();

            MyLog.d("Share","Platform name = "+pName);

            Toast.makeText(this, "����ɹ���" + pName, Toast.LENGTH_LONG).show();
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
            //��ȡ�û�ID��ʽ�ĵ�������¼
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
