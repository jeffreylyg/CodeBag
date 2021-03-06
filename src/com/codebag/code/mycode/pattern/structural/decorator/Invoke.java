package com.codebag.code.mycode.pattern.structural.decorator;

import com.codebag.bag.Entry;
import com.codebag.bag.MainActivity;
import com.codebag.bag.MyCode;

/**
 * 一个对象通过叠加其他对象来增加这个对象的功能。
 * 
 * @author zhangrui-ms
 *
 */
public class Invoke extends MyCode {

    public Invoke(MainActivity act) {
        super(act);
    }

    @Entry
    public void invoke() {
        new BlueBuffer(new Hero()).attack();
        new RedBuffer(new Hero()).attack();
        new BlueBuffer(new RedBuffer(new Hero())).attack();
    }
}
