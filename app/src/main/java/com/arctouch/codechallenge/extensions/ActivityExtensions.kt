package com.arctouch.codechallenge.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Activity.goToActivityWithData(activity: Class<*>, bundle: Bundle) {
    val intent = Intent(this, activity)
    intent.putExtras(bundle)
    startActivity(intent)
}
