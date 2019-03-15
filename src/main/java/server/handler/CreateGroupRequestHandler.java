package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import protocol.request.CreateGroupRequestPacket;
import protocol.response.CreateGroupResponsePacket;
import util.IDUtil;
import util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();

        List<String> userNameList = new ArrayList<>();

        // 创建一个channelGroup
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUsername());
            }
        }

        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        String groupId = IDUtil.getRandomId();
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);

        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.println("创建群聊成功，id为[" + groupId + "]");
        System.out.println("群里的成员都有 " + userIdList);

        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }
}
