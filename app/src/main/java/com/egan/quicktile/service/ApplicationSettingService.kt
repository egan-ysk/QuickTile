package com.egan.quicktile.service

import android.content.ComponentName
import android.content.Intent
import android.service.quicksettings.TileService

class ApplicationSettingService : TileService() {
    override fun onClick() {
        super.onClick()
        Intent().apply {
            action = Intent.ACTION_VIEW
            component = ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings")
            startActivityAndCollapse(this)
        }
    }
}