package com.kevinyin.lnetty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by kevinyin on 2017/7/10.
 */
public class TestByteBuf {

    public static void main(String[] args) {
        TestByteBuf t = new TestByteBuf();
        t.test3();
    }

    //内存共享的方法
    public void test1(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);

        ByteBuf sliced = buf.slice(0,14);
        System.out.println(sliced.toString(utf8));

        buf.setByte(0  ,'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }

    public void test2(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);

        ByteBuf sliced = buf.copy(0,14);
        System.out.println(sliced.toString(utf8));

        buf.setByte(0  ,'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }

    public void test3(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
        System.out.println((char) buf.readByte());

        int readerIndex = buf.readerIndex();
        int writeIndex = buf.writerIndex();

        buf.writeByte((char)'?');

        assert readerIndex == buf.readerIndex();
        assert writeIndex != buf.writerIndex();

//        ByteBufUtil
    }

}
