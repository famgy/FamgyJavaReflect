package com.example.famgy.myJavaReflect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.famgy.myfirstsdk.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());


    }

    public void toShowClassInfo(View view) {
        Class clazz = TestReflection.class;

        try {
            Constructor constructor = clazz.getConstructor();
            TestReflection testReflection = (TestReflection) constructor.newInstance();

            Method getClassInfo = clazz.getDeclaredMethod("getClassInfo");
            getClassInfo.invoke(testReflection);

            Method testField = clazz.getDeclaredMethod("testField");
            testField.invoke(testReflection);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
