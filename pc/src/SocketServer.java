import java.awt.TextArea;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import javax.naming.Context;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class SocketServer {
	private Socket socket[];
	private ServerSocket serverSocket;
	//private InputStream inputStream;
	private int max = 10;  //可连接客户端的最大数目
	private int i = 0;
	private int temp;
	private TextArea textArea;
	private String mes;
	private Thread thread;
	
	public SocketServer(){
		try {
			serverSocket = new ServerSocket(15810);  //初始化ServerSocket，端口号8080
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("can't initate ServerSocket");
			JOptionPane.showMessageDialog(null, "连接失败!\r\n请释放电脑端口：15810");
			return;
		}
		
		socket = new Socket[max];
		textArea = new TextArea("等待买家订单\r\n",70,70);
		textArea.setRows(10);
	
		
		System.out.println("waitting for connect--------");
		
//		
		thread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while((socket[i]=serverSocket.accept())!=null){  //接受客户端连接请求
						System.out.println("Some one is Connected");
						temp = i;
						i++;
						Thread th = new Thread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Socket sk = socket[temp];
								System.out.println("accept:" + sk.getInetAddress().getHostAddress());
								textArea.append("买家信息:"+ sk.getInetAddress().getHostAddress()+"\r\n");
								
								try {
									BufferedReader br = new BufferedReader(new InputStreamReader(sk.getInputStream(),"UTF-8"));
									String info = null;
									while((info = br.readLine()) != null){  //节后客户端发的订餐信息
										System.out.println(info);
										textArea.append(info+"\r\n");
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									try {
										sk.close();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								
								
							}});
						th.start();
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
				
			}}); 
		thread.start();
	}
	
	public TextArea getTextArea(){
		return textArea;
	}
	
	public void closeServer(){
		try {
			thread.stop();
			for(int j=0;j<max;j++){
				if(socket[j] != null){
					socket[j].close();
				}
			}
			serverSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
