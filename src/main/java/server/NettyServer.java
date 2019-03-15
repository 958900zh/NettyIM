package server;

import codec.PacketDecoder;
import codec.PacketEncoder;
import codec.Splitter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import server.handler.*;

public class NettyServer {

    private static final int PORT = 9000;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new Splitter());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new AuthHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new CreateGroupRequestHandler());
                        nioSocketChannel.pipeline().addLast(new JoinGroupRequestHandler());
                        nioSocketChannel.pipeline().addLast(new ListGroupMembersRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap, PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, int PORT) {
        serverBootstrap.bind(PORT).addListener(future -> {
            if (future.isSuccess())
                System.out.println("端口【"+PORT+"】绑定成功");
            else
                System.out.println("端口【"+PORT+"】绑定失败");

        });
    }
}
