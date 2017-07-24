package test.com.kevinyin.lnetty.TestHandler; 

import com.kevinyin.lnetty.TestHandler.AbsIntegerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* AbsIntegerEncoder Tester. 
* 
* @author YH
* @since <pre>���� 14, 2017</pre> 
* @version 1.0 
*/ 
public class AbsIntegerEncoderTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) 
* 
*/ 
@Test
public void testEncode() throws Exception {
    ByteBuf buf = Unpooled.buffer();
    for (int i = 1;i<10;i++){
        buf.writeInt(i * -1);
    }

    EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
    Assert.assertTrue(channel.writeOutbound(buf));

    Assert.assertTrue(channel.finish());
    for (int i=1;i<10;i++){
        int k = channel.readOutbound();
        Assert.assertEquals(i,k);
        System.out.println(k);
    }
    Assert.assertNull(channel.readOutbound());
}


} 
