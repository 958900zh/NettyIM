package protocol.request;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
