package ks.nankai;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;

public class TcpServerApplication extends Application {
	public static Socket tcpsocket;//new Socket();
	public static InetAddress disaddress;
	public static int dstPort;
	public static InetSocketAddress dissocketaddress;
	public static OutputStream outstream;  
    public static InputStream instream; 
    public static String ipstore="192.168.1.100";
    public static String portstore="8888";
    public static String textoutstore="在这个TEXTVIEW上显示UDP和TCP的数据信息";
    public static String controlpcinfo="当前还没有设置定时关机";
    public static boolean issure=true;
    public static boolean isConnect=false;
    //public static String test="1111111";
   private static boolean isGetPictureThreadRunning=false;
   public static boolean isGetPictureStart=false;
   public static Bitmap bitmapCache=null;
   public static Thread getPictureThead=null;
    
    public static void Vibrate(final Activity activity, long milliseconds) {
    	  Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
    	  vib.vibrate(milliseconds);
    	 }
   
    public static void startGetPictureThread()
    {
    	getPictureThead=new Thread(new getPictureThread());
    	isGetPictureThreadRunning=true;
    	isGetPictureStart=false;
    	getPictureThead.setDaemon(true);
    	getPictureThead.start();
    	
    	
    }
    
    public static void stopGetpictureThread()
    {
    	isGetPictureThreadRunning=false;
    	isGetPictureStart=false;
    	if(getPictureThead!=null)
    	{
    	getPictureThead.interrupt();
    	}
    }
    /***************************************************
	 * 一下的两个方法是把小端整数转化成大端整数，因为java
	 * 的readint方法从网络上读取的整数是 大端格式的，
	 * 一般和java自身带的writeint匹配使用。可以同时大小端转换，
	 * 但是c#中write写入的和read的都是小端的，所以从C#接收数据
	 * 必须人为的转化成大端格式
	 * 大端格式才是java使用的格式
	 * */
	
	

	   public static int small2big(int v) throws IOException 
	   {
		   byte[] work=new byte[4];
		   int ret;

          work[0] = (byte) v;
		    work[1] = (byte) (v >> 8);
		     work[2] = (byte) (v >> 16);
		      work[3] = (byte) (v >> 24);  
		     ret=byteArrayToInt(work);
		      return ret;
		           }
	
	   public static int byteArrayToInt(byte[] b) {
	        int value = 0;
	        for (int i = 0; i < 4; i++) {
	            int shift = (4 - 1 - i) * 8;
	            value += (b[i] & 0x000000FF) << shift;
	        }
	        return value;
	 }

/******************************************************/
	   
	   
	  public static synchronized void setBitmapCache(Bitmap bmp)
	  {
		  bitmapCache=bmp;
	  }
	  public static synchronized Bitmap getBitmapCache()
	  {
		  return bitmapCache;
	  }
   static class getPictureThread implements Runnable
    {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			DataInputStream dataInput = new DataInputStream(  
                    TcpServerApplication.instream); 
			while(isGetPictureThreadRunning)
			{
				if(isGetPictureStart)
				{
					int size = 0;
					try {
						size = dataInput.readInt();
						size=small2big(size);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
                 byte[] data = new byte[size];  
                 // dataInput.readFully(data);  
                 int len = 0;  
                 while (len < size) {  
                     try {
							len += dataInput.read(data, len, size - len);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
                 }  

                 setBitmapCache(BitmapFactory.decodeByteArray(data, 0,  data.length)); 
					
				}
			}
		}
    	
    }
    
    
    
}
