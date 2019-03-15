package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import session.Session;
import util.LoginUtil;
import util.SessionUtil;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            SessionUtil.bindSession(new Session(loginResponsePacket.getUserId(), loginResponsePacket.getUsername()), ctx.channel());
            System.out.println(new Date() +" ["+loginResponsePacket.getUsername() +":"+ loginResponsePacket.getUserId() +"]登录成功!");
        } else {
            System.out.println(new Date() + " 客户端登录失败，原因是: " + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭");
    }
}
