package ks.nankai;


import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KeyBoardActivity extends Activity {
	private EditText inputET;
	private Button sendbutton;
	private Button clearbutton;
	private Button enterButton;
	
	private Button space;
	private Button esc;
	private Button shift;
	private Button ctrl;
	private Button alt;
	private Button tab;
	private Button win;
	private Button f1;
	private Button f2;
	private Button f3;
	private Button f4;
	private Button f5;
	private Button f6;
	private Button f7;
	private Button f8;
	private Button f9;
	private Button f10;
	private Button f11;
	private Button f12;
	private Button end;
	private Button home;
	private Button delete;
	private Button prtsc;
	private Button insert;
	private Button numlock;
	private Button pageup;
	private Button pagedown;
	private Button upkey;
	private Button downkey;
	private Button leftkey;
	private Button rightkey;
	private Button capslock;

	 public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;


	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keyboard);
		
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//屏蔽Home键
		inputET = (EditText) findViewById(R.id.InputEditText);
		
		sendbutton = (Button) findViewById(R.id.SendButton);
		clearbutton = (Button) findViewById(R.id.ClearButton);
		enterButton = (Button) findViewById(R.id.EnterButton);
		
		space=(Button)findViewById(R.id.button_space);
		esc=(Button)findViewById(R.id.button_Esc);
		shift=(Button)findViewById(R.id.button_Shift);
		ctrl=(Button)findViewById(R.id.button_Ctrl);
		alt=(Button)findViewById(R.id.button_Alt);
		tab=(Button)findViewById(R.id.button_Tab);
		
		win=(Button)findViewById(R.id.button_windows);
		f1=(Button)findViewById(R.id.button_F1);
		f2=(Button)findViewById(R.id.button_F2);
		f3=(Button)findViewById(R.id.button_F3);
		f4=(Button)findViewById(R.id.button_F4);
		f5=(Button)findViewById(R.id.button_F5);
		f6=(Button)findViewById(R.id.button_F6);
		f7=(Button)findViewById(R.id.button_F7);
		f8=(Button)findViewById(R.id.button_F8);
		f9=(Button)findViewById(R.id.button_F9);
		f10=(Button)findViewById(R.id.button_F10);
		f11=(Button)findViewById(R.id.button_F11);
		f12=(Button)findViewById(R.id.button_F12);
		
		 end=(Button)findViewById(R.id.button_End);
		home=(Button)findViewById(R.id.button_Home);
		 delete=(Button)findViewById(R.id.button_Delete);
		 prtsc=(Button)findViewById(R.id.button_PrtSc);
		insert=(Button)findViewById(R.id.button_insert);
		 numlock=(Button)findViewById(R.id.button_NumLock);
		pageup=(Button)findViewById(R.id.button_PageUp);
		pagedown=(Button)findViewById(R.id.button_PageDown);
		 upkey=(Button)findViewById(R.id.handtouch);
		downkey=(Button)findViewById(R.id.button_Down);
		leftkey=(Button)findViewById(R.id.button_Left);
		 rightkey=(Button)findViewById(R.id.button_Right);
		 capslock=(Button)findViewById(R.id.button_CapsLock);

	
		
		sendbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String s = inputET.getText().toString();
				if (s == null || s.equals("")) {
					Toast.makeText(KeyBoardActivity.this, "信息为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				sendMessage("KEY+"+s+"+OK+DONE");
				inputET.setText("");
			}

		});

		
		
		/***************************************************
		 * 
		 * 特殊按键的定义
		 * 
		 */
		clearbutton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+BACKSPACE_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+BACKSPACE_UP+OK+DONE");

				}
				return false;
			}
		});


		enterButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+ENTER_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+ENTER_UP+OK+DONE");

				}
				return false;
			}
		});
		
		space.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+SPACE_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+SPACE_UP+OK+DONE");

				}
				return false;
			}
		});
	
		esc.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+ESC_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+ESC_UP+OK+DONE");

				}
				return false;
			}
		});
		
		shift.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+SHIFT_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+SHIFT_UP+OK+DONE");

				}
				return false;
			}
		});
	
		
		ctrl.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+CTRL_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+CTRL_UP+OK+DONE");

				}
				return false;
			}
		});
		
		alt.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+ALT_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+ALT_UP+OK+DONE");

				}
				return false;
			}
		});
		
		tab.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+TAB_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+TAB_UP+OK+DONE");

				}
				return false;
			}
		});
		
		
		win.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+WIN_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+WIN_UP+OK+DONE");

				}
				return false;
			}
		});
		
		
		
		 f1.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F1_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F1_UP+OK+DONE");

					}
					return false;
				}
			});
		 f2.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F2_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F2_UP+OK+DONE");

					}
					return false;
				}
			});
		 
		 f3.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F3_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F3_UP+OK+DONE");

					}
					return false;
				}
			});
		 
		 f4.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F4_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F4_UP+OK+DONE");

					}
					return false;
				}
			});
		
		 f5.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F5_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F5_UP+OK+DONE");

					}
					return false;
				}
			});
		 
		 f6.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F6_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F6_UP+OK+DONE");

					}
					return false;
				}
			});
		 f7.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F7_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F7_UP+OK+DONE");

					}
					return false;
				}
			});
		 
		 f8.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F8_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F8_UP+OK+DONE");

					}
					return false;
				}
			});
		 
		 f9.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F9_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F9_UP+OK+DONE");

					}
					return false;
				}
			});
		 
		 f10.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F10_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F10_UP+OK+DONE");

					}
					return false;
				}
			});
		 f11.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F11_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F11_UP+OK+DONE");

					}
					return false;
				}
			});
		 f12.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+F12_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+F12_UP+OK+DONE");

					}
					return false;
				}
			});
		end.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+END_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+END_UP+OK+DONE");

				}
				return false;
			}
		});
	 home.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+HOME_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+HOME_UP+OK+DONE");

				}
				return false;
			}
		});
		delete.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+DELETE_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+DELETE_UP+OK+DONE");

				}
				return false;
			}
		});
		 prtsc.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+PRTSC_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+PRTSC_UP+OK+DONE");

					}
					return false;
				}
			});
		 insert.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+INTERT_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+INTERT_UP+OK+DONE");

					}
					return false;
				}
			});
		 numlock.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+NUMLOCK_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+NUMLOCK_UP+OK+DONE");

					}
					return false;
				}
			});
		 pageup.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+PAGEUP_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+PAGEUP_UP+OK+DONE");

					}
					return false;
				}
			});
		pagedown.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+PAGEDOWN_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+PAGEDOWN_UP+OK+DONE");

				}
				return false;
			}
		});
		 upkey.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+UPKEY_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+UPKEY_UP+OK+DONE");

					}
					return false;
				}
			});
		 downkey.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+DOWNKEY_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+DOWNKEY_UP+OK+DONE");

					}
					return false;
				}
			});
		 leftkey.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					{
						sendMessage("SPECIAL+LEFTKEY_DOWN+OK+DONE");
					}
					if(event.getAction() == MotionEvent.ACTION_UP)
					{
						sendMessage("SPECIAL+LEFTKEY_UP+OK+DONE");

					}
					return false;
				}
			});
	 rightkey.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+RIGHTKEY_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+RIGHTKEY_UP+OK+DONE");

				}
				return false;
			}
		});
		capslock.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				// TODO Auto-generated method stub
			
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					sendMessage("SPECIAL+CAPSLOCK_DOWN+OK+DONE");
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					sendMessage("SPECIAL+CAPSLOCK_UP+OK+DONE");

				}
				return false;
			}
		});
		

		
		/************************************************************
		 * 
		 * 特殊按键的定义结束
		 * 
		 * 
		*************************************************************/
		
		
		
		
	
		/*upButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					
						sendMessage("GAME+UP_DOWN+OK+1");
					
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					
						sendMessage("GAME+UP_UP+OK+2");
						sendMessage("GAME+LEFT_UP+OK+6 ");
						sendMessage("GAME+RIGHT_UP+OK+8");
					
				} else if (event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN) {
					if (event.getX(1) < 0 && event.getY(1) > 50) {
						
							sendMessage("GAME+LEFT_DOWN+OK+5");
						
					}

					if (event.getX(1) > 150 && event.getX(1) < 300
							&& event.getY(1) > 50) {
						
							sendMessage("GAME+RIGHT_DOWN+OK+7");
						
					}

				} else if (event.getAction() == MotionEvent.ACTION_POINTER_2_UP) {
					if (event.getX(1) < 0 && event.getY(1) > 50) {
						
							sendMessage("GAME+LEFT_UP+OK+6");
						
					}

					if (event.getX(1) > 150 && event.getX(1) < 300
							&& event.getY(1) > 50) {
						
							sendMessage("GAME+RIGHT_UP+OK+8");
						
					}
				}
				return false;
			}
		});
*/
		
	}

	/*
	 * public void onclick(View v){ switch(v.getId()){ case R.id.UP:
	 * 
	 * break;
	 * 
	 * case R.id.DOWN: if(isUSLR) sendMessage("keyboard:key,Down"); else
	 * sendMessage("keyboard:key,S"); break;
	 * 
	 * case R.id.LEFT: if(isUSLR) sendMessage("keyboard:key,Left"); else
	 * sendMessage("keyboard:key,A"); break; case R.id.RIGHT: if(isUSLR)
	 * sendMessage("keyboard:key,Right"); else sendMessage("keyboard:key,D");
	 * break; }
	 * 
	 * }
	 */

	
	
