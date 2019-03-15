package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import session.Session;
import util.IDUtil;
import util.SessionUtil;

import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) throws Exception {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(requestPacket.getVersion());
        responsePacket.setUsername(requestPacket.getUsername());
        if (valid(requestPacket)) {
            // 校验通通过
            responsePacket.setSuccess(true);
            String userId = IDUtil.getRandomId();
            responsePacket.setUserId(userId);
            SessionUtil.bindSession(new Session(userId, requestPacket.getUsername()), ctx.channel());
            System.out.println("[" + requestPacket.getUsername() + "]");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码校验失败!");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    private boolean valid(LoginRequestPacket packet) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
