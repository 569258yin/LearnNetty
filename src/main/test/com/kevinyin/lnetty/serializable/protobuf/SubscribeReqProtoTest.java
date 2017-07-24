package com.kevinyin.lnetty.serializable.protobuf; 

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/** 
* SubscribeReqProto Tester. 
* 
* @author YH
* @since <pre>���� 24, 2017</pre> 
* @version 1.0 
*/ 
public class SubscribeReqProtoTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

private byte[] encode(SubscribeReqProto.SubscribeReq req){
    return req.toByteArray();
}

private SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
    return SubscribeReqProto.SubscribeReq.parseFrom(body);
}

private SubscribeReqProto.SubscribeReq createSubScribeReq(){
    SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq
            .newBuilder();
    builder.setSubReqID(1);
    builder.setUserName("kevin");
    builder.setProductName("Netty book");
    builder.setAddress("ShangHai XuHui");
    return builder.build();
}

@Test
public void testProBuf() throws InvalidProtocolBufferException {
    SubscribeReqProto.SubscribeReq req = createSubScribeReq();
    System.out.println("Before encode :" + req.toString());
    SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
    System.out.println("After decode :" + req2.toString());
    System.out.println("Assert equal : -->" + req2.equals(req));
}

    @Test
    public void testProBuf2() throws InvalidProtocolBufferException {
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            SubscribeReqProto.SubscribeReq req = createSubScribeReq();
            SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        }
        System.out.println((System.currentTimeMillis() - currentTime) + "ms" );
//        82ms


    }

} 
