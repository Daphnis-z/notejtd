package com.daphnis.network.server;

import java.util.List;

public class WriteTask {

  private String taskName;
  private List<String> messages;

  public WriteTask(String taskName, List<String> messages) {
    this.taskName = taskName;
    this.messages = messages;
  }

  public String getTaskName() {
    return taskName;
  }

  public List<String> getMessages() {
    return messages;
  }

  @Override
  public String toString() {
    return String.format("[task name: %s,messages count: %s]", taskName, messages.size());
  }
}
