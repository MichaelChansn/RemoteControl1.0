package ks.nankai;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MouseControlActivity extends Activity {
	private static float mx = 0; // 发送的鼠标移动的差值
	private static float my = 0;
	private static float lx; // 记录上次鼠标的位置
	private static float ly;
	private static float fx; // 手指第一次接触屏幕时的坐标
	private static float fy;
	private static float lbx = 0; // 鼠标左键移动初始化坐标
	private static float lby = 0;
	private Handler handler = new Handler();
	private Runnable leftButtonDown;
    private Runnable leftButtonRealease;
	private Runnable rightButtonDown;
	private Runnable rightButtonRealease;

	private FrameLayout leftButton;
	private FrameLayout rightButton;
	private FrameLayout middleButton;
	private  FrameLayout touch;
	private LinearLayout mouseButtonGone;
	private LinearLayout mouseMiddleButton;
	private LinearLayout longPress;
	
	private EditText    text_trans;
	private TextView    noConnection;

	
	
	private Thread showpict;
	private ImageView pictview;
	private Bitmap bitmap;
	private boolean isrun;
	  
	
	private int screenw;
	private int screenh;
	private int densityDPI;
	private double screenWidthInInch;
	private double screenHeightInInch;
	private DisplayMetrics dm;  
	private int clear=20;  
	 public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	 
	 private final String TAG="ControlActivity";
	 
	 //手势识别
	 GestureDetector gestureDetector;
	
	public  byte[] inputStream2ByteArr(InputStream in) throws Exception{ 
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
		byte[] buffer = new byte[1024*50]; 
		
		int len =  0; 
		
		while((len = in.read(buffer)) != -1) { 
		outputStream.write(buffer, 0, len); 
		} 
		outputStream.flush(); 
		outputStream.close(); 
		//in.close(); 
		return outputStream.toByteArray(); 
		}
	private Bitmap decodeBitmap(InputStream is) throws Exception {
		if (is == null) {
			return null ;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		//设置该属性可以不占用内存，并且能够得到bitmap的宽高等属性，此时得到的bitmap是空
		options.inJustDecodeBounds = true;
		byte[] data = inputStream2ByteArr(is);//将InputStream转为byte数组，可以多次读取
		 bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		//设置计算得到的压缩比例
		options.inSampleSize = 1;
		//设置为false，确保可以得到bitmap != null
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
	return bitmap;
	}
	
	   
	// 屏蔽home键和返回键
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
			  backmainview();
			  return false;
		  
		  case KeyEvent.KEYCODE_HOME:
			  return false;
		  case KeyEvent.KEYCODE_VOLUME_UP:
			  clear=clear+5;
			  if(clear>100)
				  clear=100;
				  
			  sendMessage("CLEAR+"+clear+"+OK"+"+DONE");
			  return true;
		  case KeyEvent.KEYCODE_VOLUME_DOWN:
			  clear=clear-5;
			  if(clear<0)
				  clear=0;
			  sendMessage("CLEAR+"+clear+"+OK"+"+DONE");
			  return true;
		  
			
		  }
		  return super.onKeyDown(keyCode, event);
		 }  
		 
	private void showIME()
	{
		
		 InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	       text_trans.setFocusable(true);
			// text_trans.setFocusableInTouchMode(true);
			 text_trans.requestFocus();
			// text_trans.requestFocusFromTouch();
	        
	}
	
	
	


	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mousecontrol);
		
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//屏蔽Home键
		pictview=(ImageView)findViewById(R.id.imageView1);
		text_trans=(EditText)findViewById(R.id.text_transout);
		noConnection=(TextView) findViewById(R.id.noConnection);
		noConnection.setVisibility(View.GONE);
		dm = new DisplayMetrics();   
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenw=dm.widthPixels;
		screenh=dm.heightPixels;
		//densityDPI = dm.densityDpi; 
		screenWidthInInch=screenw/(double)dm.xdpi;
		screenHeightInInch=screenh/(double)dm.ydpi;
		//showerror=(EditText)findViewById(R.id.editText1);
		initTouch();
		
		initFunctions();
	 
		
		if(TcpServerApplication.isConnect)
		{
		      // 传送屏幕大小 单位像素 
		    //  sendMessage("SCREEN+"+screenw+"+"+screenh+"+OK");//告诉服务端 可以发送屏幕截图了
		     
		    // 传送屏幕大小 单位英寸 
			 sendMessage("SCREEN+"+screenWidthInInch+"+"+screenHeightInInch+"+OK");//告诉服务端 可以发送屏幕截图了	      
		     sendMessage("PICTURE+START+OK+DONE");
		     TcpServerApplication.isGetPictureStart=true;
			 isrun=true;
		     
		     showpict.start();
		}
		else
		{
			noConnection.setVisibility(View.VISIBLE);
			
		}
	}
	


	private void initFunctions()
	{
	    gestureDetector=new GestureDetector(new MyGestureListener());
		
		text_trans.setOnKeyListener(new OnKeyListener() 
		{               
			@Override              
			public boolean onKey(View v, int keyCode, KeyEvent event) 
			{   
			
				if (keyCode == KeyEvent.KEYCODE_DEL) 
				{  
					if(event.getAction()==KeyEvent.ACTION_DOWN)  
					{
					sendMessage("SPECIAL+BACKSPACE_DOWN+OK+DONE");  
					sendMessage("SPECIAL+BACKSPACE_UP+OK+DONE");
					} 
			
				}
	
				
				return false;             
					}          
			});
		
		

		 text_trans.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				System.out.println("onTextChanged得到的数据是：——>"+text_trans.getText().toString());
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				System.out.println("beforeTextChanged得到的数据是：——>"+text_trans.getText().toString());
				
			}
		
			
			@SuppressLint("NewApi")
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				System.out.println("afterTextChanged得到的数据是：——>"+text_trans.getText().toString());
			
				String gettext=text_trans.getText().toString();
				if(!gettext.isEmpty())
				{
				sendMessage("KEY+"+gettext+"+OK+DONE");

				text_trans.getText().clear();//setText("");
				}
			
			}
		});
		 
		 
		 
		 
		 
		 text_trans.setOnEditorActionListener(new OnEditorActionListener()
		 {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
			{
				// TODO Auto-generated method stub
				
					if (actionId == EditorInfo.IME_ACTION_DONE||actionId ==EditorInfo.IME_ACTION_UNSPECIFIED) 
					{
						//System.out.println("这个是回车键按下了哦J克隆空款就考了利率J");
						sendMessage("SPECIAL+ENTER_DOWN+OK+DONE");
						sendMessage("SPECIAL+ENTER_UP+OK+DONE");
					}
				
			
				return true;
			}
		});
		 
		 showpict=new Thread(new Runnable() {
				
				//@SuppressLint("NewApi")
				@Override
				public  void run() {
					// TODO Auto-generated method stub
					 
					while(isrun)
					{
					
						try {
							Thread.sleep(200);
							  pictview.post(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										
										pictview.setImageBitmap(TcpServerApplication.getBitmapCache());
										//pictview.setImageMatrix(new )
										
									}
								});
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				   

					
					}
					
				}
			});
			   
		 
		
	}
	//用来计算长按
	private long startPressTimes;
	private long endPressTimes;
	private final int pressTime=1;
	private void startPressTime()
	{
		startPressTimes=System.currentTimeMillis();
	}
	private void endPressTime()
	{
		endPressTimes=System.currentTimeMillis();
	}

	
	
	private void initTouch() {
		touch = (FrameLayout) this.findViewById(R.id.touch);

		// let's set up a touch listener
		// this.registerForContextMenu(touch);   
		
		
		touch.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent ev) {
				
				//手势识别
				gestureDetector.onTouchEvent(ev);
				
				if (ev.getAction() == MotionEvent.ACTION_MOVE)
				{
					
					
					if(ev.getPointerCount()>1)
					{
						onMiddleButtonMove(ev);
					}
					else
					{
						onMouseMove(ev);
					}
					//return true;//不用继续相应长按事件
				}
					
				if (ev.getAction() == MotionEvent.ACTION_DOWN)
				{
					if(ev.getPointerCount()>1)
					{
						onMiddleButtonDown(ev);
						//return true;
					}
					else
					{
					onMouseDown(ev);
					
					//return false;//继续相应长按事件
					}
					
				}
				if (ev.getAction() == MotionEvent.ACTION_UP)
				{
					onMouseUp(ev);
					//return false;//继续相应长按事件
					
				}
				
				//要想继续相应长按事件必须设置为false
				return true;
			}
			
		});
		
		/**
		 * 长按 鼠标右键
		 * 但是长按和滑动有冲突，所哟采用定时器自己实现长按事件
		 */
	
		/*
		touch.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Log.e(TAG, "Long Click......");
				clickRightMouse();
				return false;
			}
		});
		*/
	
	
		
		

		//鼠标三个按键，已抛弃，采用多触点代替
		mouseButtonGone=(LinearLayout) findViewById(R.id.mouseButtonGone);
		mouseButtonGone.setVisibility(8);
		
		/**
		 * 侧边栏鼠标滚轮，已抛弃，采用多触点滑动代替
		 */
		/*mouseMiddleButton=(LinearLayout) findViewById(R.id.mouseMiddleButton);
	
		mouseMiddleButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_MOVE)
					onMiddleButtonMove(event);
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					onMiddleButtonDown(event);
				
				
				return true;
			}
		});

		*/
		
		leftButton = (FrameLayout) this.findViewById(R.id.leftButton);
		leftButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent ev) {
				if (ev.getAction() == MotionEvent.ACTION_DOWN) {
					onLeftButtonDown();
					handler.post(leftButtonDown);
				}
				if (ev.getAction() == MotionEvent.ACTION_UP) {
					onLeftButtonUp();
					lbx = 0;
					lby = 0;
					handler.post(leftButtonRealease);
				}
				
				return true;
			}
		});

		rightButton = (FrameLayout) this.findViewById(R.id.rightButton);
		rightButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent ev) {
				if (ev.getAction() == MotionEvent.ACTION_DOWN) {
					onRightButtonDown();
					handler.post(rightButtonDown);
				}
				if (ev.getAction() == MotionEvent.ACTION_UP) {
					onRightButtonUp();
					handler.post(rightButtonRealease);
				}
				return true;
			}
		});

	   middleButton = (FrameLayout) this.findViewById(R.id.middleButton);
		middleButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent ev) {
				if (ev.getAction() == MotionEvent.ACTION_DOWN)
					onMiddleButtonDown(ev);
				if (ev.getAction() == MotionEvent.ACTION_MOVE)
					onMiddleButtonMove(ev);
				return true;
			}

		});

		this.leftButtonDown = new Runnable() {
			public void run() {
				drawLeftButtonDown(leftButton);
		
			}

			private void drawLeftButtonDown(FrameLayout fl) {
				fl.setBackgroundResource(R.drawable.zuoc);
			}
		};

		this.rightButtonDown = new Runnable() {
			public void run() {
				drawButtonDown(rightButton);
			}

			private void drawButtonDown(FrameLayout fl) {
				fl.setBackgroundResource(R.drawable.youc);
			}
		};

		this.leftButtonRealease = new Runnable() {
			public void run() {
				drawLeftButtonRealease(leftButton);
			}

			private void drawLeftButtonRealease(FrameLayout fl) {
				fl.setBackgroundResource(R.drawable.zuo);

			}
		};

		this.rightButtonRealease = new Runnable() {
			public void run() {
				drawButtonRealease(rightButton);
			}

			private void drawButtonRealease(FrameLayout fl) {
				fl.setBackgroundResource(R.drawable.you);

			}
		};

	}




	
	private void onMouseDown(MotionEvent ev) {
		lx = ev.getX(); // 当手机第一放入时 把当前坐标付给lx
		ly = ev.getY();
		fx = ev.getX();
		fy = ev.getY();
		//startPressTime();//长按计时
	}

	private void onMouseMove(MotionEvent ev) {
		
		
		float x = ev.getX();
		mx = x - lx; // 当前鼠标位置 - 上次鼠标的位置
		lx = x; // 把当前鼠标的位置付给lx 以备下次使用
		float y = ev.getY();
		my = y - ly;
		ly = y;
		if ((Math.abs(mx) > 3 && my != 0)||(mx!=0 && Math.abs(my)>3))
			this.sendMouseEvent("MOUSE", (int)mx, (int)my);

	}

	private void onMouseUp(MotionEvent ev) {
		if (fx == ev.getX() && fy == ev.getY()) {//单击左键
			//endPressTime();//长按计时
			sendMessage("MOUSE+LEFT_CLICK+OK+1");
			/*
			if(endPressTimes-startPressTimes>pressTime*1000)//长按事件
			{
				clickRightMouse();
			}
			else//单击事件
			{
				sendMessage("MOUSE+LEFT_CLICK+OK+1");
			}
			*/
			
		}

	}

	private void sendMouseEvent(String type, int x, int y) {//MOUSE+MOVE+X+Y
		String str = type +"+" +"MOVE"+"+" + x + "+" + y;
		sendMessage(str);
	}

	private void onLeftButtonDown() {
		String str = "MOUSE+LEFT_DOWN+OK+5" ;
		sendMessage(str);

	}
	
	private void onLeftButtonUp() {
		String str = "MOUSE+LEFT_UP+OK+6" ;
		sendMessage(str);

	}

	private void onRightButtonDown() {
		String str ="MOUSE+RIGHT_DOWN+OK+7";
		sendMessage(str);
	}

	private void onRightButtonUp() {
		String str = "MOUSE+RIGHT_UP+OK+8";
		sendMessage(str);
	}
	
	private void clickRightMouse()
	{
		onRightButtonDown();
		onRightButtonUp();
		
	}
	
	private void sendMessage(String str) {
		try {
			if(TcpServerApplication.isConnect)
			{
	          byte buffer[] = (str+"\r\n").getBytes(); 
	          TcpServerApplication.outstream.write(buffer, 0, buffer.length);
	          TcpServerApplication.outstream.flush();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void onMiddleButtonDown(MotionEvent ev) {
		ly = ev.getY();

	}

	private void onMiddleButtonMove(MotionEvent ev) {
		// count++;

		float y = ev.getY();
		my = y - ly;
		ly = y;
		if (my > 3 || my < -3) { // 减少发送次数 滑轮移动慢点
			String str = "MOUSE+WHEEL"+"+"+(int)((-my)*3)+"+"+"9";
			sendMessage(str);
		}

	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.mousecontrol, menu);
		return true;
	}

	/**
	 * 捕捉菜单事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mousecontrol_help:
			help();
			return true;
		case R.id.mousecontrol_mainview:
			backmainview();
			return true;
		case R.id.mousecontrol_exit:
			doExit();
			return true;
			
		case R.id.mouse_hand:
			dohand();
			return true;
			
		case R.id.mouse_keyboard:
			dokeyboard();
			return true;
			
		case R.id.mouse_pc:
			dopc();
			return true;
			
		case R.id.mousecontrol_softkeyboard:
			showIME();
			return true;
			
		case R.id.mousecontrol_openmoive:
			sendMessage("MOVE+START+OK+DONE");
			return true;
			
		case R.id.mousecontrol_closemoive:
			sendMessage("MOVE+STOP+OK+DONE");
			return true;
			
		case R.id.mousecontrol_openfull:
			sendMessage("FULL+START+OK+DONE");
			return true;
			
		case R.id.mousecontrol_closefull:
			sendMessage("FULL+STOP+OK+DONE");
			return true;
		}
		return false;
	}
	
	private void dohand()
	{
		Intent intent = new Intent(MouseControlActivity.this, HandActivity.class);
		MouseControlActivity.this.startActivity(intent);
		sendMessage("PICTURE+STOP+OK+DONE");
		TcpServerApplication.isGetPictureStart=false;
		isrun=false;
		this.finish();
	}
	
	private void dokeyboard()
	{
		Intent intent = new Intent(MouseControlActivity.this, KeyBoardActivity.class);
		MouseControlActivity.this.startActivity(intent);
		sendMessage("PICTURE+STOP+OK+DONE");
		isrun=false;
		TcpServerApplication.isGetPictureStart=false;
		this.finish();
	}
	private void dopc()
	{
		Intent intent = new Intent(MouseControlActivity.this, controlPC.class);
		MouseControlActivity.this.startActivity(intent);
		sendMessage("PICTURE+STOP+OK+DONE");
		isrun=false;
		TcpServerApplication.isGetPictureStart=false;
		this.finish();
	}
	private void help(){
		new AlertDialog.Builder(MouseControlActivity.this).setTitle("使用帮助")
		.setMessage("本界面用来控制电脑鼠标 \n向无线鼠标一样使用即可  \n").setIcon(R.drawable.icon)
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


	
private void backmainview()
{
	
	Intent intent = new Intent(MouseControlActivity.this, RemoteControlActivity.class);
	this.startActivity(intent);
	
	sendMessage("PICTURE+STOP+OK+DONE");
	isrun=false;
	TcpServerApplication.isGetPictureStart=false;
	//showpict.stop();
	this.finish();
}
	

	
	// 退出程序
		protected void doExit() {
			new AlertDialog.Builder(this)
					.setMessage(getString(R.string.exit_message))
					.setPositiveButton(getString(R.string.confirm),
							new DialogInterface.OnClickListener() {
								public void onClick(
									DialogInterface dialoginterface, int i) {
									//showpict.destroy();
									isrun=false;
									sendMessage("EXIT+OFF+OK+OUT");
									//showpict.stop();
									finish();
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
	   	            MouseControlActivity.this.finish();  
	   	         }  
	   	    }; 
	    @Override  
        public void onStart() {  
    	         super.onStart();  
    	         //在当前的activity中注册广播  
    	        IntentFilter filter = new IntentFilter();  
    	        filter.addAction("GlobalVarable.EXIT_ACTION");  
    	        this.registerReceiver(this.broadcastReceiver, filter);  
    	        TcpServerApplication.isGetPictureStart=true;
    	     }  
    	  @Override
    protected void onStop() {
    		// TODO Auto-generated method stub
    	 super.onStop();
    	 TcpServerApplication.isGetPictureStart=false;
    	this.unregisterReceiver(broadcastReceiver);
    	  }
    	  
    	  
    	  
    	  
	
	private void exitapp()
	 {
		isrun=false;
		TcpServerApplication.isGetPictureStart=false;
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
		 //退出后台线程,以及销毁静态变量
		 System.exit(0);
	 }
	 
	
	
	
	
	
	//手势识别类,主要用来识别长按
	private class MyGestureListener extends SimpleOnGestureListener//implements OnGestureListener
	{

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			//Toast.makeText(SecondMainActivity.this, "OnDown Pressed", 600).show();
			
		
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			//Toast.makeText(SecondMainActivity.this, "OnFling Pressed"+":"+velocityX+":"+velocityY, 600).show();
			System.out.println("OnFling Pressed"+":"+velocityX+":"+velocityY);
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			//Toast.makeText(SecondMainActivity.this, "OnLongPressed Pressed", 600).show();
			System.out.println("OnLongPressed Pressed");
			clickRightMouse();
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			//Toast.makeText(SecondMainActivity.this, "OnScroll Pressed"+":"+distanceX+":"+distanceY, 600).show();
			System.out.println( "OnScroll Pressed"+":"+distanceX+":"+distanceY);
			
			
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			//Toast.makeText(SecondMainActivity.this, "onShowPress Pressed", 600).show();
			System.out.println( "onShowPress Pressed");
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			//Toast.makeText(SecondMainActivity.this, "onSingleTapUp Pressed", 600).show();
			System.out.println("onSingleTapUp Pressed");
			
			return false;
		}
		
	}
	
	
	
	
		
}
