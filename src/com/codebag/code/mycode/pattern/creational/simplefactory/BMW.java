package com.codebag.code.mycode.pattern.creational.simplefactory;

import android.util.Log;

public class BMW implements Car {

    @Override
    public void drive() {
        Log.i("peter", "drive BMW");
    }

}
