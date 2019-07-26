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
    //����һ�����ڷ��͵�packet����
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    private DatagramPacket outPacket;
    //���ݶδ�����ն���
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
	            
	            //���ݶ����ӳɹ���,׼�����͵�Android��
	            ds = new DatagramSocket(PORT);
	            //��������
	            ds_d = new DatagramSocket(PORT_DATA);
	            
	            
	            
		        while(true)
		        {
		        	//�Ƚ������ݶ˵����ݰ�
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
		        	//������ȡ��תΪ�ַ���
		        	content = inPacket_data.getData().toString();
		        	
		        	//���յ��İ�,���ڸ���ip��ַ
		        	try {
		        		//��ʱ
		        		ds.setSoTimeout(1000);
		        		ds.receive(inPacket);
		        	}
		        	catch (SocketException se){
		        		se.printStackTrace();
		        		System.out.println("\nthe second socket\n");
		        		break;
		        	}
		            
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
            
	        } 
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	        	//�ر���Դ
	        	System.out.println("close the socket\n");
	        	ds.close();
	        	ds_d.close();
	        }
        }
    	//���ѭ��,��ѭ��,��Ϊ�Ŀ���ϵͳɱ������
    }

    
    
}