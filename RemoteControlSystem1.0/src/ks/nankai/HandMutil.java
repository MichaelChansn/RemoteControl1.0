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
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class HandMutil extends Activity {
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
	
	private static float mx = 0; // 发送的鼠标移动的差值
	private static float my = 0;
	private static float lx; // 记录上次鼠标的位置
	private static float ly;
	private static float fx; // 手指第一次接触屏幕时的坐标
	private static float fy;
	
	private int[] A_location=new int[2];
	private int[] B_location=new int[2];
	private int[] C_location=new int[2];
	private int[] D_location=new int[2];
	private int[] leftmouse_location=new int[2];
	
	
	
	private int[] up_location=new int[2];
	private int[] down_location=new int[2];
	private int[] left_location=new int[2];
	private int[] right_location=new int[2];
	private int[] leftup_location=new int[2];
	private int[] leftdown_location=new int[2];
	private int[] rightup_location=new int[2];
	private int[] rightdown_location=new int[2];
	private int[] zhongjian_location=new int[2];
	private int[] handtouch_location=new int[2];


	private int A_heigh;
	private int A_width;
	private int B_heigh;
	private int B_width;
	private int C_heigh;
	private int C_width;
	private int D_heigh;
	private int D_width;
	
	private int leftmouse_heigh;
	private int leftmouse_width;
	private int zhongjian_heigh;
	private int zhongjian_width;
	
	private int up_heigh;
	private int up_width;
	private int down_heigh;
	private int down_width;
	private int left_heigh;
	private int left_width;
	private int right_heigh;
	private int right_width;
	
	private int leftup_heigh;
	private int leftup_width;
	private int leftdown_heigh;
	private int leftdown_width;
	private int rightup_heigh;
	private int rightup_width;
	private int rightdown_heigh;
	private int rightdown_width;
    
	private FrameLayout handtouch;
	private SensorManager sensorManager;
	private MySensorEventListener mySensorEventListener;
	private ImageButton jiasudu;

	private boolean isopen=false;

	
	   public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handmutil);
		  this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//屏蔽Home键
		up=(ImageButton)findViewById(R.id.imageButton_up_handmutil);
		down=(ImageButton)findViewById(R.id.imageButton_down_handmutil);
		left=(ImageButton)findViewById(R.id.imageButton_left_handmutil);
		right=(ImageButton)findViewById(R.id.imageButton_right_handmutil);
		leftup=(ImageButton)findViewById(R.id.imageButton_leftup_handmutil);
		leftdown=(ImageButton)findViewById(R.id.imageButton_leftdown_handmutil);
		rightup=(ImageButton)findViewById(R.id.imageButton_rightup_handmutil);
		rightdown=(ImageButton)findViewById(R.id.imageButton_rightdown_handmutil);
		zhongjian=(ImageButton)findViewById(R.id.imageButton_zhingjian_handmutil);
		A=(ImageButton)findViewById(R.id.imageButton_A_handmutil);
		B=(ImageButton)findViewById(R.id.imageButton_B_handmutil);
		C=(ImageButton)findViewById(R.id.imageButton_C_handmutil);
		D=(ImageButton)findViewById(R.id.imageButton_D_handmutil);
		leftmouse=(ImageButton)findViewById(R.id.imageButton_leftmouse_handmutil);
		start=(ImageButton)findViewById(R.id.imageButton_start_handmutil);
		stop=(ImageButton)findViewById(R.id.imageButton_stop_handmutil);
		
		space=(ImageButton)findViewById(R.id.imageButton_space_handmutil);
		enter=(ImageButton)findViewById(R.id.ImageButton_enter_handmutil);
		 handtouch=(FrameLayout)findViewById(R.id.handtouch);
		 
		 jiasudu=(ImageButton)findViewById(R.id.imageButton_mutilkaizhogli);
			mySensorEventListener= new MySensorEventListener();//这个监听器当然是我们自己定义的，在重力感     应器感应到手机位置有变化的时候，我们可以采取相应的操作，这里紧紧是将x,y,z的值打印出来
			sensorManager=(SensorManager) this.getSystemService(SENSOR_SERVICE); 
		 
		
		 handtouch.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
					//GetRaeX getRawY 是第一个接触屏幕的坐标 和 最后一个抬起的坐标
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					//onMouseDown(event);
					//System.out.println("ACTION_DOWN-->"+"RawX:"+event.getX()+"\n RawY:"+event.getY());
					action_down(event,event.getX(),event.getY());
					
				}
					
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					//onMouseUp(event);
					//event.getX()
					//System.out.println("ACTION_UP-->"+"RawX:"+event.getX()+"\n RawY:"+event.getY());
					action_up(event,event.getX(),event.getY());
					
				}
				if(event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN)
				{
					//onMouseDown(event);
					//System.out.println("ACTION_DOWN-2->"+"RawX:"+event.getX()+"\n RawY:"+event.getY());
					action_down(event,event.getX(1),event.getY(1));
					
				}
				if(event.getAction() == MotionEvent.ACTION_POINTER_2_UP)
				{
					//onMouseUp(event);
					//System.out.println("ACTION_UP-2->"+"RawX:"+event.getX()+"\n RawY:"+event.getY());
					action_up(event,event.getX(1),event.getY(1));
					
				}
				if(event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN)
				{
					
					//onMouseDown(event);//System.out.println("ACTION_DOWN-2->"+"RawX:"+event.getX()+"\n RawY:"+event.getY());
					action_down(event,event.getX(2),event.getY(2));
					
				}
				if(event.getAction() == MotionEvent.ACTION_POINTER_3_UP)
				{
					//onMouseUp(event);
					//System.out.println("ACTION_UP-2->"+"RawX:"+event.getX()+"\n RawY:"+event.getY());
					action_up(event,event.getX(2),event.getY(2));
					
				}
					
				if(event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN)
				{
					//onMouseDown(event);
					//System.out.println("ACTION_DOWN-1->"+"RawX:"+event.getX()+"\n RawY:"+event.getY());
					action_down(event,event.getX(0),event.getY(0));
					
				}
				if(event.getAction() == MotionEvent.ACTION_POINTER_1_UP)
				{
					//onMouseUp(event);
					//System.out.println("ACTION_UP-1->"+"RawX:"+event.getX()+"\n RawY:"+event.getY());
					action_up(event,event.getX(0),event.getY(0));
					
				}
			
				
				return true;
			}
		});
		
	
		 jiasudu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TcpServerApplication.Vibrate(HandMutil.this, 100);
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
	
	 space.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+SPACE_DOWN+OK+DONE");
						space.setImageResource(R.drawable.konggeanxia);
						TcpServerApplication.Vibrate(HandMutil.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+SPACE_UP+OK+DONE");
						space.setImageResource(R.drawable.konggeweian);
			
					}
					return false;
				}
			});
		 
		
			enter.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+ENTER_DOWN+OK+DONE");
						enter.setImageResource(R.drawable.huicheanxia);
						TcpServerApplication.Vibrate(HandMutil.this, 100);
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+ENTER_UP+OK+DONE");
						enter.setImageResource(R.drawable.huicheweian);
			
					}
					return false;
				}
			});
			
	start.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				start.setImageResource(R.drawable.kaishianxia);
				TcpServerApplication.Vibrate(HandMutil.this, 100);
			}
			if(event.getAction() == MotionEvent.ACTION_UP)
			{
				sendMessage("GAME+START+OK+17");
				start.setImageResource(R.drawable.kaishiweian);
	
			}
			return false;
		}
	});
	
