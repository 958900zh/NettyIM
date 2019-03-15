package protocol.response;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;
    private String groupId;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
