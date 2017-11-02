import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Server extends JFrame implements ActionListener{
	private JPanel panel1;
	private JPanel panel2;
	private JButton button;
	private JLabel label;
	
	public Server(){
		this.setBounds(100,50,350,100);
		this.setTitle("O~YEE点餐系统服务器端");
		panel1 = new JPanel();
		panel2 = new JPanel();
		button = new JButton("开启服务器");
		button.addActionListener(this);
		label = new JLabel("欢迎使用O~YEE点餐系统");
		panel1.add(label);
		panel2.add(button);
		this.add(BorderLayout.NORTH,panel1);
		this.add(BorderLayout.SOUTH,panel2);
		this.setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		this.setVisible(true);	
	}
	
	public static void main(String[] args){
		new Server();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button){
			this.setVisible(false);
			new MainWindow();
		}
		
	}
	
	class MainWindow extends JFrame{
		private JPanel PanelLabel;
		private JPanel PanelText;
		private JPanel PanelButton;
		private JLabel label;
		private JButton closeButton;
		private SocketServer server;
		public MainWindow(){
			this.setTitle("O~YEE点餐系统");
			this.setBounds(500,200,600,300);
			PanelLabel = new JPanel();
			PanelText = new JPanel();
			PanelButton = new JPanel();
			closeButton = new JButton("关闭服务器");
			server = new SocketServer();
			label = new JLabel("订单信息");
			
			closeButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					closeFrame();
				}});
			PanelLabel.add(label);
			PanelButton.add(closeButton);
			PanelText.add(server.getTextArea());
			this.add(BorderLayout.NORTH,PanelLabel);
			this.add(BorderLayout.CENTER,PanelText);
			this.add(BorderLayout.SOUTH,PanelButton);
			this.setAlwaysOnTop(true);
			this.setVisible(true);
			
			
			
		}
		
		private void closeFrame(){
			server.closeServer();    //强制关闭socket，但输入输出流还未关闭，会抛出错误信息，不影响程序使用
			this.setVisible(false);
		}
	}
	
	

}
