package com.egan.quicktile.service

import android.content.ComponentName
import android.content.Intent
import android.service.quicksettings.TileService

class DevelopSettingService : TileService() {
    override fun onClick() {
        super.onClick()
        Intent().apply {
            component = ComponentName("com.android.settings", "com.android.settings.Settings.DevelopmentActivity")
            startActivityAndCollapse(this)
        }
    }
}