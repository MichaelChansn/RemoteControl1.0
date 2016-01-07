package ks.nankai;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RemoteControlActivity extends Activity {
    EditText ipET;
    EditText socketET;
    Button button;
    Button button_getip;
    Thread udpsend;
    Thread udpget;
    Handler handler;
    TextView textout;
    
    ImageButton shubiao;
    ImageButton jianpan;
    ImageButton shoubing;
    ImageButton kongzhi;
    
    
   // Runnable broadcast_send;
   // Runnable broadcast_get;
    String message;
    String[] messages;
  //  public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
    private boolean isconn=false;
    private ProgressDialog dialog=null;
    private boolean isGettingUDPIP=false;
   
    
    
    
   
    	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       // this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//屏蔽Home键
        
        
        initViews();
        initFunctions();

        
    }
    	 
    	 private void initViews()
    	 {
    		   isconn=false;
    	        
    	        ipET = (EditText)findViewById(R.id.IpEditText);
    	        socketET = (EditText)findViewById(R.id.SocketEditText);
    	        button = (Button)findViewById(R.id.ConnectButton);
    	        button_getip=(Button)findViewById(R.id.getserip);
    	        textout=(TextView)findViewById(R.id.textView3);
    	        
    	        shubiao=(ImageButton)findViewById(R.id.imageButton_shubiao);
    	        jianpan=(ImageButton)findViewById(R.id.imageButton2_jianpan);
    	        shoubing=(ImageButton)findViewById(R.id.imageButton3_shoubing);
    	        kongzhi=(ImageButton)findViewById(R.id.imageButton4_kongzhi);
    	        
    	        ipET.setText(TcpServerApplication.ipstore);
    	        socketET.setText(TcpServerApplication.portstore);
    	        textout.setText(TcpServerApplication.textoutstore);
    	 }
    	 
    	 private void initFunctions()
    	 {
    		 handler=new Handler(){

    				/* (non-Javadoc)
    				 * @see android.os.Handler#handleMessage(android.os.Message)
    				 */
    				@Override
    				public void handleMessage(Message msg) {
    					// TODO Auto-generated method stub
    					switch (msg.what) {
    					case 0:
    						textout.setText(msg.obj.toString());
    						String[] tem=msg.obj.toString().split("\\+");
    						if(tem.length>=2)
    						{
    						
    						ipET.setText(tem[0].substring(1));
    						socketET.setText(tem[1]);
    						TcpServerApplication.ipstore=ipET.getText().toString();
    	        			TcpServerApplication.portstore=socketET.getText().toString();
    	        			TcpServerApplication.textoutstore=textout.getText().toString();
    						}
    						break;
    					case 1:
    						dialog=ProgressDialog.show(RemoteControlActivity.this, "正在连接", "请稍后...");
    						dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
    				            
    				            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
    				                // TODO Auto-generated method stub
    				                // Cancel task.
    				                if (keyCode == KeyEvent.KEYCODE_BACK) {
    				                	dialog.dismiss();
    				                	
    				                }
    				                return false;
    				            }
    				        });
    						break;
    					case 2:
    						if(dialog!=null)
    						{
    							dialog.dismiss();
    							dialog=null;
    						}
    						break;
    					case 3:
    						button.setText("断开连接电脑");
    						break;
    					case 4:
    						button.setText("手机连接电脑");
    						break;
    					case 5:
    						Toast.makeText(RemoteControlActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
    	        			textout.setText("成功连接到电脑："+textout.getText());
    						break;
    					case 6:
    						textout.setText("TCP连接到电脑出错");
    						break;

    					case 7:
    						new AlertDialog
    						 .Builder(RemoteControlActivity.this)
    						 .setIcon(android.R.drawable.stat_notify_error)
    						 .setTitle("连接错误！")
    						 .setMessage("请检查网络和IP地址是否输入错误！")
    						 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
    							
    							@Override
    							public void onClick(DialogInterface dialog, int which) {
    								// TODO Auto-generated method stub
    								dialog.dismiss();
    							
    							}
    						}).create().show();
    						break;
    					default:
    						break;
    					}
    				}
    	        	
    	        };
    	        
    	        
    	       
    	        
    	        button_getip.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					 udpget=new Thread(new Runnable() {
    		    				
    		    				@Override
    		    				public  void run() {
    		    					
    		    					
    		    					// TODO Auto-generated method stub
    		    					DatagramSocket socket = null;
    		    					try {
    		    						 socket=new DatagramSocket(7000);
    		    						 socket.setSoTimeout(1000);
    		    					} catch (SocketException e) {
    		    						// TODO Auto-generated catch block
    		    						e.printStackTrace();
    		    					}
    		    					byte data [] = new byte[1024];
    		    					DatagramPacket packet = new DatagramPacket(data,data.length);
    		    					
    		    					
    		    						try {
    		    							socket.receive(packet);
    		    						} catch (IOException e) {
    		    							// TODO Auto-generated catch block
    		    							e.printStackTrace();
    		    						}
    		    				
    		    					
    		    				  message = new String(packet.getData(),packet.getOffset(),packet.getLength());
    		    					if(message.equalsIgnoreCase("This is using Server IP"))
    		    					{
    		    						Message msg=new Message();
    		    						//packet.getAddress().toString()为什么字符串的前边会有个"\"呢？
    		    						msg.obj=packet.getAddress().toString()+"+"+"6000";//packet.getPort();
    		    						msg.what=0;
    		    						handler.sendMessage(msg);
    		    						//不能在子线程访问主线程的控件
    		    				       // ipET.setText(packet.getAddress().toString())	;
    		    				          //socketET.setText(packet.getPort());
    		    					}
    		    					
    		    					socket.close();
    		    					
    		    				
    		    					
    		    				}
    		    			})	;	
				
					
    					
    			udpsend=new Thread(new Runnable() {
    				
    				@Override
    				public void run() {
    					
    					// TODO Auto-generated method stub
    					DatagramSocket dgSocket = null;
    					try {
    						dgSocket = new DatagramSocket();
    					} catch (SocketException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    					  byte b[] = null;
    					try {
    						b = ("CON_OK+"+InetAddress.getLocalHost().getHostName()).getBytes();
    					} catch (UnknownHostException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
    					  DatagramPacket dgPacket = null;
    					try {
    						dgPacket = new DatagramPacket(b,b.length,InetAddress.getByName("255.255.255.255"),8000);
    					} catch (UnknownHostException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    					  try {
    						dgSocket.send(dgPacket);
    					} catch (IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    					  dgSocket.close();
    					
    				}
    			});
    				
    			
    			
    			
    			udpsend.start();
    			udpget.start();
    			
    				}
    				
    			});
    	        
    	        if(TcpServerApplication.isConnect && TcpServerApplication.tcpsocket!=null && TcpServerApplication.tcpsocket.isConnected())
    	        {
    	        	TcpServerApplication.isConnect=true;
    	        	button.setText("断开连接电脑");
    	        	
    	        }
    	        else
    	        {
    	        	TcpServerApplication.isConnect=false;
    	        	
    	        	TcpServerApplication.tcpsocket=null;
    	        	button.setText("连接连接电脑");
    	        }
    	        button.setOnClickListener(new OnClickListener() { 
    	        	@Override 
    	        	public void onClick(View v) { 
    	        		
    	        		if(!TcpServerApplication.isConnect)
    	        		{
    	        			
    	        			new Thread(new Runnable() {
								public void run() {
									  String ipnum = ipET.getText().toString(); 
			    	        		  int socketnum = Integer.parseInt(socketET.getText().toString());
			    	        	  
			    	        		
			    	        		try {
			    	        			TcpServerApplication.tcpsocket=new Socket();
			    	        			TcpServerApplication.tcpsocket.setKeepAlive(true);
			    	        			//tcpcon.tcpsocket.setSoTimeout(3000);
			    	        			
			    	        			TcpServerApplication.disaddress= InetAddress.getByName(ipnum);  
			    	        			TcpServerApplication.dstPort=socketnum;
			    	        			
			    	        			 
			    	        			TcpServerApplication.dissocketaddress=new InetSocketAddress(TcpServerApplication.disaddress, TcpServerApplication.dstPort);
			    	        			//tcpcon.tcpsocket.connect(tcpcon.dissocketaddress);
			    	        			handler.sendEmptyMessage(1);
			    	        			TcpServerApplication.tcpsocket.connect(TcpServerApplication.dissocketaddress, 3000);
			    	        			if(TcpServerApplication.tcpsocket.isConnected())
			    	        			{
			    	        				TcpServerApplication.startGetPictureThread();//启动接收线程
			    	        				//button.setText("断开连接电脑");
			    	        				handler.sendEmptyMessage(3);
			    	        				handler.sendEmptyMessage(2);
			    	        				TcpServerApplication.isConnect=true;
			    	        				//tcpcon.tcpsocket.setReceiveBufferSize(1024*50);
			    	        				  isconn=true;
			    	        				  TcpServerApplication.outstream=TcpServerApplication.tcpsocket.getOutputStream();  
			    	        		          TcpServerApplication.instream=TcpServerApplication.tcpsocket.getInputStream();  
			    	        		          
			    	        		          byte buffer[] = ("HOSTNAME+"+InetAddress.getLocalHost().getHostName()+"\r\n").getBytes("UTF8");
			    	        		     
			    	        		          TcpServerApplication.outstream.write(buffer, 0, buffer.length);
			    	        		          TcpServerApplication.outstream.flush();
			    	        		        
			    	        		            
			    	        			
			    	        		         handler.sendEmptyMessage(5); 
			    	        			//Toast.makeText(RemoteControlActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
			    	        			//textout.setText("成功连接到电脑："+textout.getText());
			    	        			
			    	        			  TcpServerApplication.textoutstore=textout.getText().toString();
			    	        			}
			    	        		else
			    	        			{
			    	        			handler.sendEmptyMessage(2);
			    	        			handler.sendEmptyMessage(7);
			    	        			if(TcpServerApplication.tcpsocket!=null)
			    	        			{
			    	        			  TcpServerApplication.tcpsocket.close();
			    	        			  TcpServerApplication.tcpsocket=null;
			    	        			}
			    	        			TcpServerApplication.stopGetpictureThread();
			    	        			 TcpServerApplication.isConnect=false;
			    	        			 isconn=false;
			    	        			 
			    	        			 handler.sendEmptyMessage(4);
			    	        			 handler.sendEmptyMessage(6);
			    	        			 //   button.setText("手机连接电脑");
			    	        			//	textout.setText("TCP连接到电脑出错");
			    	        				
			    	        			}
			    	        		} catch (Exception e) {
			    	        			// TODO Auto-generated catch block
			    	        			//button.setText("手机连接电脑");
			    	        			handler.sendEmptyMessage(4);
			    	        			handler.sendEmptyMessage(2);
			    	        			handler.sendEmptyMessage(7);
			    	        			e.printStackTrace();
			    	        			TcpServerApplication.stopGetpictureThread();
			    	        			TcpServerApplication.tcpsocket=null;
			    	        			isconn=false;
			    	        			TcpServerApplication.isConnect=false;
			    	        		}
			    	        		
								}
							}).start();
    	        		  
    	        		}
    	        		else
    	        		{
    	        			button.setText("手机连接电脑");
    	        			
    	        			if(TcpServerApplication.tcpsocket!=null)
    	        			{
    	        			 sendMessage("EXIT+OFF+OK+OUT");
    	        			try {
    	        				
    	        				TcpServerApplication.stopGetpictureThread();
    	        				
    	        				
    							TcpServerApplication.tcpsocket.close();
    						} catch (IOException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    	        			TcpServerApplication.tcpsocket=null;
    	        			}
    	        			
    	        			TcpServerApplication.isConnect=false;
    	        			isconn=false;
    	        		}
    	        	
    	        	
    	        	
    	        	
    	        	
    	        	} 
    	        }); 
    	        
    	  
    	        shubiao.setOnTouchListener(new View.OnTouchListener() {
    				
    				@Override
    				public boolean onTouch(View v, MotionEvent event) {
    					// TODO Auto-generated method stub
    					if(event.getAction() == MotionEvent.ACTION_DOWN)
    					{
    						shubiao.setImageResource(R.drawable.shubiaoanxia);
    						TcpServerApplication.Vibrate(RemoteControlActivity.this, 100);
    					}
    					if(event.getAction() == MotionEvent.ACTION_UP)
    					{
    						shubiao.setImageResource(R.drawable.shubiao);
    						//加上跳转代码
    						Intent intent = new Intent(RemoteControlActivity.this,MouseControlActivity.class);//ControlActivity.class);
    	        			RemoteControlActivity.this.startActivity(intent);
    	        			RemoteControlActivity.this.finish();
    					}
    					return false;
    				}
    			});
    	        
    	        jianpan.setOnTouchListener(new View.OnTouchListener() {
    				
    				@Override
    				public boolean onTouch(View v, MotionEvent event) {
    					// TODO Auto-generated method stub
    					if(event.getAction() == MotionEvent.ACTION_DOWN)
    					{
    						jianpan.setImageResource(R.drawable.jianpananxia);
    						TcpServerApplication.Vibrate(RemoteControlActivity.this, 100);
    					}
    					if(event.getAction() == MotionEvent.ACTION_UP)
    					{
    						jianpan.setImageResource(R.drawable.jianpan);
    						//加上跳转代码
    						Intent intent = new Intent(RemoteControlActivity.this,KeyBoardActivity.class);//ControlActivity.class);
    	        			RemoteControlActivity.this.startActivity(intent);
    	        			RemoteControlActivity.this.finish();
    					}
    					return false;
    				}
    			});
    	        
    	        shoubing.setOnTouchListener(new View.OnTouchListener() {
    				
    	  			@Override
    	  			public boolean onTouch(View v, MotionEvent event) {
    	  				// TODO Auto-generated method stub
    	  				if(event.getAction() == MotionEvent.ACTION_DOWN)
    	  				{
    	  					shoubing.setImageResource(R.drawable.shoubinganxia);
    	  					TcpServerApplication.Vibrate(RemoteControlActivity.this, 100);
    	  				}
    	  				if(event.getAction() == MotionEvent.ACTION_UP)
    	  				{
    	  					shoubing.setImageResource(R.drawable.shoubing);
    	  					//加上跳转代码
    	  					Intent intent = new Intent(RemoteControlActivity.this,HandActivity.class);//ControlActivity.class);
    	        			RemoteControlActivity.this.startActivity(intent);
    	        			RemoteControlActivity.this.finish();
    	  				}
    	  				return false;
    	  			}
    	  		});
    	          
    		        
    		kongzhi.setOnTouchListener(new View.OnTouchListener() {
    					
    		  			@Override
    		  			public boolean onTouch(View v, MotionEvent event) {
    		  				// TODO Auto-generated method stub
    		  				if(event.getAction() == MotionEvent.ACTION_DOWN)
    		  				{
    		  					kongzhi.setImageResource(R.drawable.kongzhianxia);
    		  					TcpServerApplication.Vibrate(RemoteControlActivity.this, 100);
    		  				}
    		  				if(event.getAction() == MotionEvent.ACTION_UP)
    		  				{
    		  					kongzhi.setImageResource(R.drawable.kongzhi);
    		  					//加上跳转代码
    		  					Intent intent = new Intent(RemoteControlActivity.this,controlPC.class);//ControlActivity.class);
    		        			RemoteControlActivity.this.startActivity(intent);
    		        			RemoteControlActivity.this.finish();
    		  					
    		  				}
    		  				return false;
    		  			}
    		  		});
    	        

    	 }
    	 
    	 
    	 
    	 
    		private void sendMessage(String str) {
    			try {
    				  if(TcpServerApplication.isConnect&& TcpServerApplication.tcpsocket.isConnected())
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
    		
    	/* 
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
    	   
    	   break;
    	  case KeyEvent.KEYCODE_HOME:
    	   
    	   break;
    	  }
    	  return false;
    	 }   
*/
    	 
    	 
    	 
    		@Override
    		public boolean onCreateOptionsMenu(Menu menu) {
    			super.onCreateOptionsMenu(menu);
    			getMenuInflater().inflate(R.menu.menu, menu);
    			return true;
    		}

    		@Override
			public void onBackPressed() {
				// TODO Auto-generated method stub
    			exitapp();
				super.onBackPressed();
			}
			/**
    		 * 捕捉菜单事件
    		 */
    		@Override
    		public boolean onOptionsItemSelected(MenuItem item) {
    			switch (item.getItemId()) {
    			case R.id.main_about:
    				about();
    				return true;
    			
    			case R.id.main_exit:
    				doExit();
    				return true;
    			}
    			return false;
    		}
    
    		/**
    		 * 显示关于我们
    		 */
    		public void about() {
    			new AlertDialog.Builder(RemoteControlActivity.this)
    					.setTitle("关于我们")
    					.setMessage("欢迎使用Android控制系统  \n作者：南开大学 Michael")
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
    		
    		//广播的内部类，当收到关闭事件时，调用finish方法结束activity  
  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
    	   	       @Override  
    	   public void onReceive(Context context, Intent intent) {  
    	   	             RemoteControlActivity.this.finish();  
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

		
        	  
        		// 退出程序
      		protected void doExit() {
      			new AlertDialog.Builder(this)
      					.setMessage(getString(R.string.exit_message))
      					.setPositiveButton(getString(R.string.confirm),
      							new DialogInterface.OnClickListener() {
      								public void onClick(
      									DialogInterface dialoginterface, int i) {
      									//showpict.destroy();
      									//showpict.stop();
      									if(isconn)
      									{
      									sendMessage("EXIT+OFF+OK+OUT");
      									}
      									//finish();
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
    			 isconn=false;
    			 
    			 TcpServerApplication.isConnect=false;
    		 }
    		 Intent intent = new Intent();
    		 intent.setAction("GlobalVarable.EXIT_ACTION"); // 退出动作
    		 this.sendBroadcast(intent);// 发送广播
    		 super.finish();
    		 RemoteControlActivity.this.finish();
    		 //退出后台线程,以及销毁静态变量
    		 System.exit(0);
    	 }
    	 
    	 
}