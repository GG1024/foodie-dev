package com.lucky.reflect;

/**
 * @FileName: ReflectServiceImpl.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-01-25
 **/
public class ReflectServiceImpl {


    public void sayHello(String name) {
        System.err.println("Hello " + name);
    }


    public ReflectServiceImpl getInstance() {
        ReflectServiceImpl object = null;
        try {
            object = (ReflectServiceImpl) Class.forName("com.lucky.reflect.ReflectServiceImpl").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

}
