package ks.nankai;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class controlPC extends Activity {
	
	 private ImageButton guanji;
	 private ImageButton chongqi;
	 private ImageButton zhuxiao;
	 private ImageButton daiji;
	 private ImageButton suoding;
	 private ImageButton renwu;
	 private ImageButton xuanze;
	 private ImageButton queding;
	 private EditText shijian;
	 
	 
	 private Calendar c;
	 private Calendar last;
	 private long shutdowntime;
	  
	 
	 public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controlpc);
		  this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//屏蔽Home键
		  
		  guanji=(ImageButton)findViewById(R.id.imageButton_guanji);
		  chongqi=(ImageButton)findViewById(R.id.imageButton_chongqi);
		  zhuxiao=(ImageButton)findViewById(R.id.imageButton_zhuxiao);
		  daiji=(ImageButton)findViewById(R.id.imageButton_daiji);
		  suoding=(ImageButton)findViewById(R.id.imageButton_suoding);
		  renwu=(ImageButton)findViewById(R.id.imageButton_renwuguanli);
		  
		  xuanze=(ImageButton)findViewById(R.id.imageButton_xuanzeshijian);
		  queding=(ImageButton)findViewById(R.id.imageButton_quedingguanji);
		  shijian=(EditText)findViewById(R.id.editText_guanjishike);
		  
		  c = Calendar.getInstance();
		  last=Calendar.getInstance();
		  
		  
		  shijian.setText(TcpServerApplication.controlpcinfo);
		  
		  
		  
		  if(TcpServerApplication.issure==false)
		  {
			  queding.setImageResource(R.drawable.quxiaoguanjiweian);
		  }
		  
		 
		  guanji.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					guanji.setImageResource(R.drawable.guanjianxia);
					TcpServerApplication.Vibrate(controlPC.this, 100);
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("FUN+SHUTDOWN+OK+1");
					guanji.setImageResource(R.drawable.guanjiweian);
		
				}
				return false;
			}
		});
		  
		  
		  chongqi.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						chongqi.setImageResource(R.drawable.chongqianxia);
						TcpServerApplication.Vibrate(controlPC.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("FUN+RESTSRRT+OK+2");
						chongqi.setImageResource(R.drawable.chongqiweian);
			
					}
					return false;
				}
			});
		  
		  zhuxiao.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						zhuxiao.setImageResource(R.drawable.zhuxiaoanxia);
						TcpServerApplication.Vibrate(controlPC.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("FUN+LOGOUT+OK+5");
						zhuxiao.setImageResource(R.drawable.zhuxiaoweian);
			
					}
					return false;
				}
			});
		  
		  daiji.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						daiji.setImageResource(R.drawable.daijianxia);
						TcpServerApplication.Vibrate(controlPC.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("FUN+SLEEP+OK+4");
						daiji.setImageResource(R.drawable.daijiweian);
			
					}
					return false;
				}
			});
		  
		  suoding.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						suoding.setImageResource(R.drawable.suodinganxia);
						TcpServerApplication.Vibrate(controlPC.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("FUN+LOCK+OK+6");
						suoding.setImageResource(R.drawable.suodingweian);
			
					}
					return false;
				}
			});
		  
		  renwu.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						renwu.setImageResource(R.drawable.renwuguanlianxia);
						TcpServerApplication.Vibrate(controlPC.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("FUN+MANGER+OK+3");
						renwu.setImageResource(R.drawable.renwuguanliweian);
			
					}
					return false;
				}
			});
		  
		  xuanze.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						xuanze.setImageResource(R.drawable.xuanzeshijiananxia);
						TcpServerApplication.Vibrate(controlPC.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						//sendMessage("FUN+MANGER+OK+3");
						xuanze.setImageResource(R.drawable.xuanzeshijianweian);
						
						c.setTimeInMillis(System.currentTimeMillis());      

				           int hour = c.get(Calendar.HOUR_OF_DAY);             

				           int minute = c.get(Calendar.MINUTE);

				            new TimePickerDialog(controlPC.this,new OnTimeSetListener() {

				                @Override
				                public void onTimeSet(TimePicker view,int hourOfDay,int minute)

				               {

				                   last.setTimeInMillis(System.currentTimeMillis());                        

				                   last.set(Calendar.HOUR_OF_DAY,hourOfDay);                        

				                   last.set(Calendar.MINUTE, minute);                        

				                   last.set(Calendar.SECOND, 0);                        

				                   last.set(Calendar.MILLISECOND, 0);      
				                   
				                   
				                   shijian.setText("关机时机:"+last.get(Calendar.HOUR_OF_DAY)+":"+last.get(Calendar.MINUTE));
				                   TcpServerApplication.controlpcinfo=shijian.getText().toString();
				                   shutdowntime=(last.getTimeInMillis()-c.getTimeInMillis())/1000;

				               }                

				            }, hour, minute, true).show();
			
					}
					return false;
				}
			});
		  
		  queding.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(TcpServerApplication.issure)
					{
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						queding.setImageResource(R.drawable.quedingguanjianxia);
						TcpServerApplication.Vibrate(controlPC.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						//sendMessage("FUN+MANGER+OK+3");
						if(shutdowntime>=0)
						{
						queding.setImageResource(R.drawable.quedingguangjiweian);
						sendMessage("FUN+SHUTDOWNTIME+"+shutdowntime+"+OK");
		                 shijian.setText("关机时机:"+last.get(Calendar.HOUR_OF_DAY)+":"+last.get(Calendar.MINUTE)+"  总秒数:"+shutdowntime);
		                 TcpServerApplication.controlpcinfo=shijian.getText().toString();
		                 queding.setImageResource(R.drawable.quxiaoguanjiweian);
		                 TcpServerApplication.issure=false;
						}
						else
						{
							queding.setImageResource(R.drawable.quedingguangjiweian);
							Toast.makeText(controlPC.this, "不能早于当前时间，秒数未负数了",
									Toast.LENGTH_SHORT).show();
							TcpServerApplication.issure=true;
						}
					}
					}
					else
					{
						if(event.getAction() == MotionEvent.ACTION_DOWN)
						{
							queding.setImageResource(R.drawable.quxiaoguanjianxia);
							TcpServerApplication.Vibrate(controlPC.this, 100);
						}
						if(event.getAction() == MotionEvent.ACTION_UP)
						{
							//sendMessage("FUN+MANGER+OK+3");
							
							queding.setImageResource(R.drawable.quxiaoguanjiweian);
							 sendMessage("FUN+SHUTDOWNCANCEL+OK+7");
			                 shijian.setText("定时关机已取消");
			                 TcpServerApplication.controlpcinfo=shijian.getText().toString();
			                 queding.setImageResource(R.drawable.quedingguangjiweian);
			                 TcpServerApplication.issure=true;
						
						}
					}
					return false;
				}
			});
		  
		  
	}
	
	private void sendMessage(String str) {
		// System.out.println(str);
		try {
			if(TcpServerApplication.isConnect&&TcpServerApplication.tcpsocket.isConnected())
			{
	          byte buffer[] = (str+"\r\n").getBytes(); 
	          TcpServerApplication.outstream.write(buffer, 0, buffer.length);
	          TcpServerApplication.outstream.flush();
			}
			 else
			  {
				  TcpServerApplication.isConnect=false;
				  TcpServerApplication.tcpsocket=null;
			  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 屏蔽home键
	 @Override
	 public void onAttachedToWindow() {
	  // TODO Auto-generated method stub
	  this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	  super.onAttachedToWindow();
	 }
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	  // TODO Auto-generated method stub
	  switch(keyCode) {
	  case KeyEvent.KEYCODE_BACK:
		  doBack();
	   break;
	  case KeyEvent.KEYCODE_HOME:
	   
	   break;
	  }
	  return false;
	 }   

	 
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			super.onCreateOptionsMenu(menu);
			getMenuInflater().inflate(R.menu.controlpcmenu, menu);
			return true;
		}
		
		/**
		 * 捕捉菜单事件
		 */
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.control_exit:
				doExit();
				return true;
			
			case R.id.control_back:
				doBack();
				return true;
			case R.id.control_help:
				help();
				return true;
			case R.id.control_mouse:
				domouse();
				return true;
			case R.id.control_keyboard:
				dokeyboard();
				return true;
			case R.id.control_hand:
				dohand();
				return true;
			}
			return false;
		}
		
		private void dohand()
		{
			Intent intent = new Intent(controlPC.this, HandActivity.class);
			controlPC.this.startActivity(intent);
			this.finish();
		}
		
		private void dokeyboard()
		{
			Intent intent = new Intent(controlPC.this, KeyBoardActivity.class);
			controlPC.this.startActivity(intent);
			this.finish();
		}
		private void domouse()
		{
			Intent intent = new Intent(controlPC.this, MouseControlActivity.class);
			controlPC.this.startActivity(intent);
			this.finish();
		}
	private void doBack() {
		Intent intent = new Intent(controlPC.this, RemoteControlActivity.class);
		controlPC.this.startActivity(intent);
		this.finish();
	}


	private void help(){
		new AlertDialog.Builder(controlPC.this).setTitle("使用帮助")
		.setMessage("本界面用来控制电脑 \n选择想要的功能按下按钮即可  \n").setIcon(R.drawable.icon)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// finish();
			}
		}).setNegativeButton("返回",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub

					}

				}).show();
	}
	
	
	protected void doExit() {
		new AlertDialog.Builder(this)
				.setMessage(getString(R.string.exit_message))
				.setPositiveButton(getString(R.string.confirm),
						new DialogInterface.OnClickListener() {
							public void onClick(
								DialogInterface dialoginterface, int i) {
								sendMessage("EXIT+OFF+OK+OUT");
								controlPC.this.finish();
								exitapp();
							}
						})
				.setNeutralButton(getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
							}

						}).show();
	}
		
		//广播的内部类，当收到关闭事件时，调用finish方法结束activity  
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
	   	       @Override  
	   	        public void onReceive(Context context, Intent intent) {  
	   	             controlPC.this.finish();  
	   	         }  
	   	    }; 
	   	    
	   	    
    	    /* (non-Javadoc)
		 * @see android.app.Activity#onStart()
		 */
		@Override
		protected void onStart() {
			// TODO Auto-generated method stub
			  //在当前的activity中注册广播  
		    IntentFilter filter = new IntentFilter();  
		    filter.addAction("GlobalVarable.EXIT_ACTION");  
		    this.registerReceiver(this.broadcastReceiver, filter);  
			super.onStart();
		}
		
		/* (non-Javadoc)
		 * @see android.app.Activity#onStop()
		 */
		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			this.unregisterReceiver(broadcastReceiver);
			super.onStop();
		}
	   	    
	   	    
	
	 private void exitapp()
	 {
		 TcpServerApplication.stopGetpictureThread();
		 if(TcpServerApplication.isConnect)
		 {
			 
			 if(TcpServerApplication.tcpsocket.isConnected())
			 {
				 sendMessage("EXIT+OFF+OK+OUT");
				 
			  try {
				TcpServerApplication.tcpsocket.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }
			 TcpServerApplication.tcpsocket=null;
			 
			 
			 TcpServerApplication.isConnect=false;
		 }
		 Intent intent = new Intent();
		 intent.setAction("GlobalVarable.EXIT_ACTION"); // 退出动作
		 this.sendBroadcast(intent);// 发送广播
		 super.finish();
		 this.finish();
		 //退出后台线程,以及销毁静态变量
		 System.exit(0);
	 }
 
}