/*	 @Override  
	 public void onAttachedToWindow() {  
	        
	     this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);  
	        
	     super.onAttachedToWindow();  
	 } 
	*/
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.keyboardmenu, menu);
		return true;
	}
	
	/**
	 * 捕捉菜单事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.keyboardhelp:
			help();
			return true;
		
		case R.id.keyboard_reback:
			doBack();
			return true;
			
		case R.id.keyboard_exit:
			doExit();
			return true;
		case R.id.keyboard_hand:
			dohand();
			return true;
		case R.id.keyboard_mouse:
			domouse();
			return true;
		case R.id.keyboard_pc:
			dopc();
			return true;
	
		}
		return false;
	}
	
	private void dohand()
	{
		Intent intent = new Intent(KeyBoardActivity.this, HandActivity.class);
		KeyBoardActivity.this.startActivity(intent);
		this.finish();
	}
	private void dopc()
	{
		Intent intent = new Intent(KeyBoardActivity.this, controlPC.class);
		KeyBoardActivity.this.startActivity(intent);
		this.finish();
	}
	
	private void dokeyboard()
	{
		Intent intent = new Intent(KeyBoardActivity.this, KeyBoardActivity.class);
		KeyBoardActivity.this.startActivity(intent);
		this.finish();
	}
	private void domouse()
	{
		Intent intent = new Intent(KeyBoardActivity.this, MouseControlActivity.class);
		KeyBoardActivity.this.startActivity(intent);
		this.finish();
	}
	private void help(){
		new AlertDialog.Builder(KeyBoardActivity.this).setTitle("使用帮助")
		.setMessage("本页面可进行信息的发送 \n以及模拟键盘特殊键的按键情况  \n").setIcon(R.drawable.icon)
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
	
	
	private void doBack(){
		 Intent intent = new Intent(KeyBoardActivity.this,RemoteControlActivity.class);
		 KeyBoardActivity.this.startActivity(intent);
		 this.finish();
	}
	
	
		//广播的内部类，当收到关闭事件时，调用finish方法结束activity  
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
	   	       @Override  
	   	        public void onReceive(Context context, Intent intent) {  
	   	             KeyBoardActivity.this.finish();  
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
	 
		protected void doExit() {
			new AlertDialog.Builder(this)
					.setMessage(getString(R.string.exit_message))
					.setPositiveButton(getString(R.string.confirm),
							new DialogInterface.OnClickListener() {
								public void onClick(
									DialogInterface dialoginterface, int i) {
									//showpict.destroy();
									sendMessage("EXIT+OFF+OK+OUT");
									finish();
									exitapp();
									//
								}
							})
					.setNeutralButton(getString(R.string.cancel),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0, int arg1) {
								}

							}).show();

		}
 
}
