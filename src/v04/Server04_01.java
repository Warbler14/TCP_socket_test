package v04;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import v03.DataStreamUtil;

/**
 * 바이트 스트림 테스트
 * 
 * @since 2017.09.02
 * @author insung
 *
 */
public class Server04_01 {

	public static void main(String args[]) {
		ServerSocket serverSocket = null;

		try {
			// 서버소켓을 생성하고 5000번 포트와 결합(bind) 시킨다.
			int clientSocketPort = 5000;
			serverSocket = new ServerSocket(clientSocketPort);
			System.out.println(getTime() + " 서버가 준비되었습니다." + clientSocketPort);
			
			while (true) {
				try {
					
					
					String serverIP = "127.0.0.1"; // 127.0.0.1 & localhost 본인
					System.out.println("서버에 연결중입니다. 서버 IP : " + serverIP);

					// 소켓을 생성하여 연결을 요청한다.
					int homeSocketPort = 5001;
					Socket socket1 = new Socket(serverIP, homeSocketPort);

					// 소켓의 입/출력스트림을 얻는다.
					DataStreamUtil waver1 = new DataStreamUtil(socket1);
					System.out.println(getTime() + " 홈 서버에 연결 완료 " + homeSocketPort);
					
					
					
					
					
					
					
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
					
					
					
					
					System.out.println(getTime() + " 연결요청을 기다립니다.");
					// 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
					// 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
					Socket socket2 = serverSocket.accept();

					socket2.getInetAddress();

					InetAddress clientInetAddress = socket2.getInetAddress();
					String clientHostAddress = clientInetAddress.getHostAddress();
					System.out.println(getTime() + clientInetAddress + " 로부터 연결요청이 들어왔습니다.");

					// 클라이언트의 입출력 소켓을 얻는다.
					DataStreamUtil waver2 = new DataStreamUtil(socket2);
					
					// 클라이언트 소켓으로 부터 받은 데이터를 출력한다.
					System.out.println("read data");
					
					String readData2 = waver2.receiveData();
					
					//System.out.println("read finish");
										
					System.out.println("클라이언트로부터 받은 메세지 : " + readData2 + " " + readData2.length() );
					
					
					
					
					
					//====
					

					// 홈 서버에 보낼 메시지 준비
					String sendData1 = readData2;
					System.out.println("홈 서버에 전송 할 메시지 : " + sendData1 + " " + sendData1.length() );

					// 클라이언트에 데이터를 보낸다.
					waver1.serveData( sendData1.getBytes() );
					
					System.out.println(getTime() + " 홈서버에 데이터를 전송했습니다." + clientHostAddress);
					
					
					
					// 스트림과 소켓을 달아준다.
					//System.out.println("연결을 종료합니다." + clientHostAddress);
					//waver.close();
					//socket.close();
					
					
					
					//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					
					


					// 소켓으로 부터 받은 데이터를 출력한다.
					String dataRead = waver1.receiveData();
					
					System.out.println(getTime() + " 홈 서버로부터 받은 메세지 : " + dataRead );


					// 서버로 데이터를 전송한다.
					
					waver2.serveData( dataRead.getBytes() );
					
					System.out.println(getTime() + " 클라이언트에 데이터를 전송했습니다." + clientHostAddress);
					
					
					// 스트림과 소켓을 닫는다.
					System.out.println("연결을 종료합니다.");
					waver1.close();
					waver2.close();
					socket1.close();
					socket2.close();
					
					
					
					
				} catch (ConnectException ce) {
					System.out.println("ConnectException " + ce.getMessage() ); 
				} catch (IOException ie) {
					System.out.println("IOException " + ie.getMessage() ); 
				} catch (Exception e) {
					System.out.println("Exception " + e.getMessage() ); 
				} // try - catch
			} // while
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null && !serverSocket.isClosed()) {
				try {

					if (serverSocket != null && !serverSocket.isClosed()) {

					}
					serverSocket.close();
				} catch (Exception e) {
				}

			}

		}

	}

	
	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	} // getTime

}
