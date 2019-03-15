package session;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {
    private String userId;
    private String username;

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String toString() {
        return userId + ":" + username;
    }
}
