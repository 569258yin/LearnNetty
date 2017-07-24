/**
 *
 * Java 几种IO模式实现
 *
 * 共有五种IO模式
 * 阻塞IO
 * 非阻塞IO
 * IO多路复用   unix select（全部轮询）/epoll（采用回调，每次只处理活跃IO）
 * 信号驱动IO
 * 异步IO
 *
 * java  API 对于 InpustStream OuputStream I/O read write 实现是阻塞型的，当网络环境差时，会导致线程池中的线程很多被阻塞
 * 而不能完成任务
 * NIO  采用  ByteBuffer 缓冲区进行读写数据，利用channel（通道）全双工，支持读写
 *
 * Created by kevinyin on 2017/7/9.
 */
package com.kevinyin.lnetty.baseio;