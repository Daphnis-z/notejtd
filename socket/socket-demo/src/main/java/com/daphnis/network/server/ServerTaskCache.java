package com.daphnis.network.server;

import com.google.common.collect.Maps;
import java.util.Map;

public class ServerTaskCache {

  public static Map<String, ServerTask> clientAddress2ServerTask = Maps.newConcurrentMap();
  
}
