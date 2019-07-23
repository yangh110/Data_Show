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
    //����һ�����ڷ��͵�packet����
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    private DatagramPacket outPacket;
    
    
    
    Socket s1 = null;
    BufferedReader br = null;
    BufferedReader br1 = null;
    SimpleDateFormat df = null;

    public ServerThread(Socket s1) throws IOException {
        
        this.s1 = s1;
        //���ݶ����ӳɹ���,׼�����͵�Android��
        ds = new DatagramSocket(PORT);
        //���ӳɹ�,��ӡ
        System.out.println("conn success\n");
        //��������
        br1 = new BufferedReader(new InputStreamReader(s1.getInputStream(),"utf-8"));

       
    }

    public void run()
    {
        try 
        {
            
            String content = null;
            
	        while((content = readForm()) != null)
	        {
	        	//���յ��İ�,���ڸ���ip��ַ
	        	ds.receive(inPacket);
	            
	            //��ӡ��Ϣ,���ڼ�������Ϣ�Ƿ�������
	            System.out.println("\n" + content);
	            //�����ݶ˵���Ϣת��Ϊbyte����        
	            byte[] sendData = (content + "\n").getBytes("utf-8");
	            //��䷢�Ͱ�����Ϣ        
	            outPacket = new DatagramPacket(sendData , sendData.length , inPacket.getSocketAddress());
	            //����!!
	            ds.send(outPacket);
	            //ִ�гɹ�
	            System.out.println("send success\n");
	            //���ַ����ÿ�
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

    //������,�Ƿ���ɹ�,������
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