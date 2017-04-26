package com.fisher.bostoudmp.core.task;


import com.fisher.bostoudmp.core.bean.BosToUdmpProperties;
import com.fisher.bostoudmp.tools.BosToUdmpTools;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fisher on 17-4-26.
 */
public class TaskListenBreak implements Runnable {
    private static Logger logger = Logger.getLogger(TaskListenBreak.class);
    private String name = "";

    public TaskListenBreak(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.name);
        ServerSocket server = null;
        try {
            server = new ServerSocket(BosToUdmpProperties.getPORT());
            while (true) {
                Socket socket = server.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = br.readLine();
                if (br != null) {
                    br.close();
                }
                if (str != null) {
                    if (str.equals("STOP")) {
                        BosToUdmpTools.setGoodBye(false);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    logger.info(">>>>>>>>>>>>>>>>>>>>>close all the thing !");
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
