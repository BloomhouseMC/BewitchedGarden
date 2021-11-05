package com.bloomhousemc.bewitchedgarden.common.util;

import net.minecraft.util.StringIdentifiable;


public enum MultiBlock4x4 implements StringIdentifiable {
    M1X1,M1X2,M1X3,M1X4,
    M2X1,M2X2,M2X3,M2X4,
    M3X1,M3X2,M3X3,M3X4,
    M4X1,M4X2,M4X3,M4X4;


    MultiBlock4x4() {
    }

    public static MultiBlock4x4 getMulti(int x, int y){
        return fromString("m"+x+"x"+y);
    }
    public static Point getPoint(String string){
        return new Point(Integer.parseInt(String.valueOf(string.charAt(1))) ,Integer.parseInt(String.valueOf(string.charAt(3))));
    }

    public static MultiBlock4x4 fromString(String s) {
        return
        s.equals("m1x1") ? M1X1 :
        s.equals("m1x2") ? M1X2 :
        s.equals("m1x3") ? M1X3 :
        s.equals("m1x4") ? M1X4 :
        s.equals("m2x1") ? M2X1 :
        s.equals("m2x2") ? M2X2 :
        s.equals("m2x3") ? M2X3 :
        s.equals("m2x4") ? M2X4 :
        s.equals("m3x1") ? M3X1 :
        s.equals("m3x2") ? M3X2 :
        s.equals("m3x3") ? M3X3 :
        s.equals("m3x4") ? M3X4 :
        s.equals("m4x1") ? M4X1 :
        s.equals("m4x2") ? M4X2 :
        s.equals("m4x3") ? M4X3 :
        M4X4;
    }


    public String asString() {
        return
        this == M1X1 ? "m1x1" :
        this == M1X2 ? "m1x2" :
        this == M1X3 ? "m1x3" :
        this == M1X4 ? "m1x4" :
        this == M2X1 ? "m2x1" :
        this == M2X2 ? "m2x2" :
        this == M2X3 ? "m2x3" :
        this == M2X4 ? "m2x4" :
        this == M3X1 ? "m3x1" :
        this == M3X2 ? "m3x2" :
        this == M3X3 ? "m3x3" :
        this == M3X4 ? "m3x4" :
        this == M4X1 ? "m4x1" :
        this == M4X2 ? "m4x2" :
        this == M4X3 ? "m4x3" :
        "m4x4";
    }
    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}

