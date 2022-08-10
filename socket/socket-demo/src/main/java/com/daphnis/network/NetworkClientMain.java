package com.daphnis.network;

import com.daphnis.network.client.NetworkClient;

public class NetworkClientMain {

  public static void main(String[] args) {
    NetworkClient netClient = new NetworkClient();
    netClient.start();
  }

}
