package com.daphnis.network;

import com.daphnis.network.server.NetworkServer;

public class NetworkServerMain {

  public static void main(String[] args) {
    NetworkServer netServer = new NetworkServer();
    netServer.start();
  }

}