stop.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				stop.setImageResource(R.drawable.tingzhianxia);
				TcpServerApplication.Vibrate(HandMutil.this, 100);
			}
			if(event.getAction() == MotionEvent.ACTION_UP)
			{
				sendMessage("GAME+STOP+OK+18");
				stop.setImageResource(R.drawable.tingzhiweian);
	
			}
			return false;
		}
	});



	}
	
	
	
	public void action_down(MotionEvent event,float getx,float gety)
	{
		float x=getx+handtouch_location[0];//event.getX()+handtouch_location[0];
		float y=gety+handtouch_location[1];//event.getY()+handtouch_location[1];
		if(x>=left_location[0] && x<=(left_location[0]+left_width) && y>=left_location[1] && y<=(left_location[1]+left_heigh))
		{
			//左键
			sendMessage("GAME+LEFT_DOWN+OK+5");
			left.setImageResource(R.drawable.shanganxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		
		if(x>=right_location[0] && x<=(right_location[0]+right_width) && y>=right_location[1] && y<=(right_location[1]+right_heigh))
		{//右键
			sendMessage("GAME+RIGHT_DOWN+OK+7");
			right.setImageResource(R.drawable.xiaanxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=up_location[0] && x<=(up_location[0]+up_width) && y>=up_location[1] && y<=(up_location[1]+up_heigh))
		{//上键
			sendMessage("GAME+UP_DOWN+OK+1");
			up.setImageResource(R.drawable.youanxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=down_location[0] && x<=(down_location[0]+down_width) && y>=down_location[1] && y<=(down_location[1]+down_heigh))
		{//下键
			sendMessage("GAME+DOWN_DOWN+OK+3");
			down.setImageResource(R.drawable.zuoanxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=leftup_location[0] && x<=(leftup_location[0]+leftup_width) && y>=leftup_location[1] && y<=(leftup_location[1]+leftup_heigh))
		{//左上键
			sendMessage("GAME+UP_DOWN+OK+1");
			sendMessage("GAME+LEFT_DOWN+OK+5");
			leftup.setImageResource(R.drawable.youshanganxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		
		if(x>=leftdown_location[0] && x<=(leftdown_location[0]+leftdown_width) && y>=leftdown_location[1] && y<=(leftdown_location[1]+leftdown_heigh))
		{//左下键
			sendMessage("GAME+LEFT_DOWN+OK+5");
			sendMessage("GAME+DOWN_DOWN+OK+3");
			leftdown.setImageResource(R.drawable.zuoshanganxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=rightup_location[0] && x<=(rightup_location[0]+rightup_width) && y>=rightup_location[1] && y<=(rightup_location[1]+rightup_heigh))
		{//右上键
			sendMessage("GAME+RIGHT_DOWN+OK+7");
			sendMessage("GAME+UP_DOWN+OK+1");
			rightup.setImageResource(R.drawable.youxiaanxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		if(x>=rightdown_location[0] && x<=(rightdown_location[0]+rightdown_width) && y>=rightdown_location[1] && y<=(rightdown_location[1]+rightdown_heigh))
		{//右下键
			sendMessage("GAME+RIGHT_DOWN+OK+7");
			sendMessage("GAME+DOWN_DOWN+OK+3");
			rightdown.setImageResource(R.drawable.zuoxiaanxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=A_location[0] && x<=(A_location[0]+A_width) && y>=A_location[1] && y<=(A_location[1]+A_heigh))
		{//A键
			sendMessage("GAME+A_DOWN+OK+9");
			A.setImageResource(R.drawable.a_anxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		if(x>=B_location[0] && x<=(B_location[0]+B_width) && y>=B_location[1] && y<=(B_location[1]+B_heigh))
		{//B键
			sendMessage("GAME+B_DOWN+OK+11");
			B.setImageResource(R.drawable.b_anxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=C_location[0] && x<=(C_location[0]+C_width) && y>=C_location[1] && y<=(C_location[1]+C_heigh))
		{//C键
			sendMessage("GAME+C_DOWN+OK+13");
			C.setImageResource(R.drawable.c_anxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=D_location[0] && x<=(D_location[0]+D_width) && y>=D_location[1] && y<=(D_location[1]+D_heigh))
		{//D键
			sendMessage("GAME+D_DOWN+OK+15");
			D.setImageResource(R.drawable.d_anxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=leftmouse_location[0] && x<=(leftmouse_location[0]+leftmouse_width) && y>=leftmouse_location[1] && y<=(leftmouse_location[1]+leftmouse_heigh))
		{//右中键
			//onMouseDown(event);
			sendMessage("MOUSE+LEFT_DOWN+OK+5");
			leftmouse.setBackgroundResource(R.drawable.zhongjianshubiaoanxian);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=zhongjian_location[0] && x<=(zhongjian_location[0]+zhongjian_width) && y>=zhongjian_location[1] && y<=(zhongjian_location[1]+zhongjian_heigh))
		{//左中键
			sendMessage("MOUSE+RIGHT_DOWN+OK+7");
			
			zhongjian.setImageResource(R.drawable.zhongjiananxia);
			TcpServerApplication.Vibrate(HandMutil.this, 100);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void action_up(MotionEvent event,float getx,float gety)
	{
		float x=getx+handtouch_location[0];//event.getX()+handtouch_location[0];
		float y=gety+handtouch_location[1];//event.getY()+handtouch_location[1];
		if(x>=left_location[0] && x<=(left_location[0]+left_width) && y>=left_location[1] && y<=(left_location[1]+left_heigh))
		{
			sendMessage("GAME+LEFT_UP+OK+6");
			left.setImageResource(R.drawable.shangweian);
			//tcpcon.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=right_location[0] && x<=(right_location[0]+right_width) && y>=right_location[1] && y<=(right_location[1]+right_heigh))
		{
			sendMessage("GAME+RIGHT_UP+OK+8");
			right.setImageResource(R.drawable.xiaweian);
			//tcpcon.Vibrate(HandMutil.this, 100);
		}
		
		if(x>=up_location[0] && x<=(up_location[0]+up_width) && y>=up_location[1] && y<=(up_location[1]+up_heigh))
		{//上键
			sendMessage("GAME+UP_UP+OK+2");
			up.setImageResource(R.drawable.you_weian);
		}
		
		if(x>=down_location[0] && x<=(down_location[0]+down_width) && y>=down_location[1] && y<=(down_location[1]+down_heigh))
		{//下键
			sendMessage("GAME+DOWN_UP+OK+4");
			down.setImageResource(R.drawable.zuoweian);
		}
		if(x>=leftup_location[0] && x<=(leftup_location[0]+leftup_width) && y>=leftup_location[1] && y<=(leftup_location[1]+leftup_heigh))
		{//左上键
			sendMessage("GAME+UP_UP+OK+2");
			sendMessage("GAME+LEFT_UP+OK+6");
			leftup.setImageResource(R.drawable.youshangweian);
		}
		
		if(x>=leftdown_location[0] && x<=(leftdown_location[0]+leftdown_width) && y>=leftdown_location[1] && y<=(leftdown_location[1]+leftdown_heigh))
		{//左下键
			sendMessage("GAME+LEFT_UP+OK+6");
			sendMessage("GAME+DOWN_UP+OK+4");
			leftdown.setImageResource(R.drawable.zuoshangweian);
		}
		
		if(x>=rightup_location[0] && x<=(rightup_location[0]+rightup_width) && y>=rightup_location[1] && y<=(rightup_location[1]+rightup_heigh))
		{//右上键
			sendMessage("GAME+RIGHT_UP+OK+8");
			sendMessage("GAME+UP_UP+OK+2");

			rightup.setImageResource(R.drawable.youxiaweian);
		}
		
		if(x>=rightdown_location[0] && x<=(rightdown_location[0]+rightdown_width) && y>=rightdown_location[1] && y<=(rightdown_location[1]+rightdown_heigh))
		{//右下键
			sendMessage("GAME+RIGHT_UP+OK+8");
			sendMessage("GAME+DOWN_UP+OK+4");
			rightdown.setImageResource(R.drawable.zuoxiaweian);
		}
		
		if(x>=A_location[0] && x<=(A_location[0]+A_width) && y>=A_location[1] && y<=(A_location[1]+A_heigh))
		{//A键
			sendMessage("GAME+A_UP+OK+10");
			A.setImageResource(R.drawable.a);
		}
		
		if(x>=B_location[0] && x<=(B_location[0]+B_width) && y>=B_location[1] && y<=(B_location[1]+B_heigh))
		{//B键
			sendMessage("GAME+B_UP+OK+12");
			B.setImageResource(R.drawable.b);
		}
		
		if(x>=C_location[0] && x<=(C_location[0]+C_width) && y>=C_location[1] && y<=(C_location[1]+C_heigh))
		{//C键
			sendMessage("GAME+C_UP+OK+14");
			C.setImageResource(R.drawable.c);
		}
		
		if(x>=D_location[0] && x<=(D_location[0]+D_width) && y>=D_location[1] && y<=(D_location[1]+D_heigh))
		{//D键
			sendMessage("GAME+D_UP+OK+16");
			D.setImageResource(R.drawable.d);
		}
		if(x>=leftmouse_location[0] && x<=(leftmouse_location[0]+leftmouse_width) && y>=leftmouse_location[1] && y<=(leftmouse_location[1]+leftmouse_heigh))
		{//右中键
			//onMouseUp(event);
			sendMessage("MOUSE+LEFT_UP+OK+6");
			leftmouse.setBackgroundResource(R.drawable.zhongjianshubiao);
		}
		if(x>=zhongjian_location[0] && x<=(zhongjian_location[0]+zhongjian_width) && y>=zhongjian_location[1] && y<=(zhongjian_location[1]+zhongjian_heigh))
		{//左中键
			sendMessage("MOUSE+RIGHT_UP+OK+8");
			//onMouseUp(event);
			zhongjian.setImageResource(R.drawable.zhongjian);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		//过早调用会返回0
				A.getLocationOnScreen(A_location);
				B.getLocationOnScreen(B_location);
				C.getLocationOnScreen(C_location);
				D.getLocationOnScreen(D_location);
				leftmouse.getLocationOnScreen(leftmouse_location);
				zhongjian.getLocationOnScreen(zhongjian_location);
				
				up.getLocationOnScreen(up_location);
				down.getLocationOnScreen(down_location);
				left.getLocationOnScreen(left_location);
				right.getLocationOnScreen(right_location);
				
				leftup.getLocationOnScreen(leftup_location);
				leftdown.getLocationOnScreen(leftdown_location);
				rightup.getLocationOnScreen(rightup_location);
				rightdown.getLocationOnScreen(rightdown_location);
				handtouch.getLocationOnScreen(handtouch_location);
				
				
				
				A_heigh=A.getHeight();
				A_width=A.getWidth();
				
				B_heigh=B.getHeight();
				B_width=B.getWidth();
				
				C_heigh=C.getHeight();
				C_width=C.getWidth();
				
				D_heigh=D.getHeight();
				D_width=D.getWidth();
				
				leftmouse_heigh=leftmouse.getHeight();
				leftmouse_width=leftmouse.getWidth();
				
				zhongjian_heigh=zhongjian.getHeight();
				zhongjian_width=zhongjian.getWidth();
				
				up_heigh=up.getHeight();
				up_width=up.getWidth();
				
				down_heigh=down.getHeight();
				down_width=down.getWidth();
				
				left_heigh=left.getHeight();
				left_width=left.getWidth();
				
				right_heigh=right.getHeight();
				right_width=right.getWidth();
				
				leftup_heigh=leftup.getHeight();
				leftup_width=leftup.getWidth();

				leftdown_heigh=leftdown.getHeight();
				leftdown_width=leftdown.getWidth();
				
				rightup_heigh=rightup.getHeight();
				rightup_width=rightup.getWidth();
				
				rightdown_heigh=rightdown.getHeight();
				rightdown_width=rightdown.getWidth();
			
				
				
		super.onWindowFocusChanged(hasFocus);
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

	
	
	private boolean isup=false;
	private boolean isdown=false;
	private boolean isleft=false;
	private boolean isright=false;
	
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
	System.out.println("Orientation:"+x+","+y+","+z);
	
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
	/**
	 *  手机横着放
	 */
	/*
	if(x<-5)//横屏 上
	{
		if(isup==false)
		{
			isup=true;
		sendMessage("GAME+UP_DOWN+OK+1");
		//sendMessage("GAME+DOWN_UP+OK+4");
		//sendMessage("GAME+LEFT_DOWN+OK+5");
		//sendMessage("GAME+LEFT_DOWN+OK+5");
		}
		
	}
	if(x>5)//横屏下wwwwwwwwwwwwwwwwwwwwwwwwaaaaaaaaaaaaaaaaaaaaa
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


	
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		registerReceiverAndListener();
		Log.e("TEST", "onstart");
		super.onStart();
	}



	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.e("TEST", "onstop");
		unregisterReceiverAndListener();
		super.onStop();
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
		getMenuInflater().inflate(R.menu.handnutilmenu, menu);
		return true;
	}

	/**
	 * 捕捉菜单事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.handmutil_help:
			help();
			return true;
		case R.id.handmutil_reback:
			doBack();
			return true;
		case R.id.handmutil_exit:
			doExit();
			return true;
		case R.id.handmutil_pc:
			dopc();
			return true;
		case R.id.handmutil_mouse:
			domouse();
			return true;
		case R.id.handmutil_keyboard:
			dokeyboard();
			return true;
		case R.id.handmutil_opensingle:
			dohandsingle();
			return true;
		}

		return false;
	}

	
	private void dohandsingle()
	{
		Intent intent = new Intent(HandMutil.this, HandActivity.class);
		HandMutil.this.startActivity(intent);
		this.finish();
	}
	private void dopc()
	{
		Intent intent = new Intent(HandMutil.this, controlPC.class);
		HandMutil.this.startActivity(intent);
		this.finish();
	}
	
	private void dokeyboard()
	{
		Intent intent = new Intent(HandMutil.this, KeyBoardActivity.class);
		HandMutil.this.startActivity(intent);
		this.finish();
	}
	private void domouse()
	{
		Intent intent = new Intent(HandMutil.this, MouseControlActivity.class);
		HandMutil.this.startActivity(intent);
		this.finish();
	}
	private void help() {
		new AlertDialog.Builder(HandMutil.this)
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
		Intent intent = new Intent(HandMutil.this, RemoteControlActivity.class);
		HandMutil.this.startActivity(intent);
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
	
		//广播的内部类，当收到关闭事件时，调用finish方法结束activity  
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
	   	       @Override  
	   	        public void onReceive(Context context, Intent intent) {  
	   	             HandMutil.this.finish();  
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
