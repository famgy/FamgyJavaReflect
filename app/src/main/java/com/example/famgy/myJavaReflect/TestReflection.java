package com.example.famgy.myJavaReflect;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

public class TestReflection {
    private static final String TAG = "TestReflection";

    public void getClassInfo() {
        /*
        * public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {
        * */
        try {
            Class<?> hashMapClass = Class.forName("java.util.HashMap");

            //获取类名
            Log.d(TAG, "Class : " + hashMapClass.getCanonicalName());

            //获取类限定符
            Log.d(TAG, "Modifiers : " + Modifier.toString(hashMapClass.getModifiers()));

            //获取类泛型信息
            TypeVariable[] tvs = hashMapClass.getTypeParameters();
            if (tvs.length != 0) {
                StringBuffer parameter = new StringBuffer("Pramaeters : ");
                for (TypeVariable tv : tvs) {
                    parameter.append(tv.getName());
                    parameter.append(" ");
                }
                Log.d(TAG, parameter.toString());
            } else {
                Log.d(TAG, " -- No Type Parameters -- ");
            }

            //获取类继承数上的所有父类
            ArrayList<Class> arrayList = new ArrayList<>();
            Class<?> superclass = hashMapClass.getSuperclass();
            if (superclass != null) {
                StringBuffer inheritance = new StringBuffer("Inheritance : ");
                inheritance.append(superclass.getCanonicalName());
                Log.d(TAG, inheritance.toString());
            } else {
                Log.d(TAG, " -- No Super Class -- ");
            }

            //获取类实现的所有接口
            Type[] types = hashMapClass.getGenericInterfaces();
            if (types.length != 0) {
                StringBuffer interfaces = new StringBuffer("Implements Parameters : ");
                for (Type type : types) {
                    interfaces.append(type.toString());
                    interfaces.append(" ");
                }
                Log.d(TAG, interfaces.toString());
            } else {
                Log.d(TAG, " -- No Implements Parameters -- ");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void testField(){
        Cat cat = new Cat("Tom", 2);
        Class clazz = cat.getClass();
        try {
            //注意获取private变量时，需要用getDeclaredField
            Field fieldName = clazz.getDeclaredField("name");
            fieldName.setAccessible(true);

            Field fieldAge = clazz.getField("age");
            //反射获取名字, 年龄
            String name = (String) fieldName.get(cat);
            int age = fieldAge.getInt(cat);
            Log.d(TAG, "before set, Cat name = " + name + " age = " + age);
            //反射重新set名字和年龄
            fieldName.set(cat, "Timmy");
            fieldAge.setInt(cat, 3);
            Log.d(TAG, "after set, Cat " + cat.toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
