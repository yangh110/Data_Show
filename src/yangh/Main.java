package yangh;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException{
	// write your code here

            //Server
            ServerSocket ser = new ServerSocket(12500);
            System.out.println("entry the programe\n");
            while (true) {
            	
                Socket s1 = ser.accept();
                System.out.println("server socket link success\n");
                new Thread(new ServerThread(s1)).start();
            }
          
    }
}