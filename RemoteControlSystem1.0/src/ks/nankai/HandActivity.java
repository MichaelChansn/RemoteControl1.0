package ks.nankai;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageButton;

public class HandActivity extends Activity {
	private ImageButton up;
	private ImageButton down;
	private ImageButton left;
	private ImageButton right;
	private ImageButton leftup;
	private ImageButton leftdown;
	private ImageButton rightup;
	private ImageButton rightdown;
	private ImageButton zhongjian;
	private ImageButton space;
	private ImageButton enter;	
	
	private ImageButton A;
	private ImageButton B;
	private ImageButton C;
	private ImageButton D;
	private ImageButton leftmouse;
	
	private ImageButton start;
	private ImageButton stop;
	
	private ImageButton jiasudu;
	
	private static float mx = 0; // 发送的鼠标移动的差值
	private static float my = 0;
	private static float lx; // 记录上次鼠标的位置
	private static float ly;
	private static float fx; // 手指第一次接触屏幕时的坐标
	private static float fy;
	

	private SensorManager sensorManager;
	private MySensorEventListener mySensorEventListener;
	private ButtonListener touchListener;

	private boolean isopen=false;
	//private boolean isopensensor=false;
	
	private void findViews()
	{
		up=(ImageButton)findViewById(R.id.imageButton_up);
		down=(ImageButton)findViewById(R.id.imageButton_down);
		left=(ImageButton)findViewById(R.id.imageButton_left);
		right=(ImageButton)findViewById(R.id.imageButton_right);
		leftup=(ImageButton)findViewById(R.id.imageButton_leftup);
		leftdown=(ImageButton)findViewById(R.id.imageButton_leftdown);
		rightup=(ImageButton)findViewById(R.id.imageButton_rightup);
		rightdown=(ImageButton)findViewById(R.id.imageButton_rightdown);
		zhongjian=(ImageButton)findViewById(R.id.imageButton_zhingjian);
		A=(ImageButton)findViewById(R.id.imageButton_A);
		B=(ImageButton)findViewById(R.id.imageButton_B);
		C=(ImageButton)findViewById(R.id.imageButton_C);
		D=(ImageButton)findViewById(R.id.imageButton_D);
		leftmouse=(ImageButton)findViewById(R.id.imageButton_leftmouse);
		start=(ImageButton)findViewById(R.id.imageButton_start);
		stop=(ImageButton)findViewById(R.id.imageButton_stop);
		
		space=(ImageButton)findViewById(R.id.imageButton_space);
		enter=(ImageButton)findViewById(R.id.ImageButton_enter);
		
		
		jiasudu=(ImageButton)findViewById(R.id.imageButton_kaizhongli);
		mySensorEventListener= new MySensorEventListener();//这个监听器当然是我们自己定义的，在重力感     应器感应到手机位置有变化的时候，我们可以采取相应的操作，这里紧紧是将x,y,z的值打印出来
		sensorManager=(SensorManager) this.getSystemService(SENSOR_SERVICE); 
		touchListener=new ButtonListener();
		
	}
	
