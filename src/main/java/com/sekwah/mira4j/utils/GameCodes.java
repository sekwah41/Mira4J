package com.sekwah.mira4j.utils;

public class GameCodes {
    private static final int[] CHAR_MAP = { 25, 21, 19, 10, 8, 11, 12, 13, 22, 15, 16, 6, 24, 23, 18, 7, 0, 3, 9, 4, 14, 20, 1, 2, 5, 17 };
    private static final char[] CHAR_SET = "QWXRTYLPESDFGHUJKZOCVBINMA".toCharArray();

    public static String intToCode(long gameId) {
        if (gameId < -1) {
            // Version 2 codes will always be negative
            int firstTwo = (int) (gameId & 0x3FF);
            int lastFour = (int) ((gameId >> 10) & 0xFFFFF);
            
            return new String(new char[] {
                CHAR_SET[firstTwo % 26],
                CHAR_SET[firstTwo / 26],
                CHAR_SET[lastFour % 26],
                CHAR_SET[(lastFour /= 26) % 26],
                CHAR_SET[(lastFour /= 26) % 26],
                CHAR_SET[lastFour / 26 % 26]
            });
        } else {
            // ?????
            return "CYAN";
            // Version 1 codes will always be positive
//            return new String(
//                ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(Math.toIntExact(gameId)).array(),
//                StandardCharsets.UTF_8
//            );
        }
    }
    
    public static int codeToInt(String gameCode) {
        gameCode = gameCode.toUpperCase();
        
        int first = CHAR_MAP[(int)gameCode.charAt(0) - 65];
        int second = CHAR_MAP[(int)gameCode.charAt(1) - 65];
        int third = CHAR_MAP[(int)gameCode.charAt(2) - 65];
        int fourth = CHAR_MAP[(int)gameCode.charAt(3) - 65];
        int fifth = CHAR_MAP[(int)gameCode.charAt(4) - 65];
        int sixth = CHAR_MAP[(int)gameCode.charAt(5) - 65];
        
        int firstTwo = (first + 26 * second) & 0x3FF;
        int lastFour = (third + 26 * (fourth + 26 * (fifth + 26 * sixth)));
        
        return firstTwo | ((lastFour << 10) & 0x3FFFFC00) | 0x80000000;
    }
}
