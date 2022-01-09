package com.thornbirds.framework.activity

import androidx.appcompat.app.AppCompatActivity

/**
 * Basic definition of Component Activity for Android platform.
 *
 * @author YangLi yanglijd@gmail.com
 */
abstract class ComponentActivity : AppCompatActivity() {

    protected abstract val TAG: String

}