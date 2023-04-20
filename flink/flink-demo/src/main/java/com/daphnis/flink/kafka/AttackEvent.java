package com.daphnis.flink.kafka;

/**
 * @author daphnis
 * @title xx
 * @date 2023-04-20 14:11
 */
public class AttackEvent {
    private String eventName;
    private String eventTime;
    private String startTime;
    private String endTime;
    private String hostIp;
    private int count;

    public AttackEvent(String eventName, String eventTime, String hostIp) {
        this.eventName = eventName;
        this.startTime = eventTime;
        this.endTime = eventTime;
        this.hostIp = hostIp;
        this.count = 1;
    }

    public String getHostIp() {
        return hostIp;
    }

    public String getEventName() {
        return eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getCount() {
        return count;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
