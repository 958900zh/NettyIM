package protocol.response;

import lombok.Data;
import protocol.Packet;
import protocol.command.Command;

import java.util.List;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private List<String> usernameList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
