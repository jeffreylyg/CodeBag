package com.codebag.code.mycode.pattern.behavioral.state;

import android.util.Log;

public class StateA implements State {

    @Override
    public void action() {
        Log.i("peter", "stateA action");
    }

}
