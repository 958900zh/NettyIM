package client.console;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        this.consoleCommandMap = new HashMap<>();
        this.consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        this.consoleCommandMap.put("login", new LoginConsoleCommand());
        this.consoleCommandMap.put("logout", new LogoutConsoleCommand());
        this.consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        this.consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        this.consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.nextLine();

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("输入的命令[" + command + "]不存在，请重新输入!");
        }
    }
}
