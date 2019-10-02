package MineSweeper;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketController {

  public static void main(String args[])throws Exception {

    try {
      Socket socket = new Socket("localhost",8001);

      //获取输出流，向服务器端发送信息
      OutputStream os=socket.getOutputStream();//字节输出流
      PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
      pw.write("我是Java服务器");
      pw.flush();
      socket.shutdownOutput();//关闭输出流

      InputStream is=socket.getInputStream();
      BufferedReader in = new BufferedReader(new InputStreamReader(is));
      String info=null;
      while((info=in.readLine())!=null){
        System.out.println("我是客户端，Python服务器说："+info);
      }
      is.close();
      in.close();
      socket.close();
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
