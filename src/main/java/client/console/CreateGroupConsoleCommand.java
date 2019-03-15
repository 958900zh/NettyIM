package client.console;

import io.netty.channel.Channel;
import protocol.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    public static final String USER_ID_SPLITTER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.println("【创建群聊】输入userId列表，用英文逗号隔开");
        String userIds = scanner.nextLine();
        List<String> userIdList = Arrays.asList(userIds.split(USER_ID_SPLITTER));
        createGroupRequestPacket.setUserIdList(userIdList);

        channel.writeAndFlush(createGroupRequestPacket);
    }
}
