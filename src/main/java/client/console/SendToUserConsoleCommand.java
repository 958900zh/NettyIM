package client.console;

import io.netty.channel.Channel;
import protocol.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入消息接收方的userId和将要发送的消息，用空格隔开");
        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
        String userId = scanner.next();
        String message = scanner.nextLine();

        messageRequestPacket.setToUserId(userId);
        messageRequestPacket.setMessage(message);

        channel.writeAndFlush(messageRequestPacket);
    }
}
