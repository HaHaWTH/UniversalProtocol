package io.wdsj.universalprotocol.util;

public class RandomUtil {
    private RandomUtil() {}

    public static int getRandomInt(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }
}
