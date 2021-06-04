package com.test.opensource;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by Scott on 2021/5/28 0028
 */
@Route(path = "/ppp/main")
public class MyProvider implements IProvider {
    @Override
    public void init(Context context) {

    }
}
