package com.example.demos.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class GatewaySocket extends Thread {
	private ServerSocket m_socket=null;
	private int port=28021;
	
	public GatewaySocket() {
		
	}
	@Override
	public void run() {
		try{
			m_socket=new ServerSocket(port);
			System.out.println("server ready ok.");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		while(true){
			try{
				Socket s=m_socket.accept();
				System.out.println("client entry.");
				new ResponsedThread(s).start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		GatewaySocket gs=new GatewaySocket();
		gs.start();
	}
	class ResponsedThread extends Thread{
		private Socket socket=null;
		private BufferedReader read=null;
		private BufferedWriter write=null;
		
		public ResponsedThread(Socket p_socket) {
			socket=p_socket;
		}
		@Override
		public void run() {
			try{
				read=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				write=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				//测试定时发消息给客户端
				new Thread(){
					public void run() {
						while(true) {
							try {
								write.write(new Random().nextInt(100)+"\r\n");
								write.flush();
								Thread.sleep(3000);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
				}.start();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			while(true){
				try{
					String recived=null;
					while((recived=read.readLine())!=null){
						System.out.println("[received message from client]:\r\n"+recived);
						write.write(recived+"\r\n");
						write.flush();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
