package ks.nankai;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				  Intent intent=new Intent();
				 intent.setClass(StartActivity.this, RemoteControlActivity.class);
				 StartActivity.this.startActivity(intent);
				 StartActivity.this.finish();
				 StartActivity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			}
		}, 1000);
	}



}
