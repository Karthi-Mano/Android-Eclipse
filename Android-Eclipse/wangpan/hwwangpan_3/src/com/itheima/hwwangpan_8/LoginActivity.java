package com.itheima.hwwangpan_8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.vdisk.android.VDiskAuthSession;
import com.vdisk.android.VDiskDialogListener;
import com.vdisk.net.exception.VDiskDialogError;
import com.vdisk.net.exception.VDiskException;
import com.vdisk.net.session.AccessToken;
import com.vdisk.net.session.AppKeyPair;
import com.vdisk.net.session.Session.AccessType;

public class LoginActivity extends SherlockActivity implements VDiskDialogListener {

	/*--- 以下的 CONSUMER_KEY CONSUMER_SECRET REDIRECT_URL 是老师申请的.没有basic权限,不能直接反问目录.为了继续往下写,所以使用demo里面的这个几个东西---------------*/
	/*public static final String	CONSUMER_KEY	= "3237555059";
	public static final String	CONSUMER_SECRET	= "2b6c964b071e2ecc28c1835628cc6901";
	public static final String	REDIRECT_URL	= "http://billy.itheima.com";*/

	/*--------------- 以下的 CONSUMER_KEY CONSUMER_SECRET REDIRECT_URL 是apidemo里面的 ---------------*/
	public static final String	CONSUMER_KEY	= "2330724462";
	public static final String	CONSUMER_SECRET	= "04f81fc56cc936bfc8f0fa1cef285158";
	public static final String	REDIRECT_URL	= "http://vauth.appsina.com/callback1.php";
	private Button				mBtnLogin;
	private VDiskAuthSession	session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		initView();
		initData();
		initListener();
	}

	private void init() {
		/**
		 * 初始化 Init
		 */
		AppKeyPair appKeyPair = new AppKeyPair(CONSUMER_KEY, CONSUMER_SECRET);
		/**
		 * @AccessType.APP_FOLDER - sandbox 模式
		 * @AccessType.VDISK - basic 模式
		 */
		session = VDiskAuthSession.getInstance(this, appKeyPair, AccessType.VDISK);

		// 如果session没有过期.我们就不需要再次授权.直接跳到主界面就可以了
		if (session.isLinked()) {// 未过期
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}

	private void initView() {
		mBtnLogin = (Button) findViewById(R.id.btnLogin);
	}

	private void initData() {
		// TODO

	}

	private void initListener() {
		mBtnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 使用微盘Token认证，需设置重定向网址
				// Need to set REDIRECT_URL if you want to use VDisk token.
				session.setRedirectUrl(REDIRECT_URL);
				// 1.发起授权请求-->让用户输入账号/密码
				session.authorize(LoginActivity.this, LoginActivity.this);
			}
		});
	}

	/*===============  2.处理授权的结果-->得到accessToken ===============*/
	@Override
	public void onComplete(Bundle values) {// 授权完成
		if (values != null) {
			AccessToken mToken = (AccessToken) values.getSerializable(VDiskAuthSession.OAUTH2_TOKEN);
			session.finishAuthorize(mToken);// 把token绑定到session
			Toast.makeText(getApplicationContext(), mToken.getToken(), 0).show();
		}

		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	@Override
	public void onError(VDiskDialogError error) {// 授权错误
		// TODO

	}

	@Override
	public void onVDiskException(VDiskException exception) {// 授权异常
		// TODO

	}

	@Override
	public void onCancel() {// 授权取消
		// TODO

	}

}
