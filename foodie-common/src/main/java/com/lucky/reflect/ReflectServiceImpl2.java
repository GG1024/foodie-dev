package com.lucky.reflect;

/**
 * @FileName: ReflectServiceImpl.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-01-25
 **/
public class ReflectServiceImpl2 {

    private String name;

    public void sayHello() {
        System.err.println("Hello " + name);
    }

    public ReflectServiceImpl2(String name) {
        this.name = name;
    }

    public ReflectServiceImpl2 getInstance() {
        ReflectServiceImpl2 object = null;
        try {
            object = (ReflectServiceImpl2) Class.forName("com.lucky.reflect.ReflectServiceImpl2").getConstructor(String.class).newInstance("张三");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }


}
