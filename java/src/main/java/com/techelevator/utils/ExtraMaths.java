package com.techelevator.utils;

public class ExtraMaths {
    public static int log2(int x) {
        int log = 0;

        if ((x & 0xfff000) != 0) { x >>>= 16; log = 16; }

        if (x >= 256) { x >>>= 8; log += 8; }
        if (x >= 16 ) { x >>>= 4; log += 4; }
        if (x >= 4  ) { x >>>= 2; log += 2; }

        return log + (x >>> 1);
    }
}
