package com.kevinyin.lnetty.discard.bean;

import java.util.Date;

/**
 * Created by kevinyin on 2017/7/9.
 */
public class UnixTime {
    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
        System.out.println(new Date(value));
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((getValue() - 2208988800L) * 1000L).toString();
    }
}