package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.request.JoinGroupRequestPacket;
import protocol.response.JoinGroupResponsePacket;
import util.SessionUtil;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();

        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        boolean success = channelGroup.add(ctx.channel());
        if (success) {
            joinGroupResponsePacket.setSuccess(true);
            joinGroupResponsePacket.setGroupId(groupId);
        } else {
            joinGroupResponsePacket.setSuccess(false);
            joinGroupResponsePacket.setReason("添加失败!");
        }
        ctx.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
