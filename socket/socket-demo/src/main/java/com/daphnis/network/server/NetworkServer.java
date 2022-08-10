package com.daphnis.network.server;

import com.daphnis.network.util.ConfigUtil;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkServer {

  private static Logger LOG = LoggerFactory.getLogger(NetworkServer.class);

  public NetworkServer() {
  }

  /**
   * 启动服务端
   */
  public void start() {
    LOG.info("socket server is start ..");

    ExecutorService executorService = Executors.newCachedThreadPool();

    int connectionCount = 0;
    try (ServerSocket serverSocket = new ServerSocket(ConfigUtil.getServerPort())) {
      while (true) {
        LOG.info("socket server listen on port: " + serverSocket.getLocalPort());

        Socket socket = serverSocket.accept();
        ++connectionCount;

        String clientAddress = socket.getInetAddress().getHostAddress();
        LOG.info("receive a connect request from " + socket.getRemoteSocketAddress());

        ServerTask serverTask = new ServerTask(socket);
        executorService.submit(serverTask);

        ServerTaskCache.clientAddress2ServerTask.put(clientAddress, serverTask);
        LOG.info("receive client connection count: " + connectionCount);
      }
    } catch (Exception e) {
      LOG.error("socket server error !!", e);
    }
  }

}




