package protocol.request;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
