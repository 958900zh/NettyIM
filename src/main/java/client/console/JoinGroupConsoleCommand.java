package client.console;

import io.netty.channel.Channel;
import protocol.request.JoinGroupRequestPacket;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("【加入群聊】请输入群聊的groupId:");
        String groupId = scanner.nextLine();

        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        joinGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
