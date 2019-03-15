package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.request.ListGroupMembersRequestPacket;
import protocol.response.ListGroupMembersResponsePacket;
import session.Session;
import util.SessionUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        Iterator<Channel> iterator = channelGroup.iterator();
        List<String> usernameList = new ArrayList<>();
        while (iterator.hasNext()) {
            Channel next = iterator.next();
            Session session = SessionUtil.getSession(next);
            String username = session.getUsername();
            usernameList.add(username);
        }
        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();
        listGroupMembersResponsePacket.setUsernameList(usernameList);

        ctx.channel().writeAndFlush(listGroupMembersResponsePacket);

    }
}
