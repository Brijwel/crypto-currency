package com.brijwel.cryptocurrency.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.brijwel.cryptocurrency.R

/**
 * Created by Brijwel on 07-02-2021.
 */

fun Context.openWebPage(url: String) {
    val builder = CustomTabsIntent.Builder()
    builder.setDefaultColorSchemeParams(
        CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(this, R.color.black))
            .build()
    )
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
}