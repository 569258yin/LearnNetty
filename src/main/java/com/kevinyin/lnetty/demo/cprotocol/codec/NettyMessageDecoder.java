package com.kevinyin.lnetty.demo.cprotocol.codec;

import com.kevinyin.lnetty.demo.cprotocol.model.Header;
import com.kevinyin.lnetty.demo.cprotocol.model.NettyMessage;
import com.kevinyin.lnetty.demo.cprotocol.util.MarshallingCodecFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import javax.jws.Oneway;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevinyin on 2017/7/27.
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private NettyMarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                               int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.marshallingDecoder = MarshallingCodecFactory.buildMarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null){
            return null;
        }

        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());

        int size = frame.readInt();
        if (size > 0){
            Map<String,Object> attch = new HashMap<String, Object>(size);
            int keySize = 0;
            String key = null;
            byte[] keyArray = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray, CharsetUtil.UTF_8);
                attch.put(key,marshallingDecoder.decode(ctx,frame));
            }
            keyArray = null;
            key = null;
            header.setAttachment(attch);
        }
        if (frame.readableBytes() > 4){
            message.setBody(marshallingDecoder.decode(ctx,frame));
        }
        message.setHeader(header);
        return message;
    }
}
