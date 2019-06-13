package org.chance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by gengchao on 16/5/1.
 */
public class SocketStudy {

    static class Client{
        public static void main(String[] args) {
            PrintWriter pw=null;
            BufferedReader br=null;
            try {
                Socket socket = new Socket("127.0.0.1",10000);
                pw = new PrintWriter(socket.getOutputStream(),true);
                br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw.println("hello");
                String line = br.readLine();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    pw.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Server{
        public static void main(String[] args) {
            PrintWriter pw=null;
            BufferedReader br=null;
            try {
                ServerSocket serverSocket = new ServerSocket(10000);
                Socket socket = serverSocket.accept();
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw = new PrintWriter(System.out);
                pw.write(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
