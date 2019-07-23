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
        ds = new DatagramSocket(PORT);
        
        
        System.out.println("conn success\n");
        //br = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
        br1 = new BufferedReader(new InputStreamReader(s1.getInputStream(),"utf-8"));


        //df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void run()
    {
        try 
        {
            
            String content = null;
            
            
            

	        while((content = readForm()) != null)
	        {
	        	
	        	ds.receive(inPacket);
	            //System.out.println(inBuff == inPacket.getData());
	            
	            System.out.println("\n" + content);
	                    
	            byte[] sendData = (content + "\n").getBytes("utf-8");
	                    
	            outPacket = new DatagramPacket(sendData , sendData.length , inPacket.getSocketAddress());
	
	            ds.send(outPacket);
	            System.out.println("send success\n");
	            content = null;
	               
	
	         }     
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //鐠囪褰囬弫鐗堝祦濞堬拷
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