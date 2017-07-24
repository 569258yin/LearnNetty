/**
 * 以实际重现 TCP/IP 中 可能出现的粘包，拆包等问题
 *
 * 并利用分隔符解码器（LineBasedFrameDecoder）和字符串解码器（StringDecoder）
 * 实现了安全的读取每一行文本信息的程序
 * Created by kevinyin on 2017/7/24.
 */
package com.kevinyin.lnetty.msgPackage.unsafePackage;