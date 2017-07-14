package com.kevinyin.lnetty.codec;

import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectDecoder;

/**
 * Created by kevinyin on 2017/7/13.
 */
public class HttpDecoder extends HttpObjectDecoder{
    protected boolean isDecodingRequest() {
        return false;
    }

    protected HttpMessage createMessage(String[] strings) throws Exception {
        return null;
    }

    protected HttpMessage createInvalidMessage() {
        return null;
    }
}
