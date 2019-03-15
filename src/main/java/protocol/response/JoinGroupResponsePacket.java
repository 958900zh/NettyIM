package protocol.response;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

@Data
public class JoinGroupResponsePacket extends Packet {

    private String groupId;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
