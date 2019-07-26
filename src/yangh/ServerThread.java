package yangh;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread implements Runnable{

    DatagramSocket ds = null;
    DatagramSocket ds_d = null;
    private static final int PORT = 30000;
    private static final int PORT_DATA = 12500;
    private static final int DATA_LEN = 4096;
    byte[] inBuff = new byte[DATA_LEN];
    byte[] inBuff_data = new byte[DATA_LEN];
    //定义一个用于发送的packet对象
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    private DatagramPacket outPacket;
    //数据段传输接收对象
    private DatagramPacket inPacket_data = new DatagramPacket(inBuff_data , inBuff.length);
    private DatagramPacket outPacket_data;
    
    


    public ServerThread() throws IOException {
       
       
    }

    public void run()
    {
    	while(true)
        {
    		try 
    		{
            
	            String content = null;
	            
	            //数据端连接成功后,准备发送到Android端
	            ds = new DatagramSocket(PORT);
	            //创建读端
	            ds_d = new DatagramSocket(PORT_DATA);
	            
	            
	            
		        while(true)
		        {
		        	//先接收数据端的数据包
		        	try {
		        		ds_d.setSoTimeout(1000);
		        		ds_d.receive(inPacket_data);
		        	}
		        	catch(SocketException se)
		        	{
		        		se.printStackTrace();
		        		System.out.println("\nthe first socket\n");
		        		break;
		        	}
		        	//数据提取并转为字符串
		        	content = inPacket_data.getData().toString();
		        	
		        	//接收到的包,用于更改ip地址
		        	try {
		        		//超时
		        		ds.setSoTimeout(1000);
		        		ds.receive(inPacket);
		        	}
		        	catch (SocketException se){
		        		se.printStackTrace();
		        		System.out.println("\nthe second socket\n");
		        		break;
		        	}
		            
		            //打印信息,用于检测接收信息是否又问题
		            System.out.println("\n" + content);
		            //将数据端的信息转化为byte数组        
		            byte[] sendData = (content + "\n").getBytes("utf-8");
		            //填充发送包的信息        
		            outPacket = new DatagramPacket(sendData , sendData.length , inPacket.getSocketAddress());
		            //发射!!
		            ds.send(outPacket);
		            //执行成功
		            System.out.println("send success\n");
		            //将字符串置空
		            content = null;
		
		         }         
            
	        } 
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	        	//关闭资源
	        	System.out.println("close the socket\n");
	        	ds.close();
	        	ds_d.close();
	        }
        }
    	//外层循环,死循环,人为的控制系统杀死进程
    }

    
    
}