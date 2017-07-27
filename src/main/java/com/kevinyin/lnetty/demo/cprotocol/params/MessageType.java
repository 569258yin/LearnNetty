package com.kevinyin.lnetty.demo.cprotocol.params;

/**
 * Created by kevinyin on 2017/7/27.
 */
public enum  MessageType {
    /**
     * 业务请求 0
     * 业务相应 1
     * 业务ONE WAY（即使请求又是相应） 2
     * 握手请求消息 3
     * 握手应答消息 4
     * 心跳请求消息 5
     * 心跳应答消息 6
     */

//    public static final int LOGIN_REQ = 3;

//    public static final int LOGIN_RESP = 4;

    LOGIN_REQ((byte)3),
    LOGIN_RESP((byte)4),
    HEARTBEAT_REQ((byte) 5),
    HEARTBEAT_RESP((byte) 6);


    private byte value;

    MessageType(byte valye) {
        this.value = valye;
    }

    public byte Value() {
        return value;
    }
}
