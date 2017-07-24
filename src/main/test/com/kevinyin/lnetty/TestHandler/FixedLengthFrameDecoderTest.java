package com.kevinyin.lnetty.TestHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * FixedLengthFrameDecoder Tester.
 *
 * @author YH
 * @version 1.0
 * @since <pre>���� 13, 2017</pre>
 */
public class FixedLengthFrameDecoderTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test //1
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer(); //2
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));

        Assert.assertTrue(channel.finish()); //5
        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        Assert.assertNull(channel.readInbound());
        buf.release();
    }

    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        Assert.assertTrue(channel.finish());
        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        Assert.assertNull(channel.readInbound());
        buf.release();
    }

} 
