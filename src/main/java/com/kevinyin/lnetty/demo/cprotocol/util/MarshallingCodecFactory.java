package com.kevinyin.lnetty.demo.cprotocol.util;

import com.kevinyin.lnetty.demo.cprotocol.codec.NettyMarshallingDecoder;
import com.kevinyin.lnetty.demo.cprotocol.codec.NettyMarshallingEncoder;
import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Created by kevinyin on 2017/7/27.
 */
public class MarshallingCodecFactory {

    public static NettyMarshallingDecoder buildMarshallingDecoder(){
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();

        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory,configuration);
        NettyMarshallingDecoder decoder = new NettyMarshallingDecoder(provider,1024);
        return decoder;
    }

    public static NettyMarshallingEncoder buildMarshallingEncoder(){
        final MarshallerFactory marshallerFactory = Marshalling
                .getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();

        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory,configuration);
        NettyMarshallingEncoder encoder = new NettyMarshallingEncoder(provider);
        return encoder;
    }

}