	private void setUpFunctions()
	{
		jiasudu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TcpServerApplication.Vibrate(HandActivity.this, 100);
				if(isopen==false)
				{
					isopen=true;
					jiasudu.setBackgroundResource(R.drawable.guanzhongli);
				//	isopensensor=true;
				}
				else
				{
					isopen=false;
					jiasudu.setBackgroundResource(R.drawable.kaizhongli);
					//isopensensor=false;
				}
				
				
			}
		});
		up.setOnTouchListener(touchListener);
		down.setOnTouchListener(touchListener);
		left.setOnTouchListener(touchListener);
		right.setOnTouchListener(touchListener);
		leftup.setOnTouchListener(touchListener);
		leftdown.setOnTouchListener(touchListener);
		rightup.setOnTouchListener(touchListener);
		rightdown.setOnTouchListener(touchListener);
		A.setOnTouchListener(touchListener);
		B.setOnTouchListener(touchListener);
		C.setOnTouchListener(touchListener);
		D.setOnTouchListener(touchListener);
		
		zhongjian.setOnTouchListener(touchListener);
		leftmouse.setOnTouchListener(touchListener);
		space.setOnTouchListener(touchListener);
		enter.setOnTouchListener(touchListener);
		start.setOnTouchListener(touchListener);
		stop.setOnTouchListener(touchListener);
		
		
		
	}
	   public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hand);
		 // this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//屏蔽Home键
		findViews();
		setUpFunctions();

	}
	
	
	
	
	 class ButtonListener implements OnTouchListener{  
		  
		    
		  
	        public boolean onTouch(View v, MotionEvent event) {  
	          switch(v.getId())
	          {
	          case R.id.imageButton_up:
					
	        	  if(event.getAction()==MotionEvent.ACTION_DOWN)
	        	  {
	        		    sendMessage("GAME+UP_DOWN+OK+1");
						up.setImageResource(R.drawable.youanxia);
						TcpServerApplication.Vibrate(HandActivity.this, 100);
	        	  }
	        	  else
	        		  if(event.getAction()==MotionEvent.ACTION_UP)
	        	  {
	        			  sendMessage("GAME+UP_UP+OK+2");
	  					up.setImageResource(R.drawable.you_weian);
	        	  }
					
					break;
					
				case R.id.imageButton_down:
					
					
					 if(event.getAction()==MotionEvent.ACTION_DOWN)
		        	  {
						 sendMessage("GAME+DOWN_DOWN+OK+3");
							down.setImageResource(R.drawable.zuoanxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
		        	  }
		        	  else
		        		  if(event.getAction()==MotionEvent.ACTION_UP)
		        	  {
		        			  sendMessage("GAME+DOWN_UP+OK+4");
		  					down.setImageResource(R.drawable.zuoweian);
		        	  }
				
					break;
					
				case R.id.imageButton_left:
					
					
					 if(event.getAction()==MotionEvent.ACTION_DOWN)
		        	  {
						    sendMessage("GAME+LEFT_DOWN+OK+5");
							left.setImageResource(R.drawable.shanganxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
		        	  }
		        	  else
		        		  if(event.getAction()==MotionEvent.ACTION_UP)
		        	  {
		        			  sendMessage("GAME+LEFT_UP+OK+6");
		  					left.setImageResource(R.drawable.shangweian);
		        	  }
				
					break;
				case R.id.imageButton_right:
		
					
					 if(event.getAction()==MotionEvent.ACTION_DOWN)
			    	  {
						   sendMessage("GAME+RIGHT_DOWN+OK+7");
							right.setImageResource(R.drawable.xiaanxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
			    	  }
			    	  else
			    		  if(event.getAction()==MotionEvent.ACTION_UP)
			    	  {
			    			   sendMessage("GAME+RIGHT_UP+OK+8");
								right.setImageResource(R.drawable.xiaweian);
			    	  }
				
					break;
				case R.id.imageButton_leftup:
					
					
					 if(event.getAction()==MotionEvent.ACTION_DOWN)
			    	  {
						    sendMessage("GAME+UP_DOWN+OK+1");
							sendMessage("GAME+LEFT_DOWN+OK+5");
							leftup.setImageResource(R.drawable.youshanganxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
			    	  }
			    	  else
			    		  if(event.getAction()==MotionEvent.ACTION_UP)
			    	  {
			    			  sendMessage("GAME+UP_UP+OK+2");
								sendMessage("GAME+LEFT_UP+OK+6");
								leftup.setImageResource(R.drawable.youshangweian);
			    	  }
				
					break;
				case R.id.imageButton_leftdown:
					
				
					 if(event.getAction()==MotionEvent.ACTION_DOWN)
			    	  {
						 sendMessage("GAME+LEFT_DOWN+OK+5");
							sendMessage("GAME+DOWN_DOWN+OK+3");
							leftdown.setImageResource(R.drawable.zuoshanganxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
			    	  }
			    	  else
			    		  if(event.getAction()==MotionEvent.ACTION_UP)
			    	  {
			    			  sendMessage("GAME+LEFT_UP+OK+6");
								sendMessage("GAME+DOWN_UP+OK+4");
								leftdown.setImageResource(R.drawable.zuoshangweian);
			    	  }
				
					break;
				case R.id.imageButton_rightup:
					
					
					 if(event.getAction()==MotionEvent.ACTION_DOWN)
			    	  {
						 sendMessage("GAME+RIGHT_DOWN+OK+7");
							sendMessage("GAME+UP_DOWN+OK+1");
							rightup.setImageResource(R.drawable.youxiaanxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
			    	  }
			    	  else
			    		  if(event.getAction()==MotionEvent.ACTION_UP)
			    	  {
			    			  sendMessage("GAME+RIGHT_UP+OK+8");
								sendMessage("GAME+UP_UP+OK+2");
								rightup.setImageResource(R.drawable.youxiaweian);
			    	  }
				
					break;
				case R.id.imageButton_rightdown:
					
					
					 if(event.getAction()==MotionEvent.ACTION_DOWN)
			    	  {
						    sendMessage("GAME+RIGHT_DOWN+OK+7");
							sendMessage("GAME+DOWN_DOWN+OK+3");
							rightdown.setImageResource(R.drawable.zuoxiaanxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
			    	  }
			    	  else
			    		  if(event.getAction()==MotionEvent.ACTION_UP)
			    	  {
			    			  sendMessage("GAME+RIGHT_UP+OK+8");
								sendMessage("GAME+DOWN_UP+OK+4");
								rightdown.setImageResource(R.drawable.zuoxiaweian);
			    	  }
				
					break;
				case R.id.imageButton_A:
					
					
					 if(event.getAction()==MotionEvent.ACTION_DOWN)
			    	  {
						 sendMessage("GAME+A_DOWN+OK+9");
							A.setImageResource(R.drawable.a_anxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
			    	  }
			    	  else
			    		  if(event.getAction()==MotionEvent.ACTION_UP)
			    	  {
			    			  sendMessage("GAME+A_UP+OK+10");
								A.setImageResource(R.drawable.a);
			    	  }
				
					break;
					
				 case R.id.imageButton_B:
						
		        	  if(event.getAction()==MotionEvent.ACTION_DOWN)
		        	  {
		        		  sendMessage("GAME+B_DOWN+OK+11");
							B.setImageResource(R.drawable.b_anxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
		        	  }
		        	  else
		        		  if(event.getAction()==MotionEvent.ACTION_UP)
		        	  {
		        			  sendMessage("GAME+B_UP+OK+12");
								B.setImageResource(R.drawable.b);
		        	  }
						
						break;
						
					case R.id.imageButton_C:
						
						
						 if(event.getAction()==MotionEvent.ACTION_DOWN)
			        	  {
							 sendMessage("GAME+C_DOWN+OK+13");
								C.setImageResource(R.drawable.c_anxia);
								TcpServerApplication.Vibrate(HandActivity.this, 100);
			        	  }
			        	  else
			        		  if(event.getAction()==MotionEvent.ACTION_UP)
			        	  {
			        			  sendMessage("GAME+C_UP+OK+14");
									C.setImageResource(R.drawable.c);
			        	  }
					
						break;
						
					case R.id.imageButton_D:
						
						
						 if(event.getAction()==MotionEvent.ACTION_DOWN)
			        	  {
							 sendMessage("GAME+D_DOWN+OK+15");
								D.setImageResource(R.drawable.d_anxia);
								TcpServerApplication.Vibrate(HandActivity.this, 100);
			        	  }
			        	  else
			        		  if(event.getAction()==MotionEvent.ACTION_UP)
			        	  {
			        			  sendMessage("GAME+D_UP+OK+16");
									D.setImageResource(R.drawable.d);
			        	  }
					
						break;
					case R.id.imageButton_zhingjian:
			
						
						 if(event.getAction()==MotionEvent.ACTION_DOWN)
				    	  {
							 sendMessage("MOUSE+RIGHT_DOWN+OK+7");
								
								zhongjian.setImageResource(R.drawable.zhongjiananxia);
								TcpServerApplication.Vibrate(HandActivity.this, 100);
				    	  }
				    	  else
				    		  if(event.getAction()==MotionEvent.ACTION_UP)
				    	  {
				    			  sendMessage("MOUSE+RIGHT_UP+OK+8");
									
									zhongjian.setImageResource(R.drawable.zhongjian);
				    	  }
					
						break;
					case R.id.imageButton_leftmouse:
						
					  if (event.getAction() == MotionEvent.ACTION_MOVE)
							onMouseMove(event);
						if(event.getAction() == MotionEvent.ACTION_DOWN)
						{
							onMouseDown(event);
							//sendMessage("MOUSE+LEFT_DOWN+OK+5");
							leftmouse.setBackgroundResource(R.drawable.zhongjianshubiaoanxian);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
							
						}
						if(event.getAction() == MotionEvent.ACTION_UP)
						{
							onMouseUp(event);
							//sendMessage("MOUSE+LEFT_UP+OK+6");
							leftmouse.setBackgroundResource(R.drawable.zhongjianshubiao);
				
						}
					
						break;
					case R.id.imageButton_space:
						
						if(event.getAction() == MotionEvent.ACTION_DOWN)
						{
							sendMessage("SPECIAL+SPACE_DOWN+OK+DONE");
							space.setImageResource(R.drawable.konggeanxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
						}
						if(event.getAction() == MotionEvent.ACTION_UP)
						{
							sendMessage("SPECIAL+SPACE_UP+OK+DONE");
							space.setImageResource(R.drawable.konggeweian);
				
						}
					
						break;
					case R.id.ImageButton_enter:
						
						if(event.getAction() == MotionEvent.ACTION_DOWN)
						{
							sendMessage("SPECIAL+ENTER_DOWN+OK+DONE");
							enter.setImageResource(R.drawable.huicheanxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
						}
						if(event.getAction() == MotionEvent.ACTION_UP)
						{
							sendMessage("SPECIAL+ENTER_UP+OK+DONE");
							enter.setImageResource(R.drawable.huicheweian);
				
						}
					
						break;
					case R.id.imageButton_start:
						
						if(event.getAction() == MotionEvent.ACTION_DOWN)
						{
							start.setImageResource(R.drawable.kaishianxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
						}
						if(event.getAction() == MotionEvent.ACTION_UP)
						{
							sendMessage("GAME+START+OK+17");
							start.setImageResource(R.drawable.kaishiweian);
				
						}
						break;
					case R.id.imageButton_stop:
						
						if(event.getAction() == MotionEvent.ACTION_DOWN)
						{
							stop.setImageResource(R.drawable.tingzhianxia);
							TcpServerApplication.Vibrate(HandActivity.this, 100);
						}
						if(event.getAction() == MotionEvent.ACTION_UP)
						{
							sendMessage("GAME+STOP+OK+18");
							stop.setImageResource(R.drawable.tingzhiweian);
				
						}
					
						break;
					
					
				default:
						break;
	          
	          }
	            return true;  
	        }

		
	          
	 }
	



	private void onMouseDown(MotionEvent ev) {
		lx = ev.getX(); // 当手机第一放入时 把当前坐标付给lx
		ly = ev.getY();
		fx = ev.getX();
		fy = ev.getY();
	}
	private void onMouseUp(MotionEvent ev) {
		if (fx == ev.getX() && fy == ev.getY()) {//单击左键
			sendMessage("MOUSE+LEFT_CLICK+OK+1");
			
		}

	}
	
	private void onMouseMove(MotionEvent ev) {
		float x = ev.getX();
		mx = x - lx; // 当前鼠标位置 - 上次鼠标的位置
		lx = x; // 把当前鼠标的位置付给lx 以备下次使用
		float y = ev.getY();
		my = y - ly;
		ly = y;
		if (mx != 0 && my != 0)
			this.sendMouseEvent("MOUSE", (int)mx, (int)my);

	}
	
	 
	private void sendMouseEvent(String type, int x, int y) {//MOUSE+MOVE+X+Y
		String str = type +"+" +"MOVE"+"+" + (y) + "+" + (-x);//横屏坐标轴转换
		sendMessage(str);
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
		getMenuInflater().inflate(R.menu.handmenu, menu);
		return true;
	}

	/**
	 * 捕捉菜单事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.handhelp:
			help();
			return true;
		case R.id.reback:
			doBack();
			return true;
		case R.id.exit:
			doExit();
			return true;
		case R.id.hand_pc:
			dopc();
			return true;
		case R.id.hand_mouse:
			domouse();
			return true;
		case R.id.hand_keyboard:
			dokeyboard();
			return true;
		case R.id.hand_openmutil:
			dohandmutil();
			return true;
		}

		return false;
	}

	
	private void dohandmutil()
	{
		Intent intent = new Intent(HandActivity.this, HandMutil.class);
		HandActivity.this.startActivity(intent);
		this.finish();
	}
	private void dopc()
	{
		Intent intent = new Intent(HandActivity.this, controlPC.class);
		HandActivity.this.startActivity(intent);
		this.finish();
	}
	
	private void dokeyboard()
	{
		Intent intent = new Intent(HandActivity.this, KeyBoardActivity.class);
		HandActivity.this.startActivity(intent);
		this.finish();
	}
	private void domouse()
	{
		Intent intent = new Intent(HandActivity.this, MouseControlActivity.class);
		HandActivity.this.startActivity(intent);
		this.finish();
	}
	private void help() {
		new AlertDialog.Builder(HandActivity.this)
				.setTitle("使用帮助")
				.setMessage("按键定义:\n上(W) 下(S) 左(A) 右(D)\n A(J)   B(K)   C(U)   D(I)  \n开始(L)  停止(O)\n左边中间是鼠标右键 右边中间是鼠标左键")
				.setIcon(R.drawable.icon)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// finish();
					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}

				}).show();
	}





	private void doBack() {
		Intent intent = new Intent(HandActivity.this, RemoteControlActivity.class);
		HandActivity.this.startActivity(intent);
		this.finish();
	}



	
	protected void doExit() {
		new AlertDialog.Builder(this)
				.setMessage(getString(R.string.exit_message))
				.setPositiveButton(getString(R.string.confirm),
						new DialogInterface.OnClickListener() {
							public void onClick(
								DialogInterface dialoginterface, int i) {
								sendMessage("EXIT+OFF+OK+OUT");
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
	

	private boolean isup=false;
	private boolean isdown=false;
	private boolean isleft=false;
	private boolean isright=false;
	private String TAG="HandActivity";
	private final class MySensorEventListener implements  SensorEventListener
	{
	@Override
	public void onSensorChanged(SensorEvent event) 
	{
	//重力传感器
	if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
	{
		if(isopen)
		{
	float x = event.values[SensorManager.DATA_X];
	float y = event.values[SensorManager.DATA_Y];
	float z = event.values[SensorManager.DATA_Z];
	//tv_accelerometer是界面上的一个TextView标签，不再赘述
	Log.e(TAG,"Orientation:"+x+","+y+","+z);
	/**
	 * 手机竖着放
	 */
	if(z>5)//上
	{
		if(isup==false)
		{
			isup=true;
			sendMessage("GAME+UP_DOWN+OK+1");
		}
	}
	if(z<-5)//下
	{
		if(isdown==false)
		{
			isdown=true;
		sendMessage("GAME+DOWN_DOWN+OK+1");
		//sendMessage("GAME+UP_UP+OK+4");
		}
	}
	
	if(z>=-5 && z<=5)//上下恢复
	{
		if(isup==true)
		{
		isup=false;
		sendMessage("GAME+UP_UP+OK+2");
		}
		if(isdown==true)
		{
			isdown=false;
			sendMessage("GAME+DOWN_UP+OK+4");
		}
	}
	
	if(y<-5)//横屏左
	{
		if(isleft==false)
		{
		sendMessage("GAME+LEFT_DOWN+OK+5 ");
		//sendMessage("GAME+RIGHT_UP+OK+8");
		isleft=true;
		}
	}
	if(y>5 )//横屏右
	{
		if(isright==false)
		{
			isright=true;
		sendMessage("GAME+RIGHT_DOWN+OK+7");
		//sendMessage("GAME+LEFT_UP+OK+6");
		}
	}
	if(y>=-5 && y<=5)
	{
		
		
		
		if(isleft==true)
		{
		isleft=false;
		sendMessage("GAME+LEFT_UP+OK+6");
		}
		if(isright==true)
		{
			isright=false;
			sendMessage("GAME+RIGHT_UP+OK+8");
		}
		
	}
	
	/* 手机横放
	if(x<-5)//横屏 上
	{
		if(isup==false)
		{
			isup=true;
		sendMessage("GAME+UP_DOWN+OK+1");
		//sendMessage("GAME+DOWN_UP+OK+4");
		//sendMessage("GAME+LEFT_DOWN+OK+5");wwssssss
		//sendMessage("GAME+LEFT_DOWN+OK+5");
		}
		
	}
	if(x>5)//横屏下
	{
		if(isdown==false)
		{
			isdown=true;
		sendMessage("GAME+DOWN_DOWN+OK+1");
		//sendMessage("GAME+UP_UP+OK+4");
		}
	}
	if(x>=-5 && x<=5)
	{
		if(isup==true)
		{
		isup=false;
		sendMessage("GAME+UP_UP+OK+2");
		}
		if(isdown==true)
		{
			isdown=false;
			sendMessage("GAME+DOWN_UP+OK+4");
		}
	}
	if(y<-5)//横屏左
	{
		if(isleft==false)
		{
		sendMessage("GAME+LEFT_DOWN+OK+5 ");
		//sendMessage("GAME+RIGHT_UP+OK+8");
		isleft=true;
		}
	}
	if(y>5 )//横屏右
	{
		if(isright==false)
		{
			isright=true;
		sendMessage("GAME+RIGHT_DOWN+OK+7");
		//sendMessage("GAME+LEFT_UP+OK+6");
		}
	}
	if(y>=-5 && y<=5)
	{
		
		
		
		if(isleft==true)
		{
		isleft=false;
		sendMessage("GAME+LEFT_UP+OK+6");
		}
		if(isright==true)
		{
			isright=false;
			sendMessage("GAME+RIGHT_UP+OK+8");
		}
		
	}*/
	}
	}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	}

	
		

	//广播的内部类，当收到关闭事件时，调用finish方法结束activity  
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
	   	       @Override  
	   	        public void onReceive(Context context, Intent intent) {  
	   	             HandActivity.this.finish();  
	   	         }  
	   	    }; 

	   	  void registerReceiverAndListener()
		  {
			    IntentFilter filter = new IntentFilter();  
		        filter.addAction("GlobalVarable.EXIT_ACTION");  
		        this.registerReceiver(this.broadcastReceiver, filter);  
		        
		        Sensor sensor_accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		        sensorManager.registerListener(mySensorEventListener,sensor_accelerometer,   SensorManager.SENSOR_DELAY_GAME);
		     
		  }
		  void unregisterReceiverAndListener()
		  {
			  this.unregisterReceiver(broadcastReceiver);
			  sensorManager.unregisterListener(mySensorEventListener);
			  
			  
		  }
	   		/* (non-Javadoc)
	   		 * @see android.app.Activity#onStart()
	   		 */
	   		@Override
	   		protected void onStart() {
	   			// TODO Auto-generated method stub
	   			registerReceiverAndListener();
	   			super.onStart();
	   		}



	   		/* (non-Javadoc)
	   		 * @see android.app.Activity#onStop()
	   		 */
	   		@Override
	   		protected void onStop() {
	   			// TODO Auto-generated method stub
	   			unregisterReceiverAndListener();
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
