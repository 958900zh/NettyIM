package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.request.MessageRequestPacket;
import protocol.response.MessageResponsePacket;
import session.Session;
import util.SessionUtil;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUsername());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        Channel channel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if (channel != null && SessionUtil.hasLogin(channel)) {
            channel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println(messageRequestPacket.getToUserId()+" 不在线，发送失败");
        }
    }
}
