package org.chance;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by gengchao on 16/4/30.
 */
public class IOStudy {
    public static void main(String[] args) {

//        Properties properties = System.getProperties();
//        properties.list(System.out);


//        File file = new File(System.getProperty("user.home")+"/tmp/file.txt");
//        File parent = file.getParentFile();
//        InputStream is = null;
//        try {
//            if(!parent.exists()){
//                parent.mkdirs();
//            }
//            if(!file.exists()) file.createNewFile();
//            System.out.println("该分区大小" + file.getTotalSpace() / (1024 * 1024 * 1024) + "G");
//            System.out.println(file.getName());
//            System.out.println(file.getParent());
//            System.out.println(file.length());
//
//            //读文件
//            int count = 0;
//
//            is = new FileInputStream(file);
//            while(is.read()!=-1){
//                count ++ ;
//            }
//            System.out.println("length:"+count+"byte");
//
//            System.out.println(Boolean.class);
//            System.out.println(boolean.class);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(is!=null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }


        /***/
        BufferedReader br=null;
        PrintWriter pw=null;
        br=new BufferedReader(new InputStreamReader(System.in));
        pw=new PrintWriter(System.out);
        while(true){
            try {
                String s=br.readLine();
                pw.println(s);
                pw.flush();
                //System.out.println(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static private class ClientA{
        public static void main(String[] args) {
            String filePath=System.getProperty("user.home")+"/tmp/file.txt";
            System.out.println("filePath = " + filePath);
            try(
//                  FileInputStream fis=new FileInputStream(filePath);
//                  BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    FileReader reader = new FileReader(filePath);

            ){
                /**使用FileInputStream*/
                /**含有中文字符的就会乱码*/
//                System.out.println(fis.available());
//                int result;
//                while((result = fis.read())!=-1 ){
//                    System.out.println(result + ">>>>" + (char) result);
//                }

                /**使用BufferedReader*/
//                String line ;
//                while((line = br.readLine()) != null){
//                    System.out.println(line);
//                }

                /***/
                char c[] = new char[8];
                while((reader.read(c)!=-1)){
                        System.out.println(c);
                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /**写Study*/
        static private class CleintB{
            public static void main(String[] args) {

                String filePath=System.getProperty("user.home")+"/tmp/file.txt";
                System.out.println("filePath = " + filePath);


                try(
                        FileOutputStream fos = new FileOutputStream(filePath,true);
                        PrintStream ps = new PrintStream(fos);
                ){
                    ps.append("ABC\n");
                    ps.println("CCCC");
                }catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }


        static private class TimeServer{
            public static void main(String[] args) throws IOException {
                int port = 8080;
                ServerSocket server = null;
                try {
                    server = new ServerSocket(port);
                    System.out.println("TimerServer.main>>>the time server is start in port:"+port);
                    Socket socket = null;
                    while(true){
                        socket = server.accept();
                        new Thread(new TimeServerHandler(socket)).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(server !=null ){
                        System.out.println("TimerServer.main>>> the time server close");
                        server.close();
                        server =null;
                    }
                }
            }
        }

        static private class TimeServerHandler implements Runnable {

            private Socket socket;

            TimeServerHandler(Socket socket){
                this.socket = socket;
            }

            @Override
            public void run() {
                BufferedReader in = null;
                PrintWriter out = null;
                try {
                    in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    out = new PrintWriter(this.socket.getOutputStream(),true);
                    String currentTime = null;
                    String body = null;
                    while(true){
                        body = in.readLine();
                        if(body == null) break;
                        System.out.println("TimeServerHandler.run>>> the time server receive order:"+body);
                        currentTime = "query time order".equalsIgnoreCase(body) ?
                                new java.util.Date(System.currentTimeMillis()).toString():"Bad order";
                        out.println(currentTime);
                    }
                } catch (IOException e) {
                    if(in != null) {
                        try {
                            in.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if(out !=null){
                        out.close();
                        out = null;
                    }
                    if(this.socket != null ){
                        try {
                            this.socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        this.socket = null;
                    }
                    e.printStackTrace();
                }
            }
        }

        static private class TimeClient {
            public static void main(String[] args) throws IOException {
                Socket socket = null;
                BufferedReader in = null;
                PrintWriter out =null;
                try{
                    socket = new Socket("127.0.0.1",8080);
                    in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("query time order");
                    System.out.println("send order 2 server succeed.");
                    String resp = in .readLine();
                    System.out.println("Now is :"+resp);
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if(out != null){
                        out.close();
                        out = null;
                    }
                    if(in != null){
                        in.close();
                        in = null;
                    }
                    if(socket != null ){
                        socket.close();
                        socket = null;
                    }

                }
            }
        }
    }
}
