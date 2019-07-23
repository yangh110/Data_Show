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
    private static final int PORT = 30000;
    private static final int DATA_LEN = 4096;
    byte[] inBuff = new byte[DATA_LEN];
    //定义一个用于发送的packet对象
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    private DatagramPacket outPacket;
    
    
    
    Socket s1 = null;
    BufferedReader br = null;
    BufferedReader br1 = null;
    SimpleDateFormat df = null;

    public ServerThread(Socket s1) throws IOException {
        
        this.s1 = s1;
        //数据端连接成功后,准备发送到Android端
        ds = new DatagramSocket(PORT);
        //连接成功,打印
        System.out.println("conn success\n");
        //创建读端
        br1 = new BufferedReader(new InputStreamReader(s1.getInputStream(),"utf-8"));

       
    }

    public void run()
    {
        try 
        {
            
            String content = null;
            
	        while((content = readForm()) != null)
	        {
	        	//接收到的包,用于更改ip地址
	        	ds.receive(inPacket);
	            
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
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
        	ds.close();
        }
    }

    //读方法,是否读成功,长连接
    private String readForm()
    {
        try {
            return br1.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}