package com.kevinyin.lnetty.udp;

import java.net.InetSocketAddress;

/**
 * Created by kevinyin on 2017/7/14.
 */
public class LogEvent {
    public static final byte SEPARATOR = (byte)':';

    private final InetSocketAddress source;
    private final String logFile;
    private final String msg;
    private final long received;

    public LogEvent(String logFile, String msg) {
        this(null,logFile,msg,-1);
    }

    public LogEvent(InetSocketAddress source, String logFile, String msg, long received) {
        this.source = source;
        this.logFile = logFile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceived() {
        return received;
    }
}
