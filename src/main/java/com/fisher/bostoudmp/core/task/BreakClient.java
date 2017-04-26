package com.fisher.bostoudmp.core.task;

import com.fisher.bostoudmp.core.bean.BosToUdmpProperties;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by fisher on 17-4-26.
 */
public class BreakClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", BosToUdmpProperties.getPORT());
            PrintWriter pw=new PrintWriter(socket.getOutputStream());
            pw.write("STOP");
            pw.flush();
            socket.shutdownOutput();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
