package com.metauniverse.estore.util.item_util;
import java.math.BigInteger;
import java.util.UUID;

public class UUIDToLongConverter {
    public static Long convert(UUID uuid) {
        BigInteger bigInteger = new BigInteger(uuid.toString().replace("-", ""), 16);
        return bigInteger.longValue();
    }
}

