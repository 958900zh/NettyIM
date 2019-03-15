package util;

import java.util.UUID;

public class IDUtil {

    public static String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
