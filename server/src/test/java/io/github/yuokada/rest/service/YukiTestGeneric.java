package io.github.yuokada.rest.service;

public class YukiTestGeneric {

    // Generic method with no arguments
    public static <T> void printType(T hoge) {
        Class<?> aClass = hoge.getClass();
        System.out.println("Type parameter: " + aClass.getName());
    }

    private static <T> void primitive(Class<T> aClass) {
        // implementation
        System.out.println("Primitive Type parameter: " + aClass.getName());
    }

    public static void main(String[] args) {
        YukiTestGeneric.printType("100");
        YukiTestGeneric.printType("100");
        YukiTestGeneric.printType(100);

        primitive(String.class);
        primitive(Integer.class);
        primitive(Player.class);
    }

}
