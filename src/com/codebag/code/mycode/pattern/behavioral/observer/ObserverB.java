package com.codebag.code.mycode.pattern.behavioral.observer;

import android.util.Log;

public class ObserverB implements Observer {

    @Override
    public void action() {
        Log.i("peter", "action B");
    }

}
