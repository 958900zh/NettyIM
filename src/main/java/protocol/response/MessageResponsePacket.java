package protocol.response;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUserName;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
