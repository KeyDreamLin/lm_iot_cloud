package com.lm.cloud.tcp.service;

import com.lm.cloud.tcp.service.config.ChannelInit;
import com.lm.cloud.tcp.service.config.ITcpServer;
import com.lm.cloud.tcp.service.config.ServerProperties;
import com.lm.cloud.tcp.service.utils.RedisDeviceUtils;
import com.lm.common.redis.devicekey.CloudRedisKey;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * 启动 Server
 *
 * @author qiding
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TcpServer implements ITcpServer {

    private final ChannelInit channelInit;

    private final ServerProperties serverProperties;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;


    @Override
    public void start() {
        RedisDeviceUtils.delAll();
        log.info("初始化 TCP server ...");
        bossGroup = serverProperties.isUseEpoll() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        workerGroup = serverProperties.isUseEpoll() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        this.tcpServer();
    }


    /**
     * 初始化
     */
    private void tcpServer() {
        try {
            new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(serverProperties.isUseEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(serverProperties.getPort()))
                    // 配置 编码器、解码器、业务处理
                    .childHandler(channelInit)
                    // tcp缓冲区
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 将网络数据积累到一定的数量后,服务器端才发送出去,会造成一定的延迟。希望服务是低延迟的,建议将TCP_NODELAY设置为true
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 绑定端口，开始接收进来的连接
                    .bind().sync();
            log.info("tcpServer启动成功！开始监听端口：{}", serverProperties.getPort());


        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 销毁
     */
    @PreDestroy
    @Override
    public void destroy() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
