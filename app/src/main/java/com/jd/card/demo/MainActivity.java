package com.jd.card.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jd.lib.CameraActivity;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in=new Intent(this, CameraActivity.class);
        startActivity(in);
    }
}
