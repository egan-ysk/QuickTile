package com.egan.quicktile.service

import android.content.ComponentName
import android.content.Intent
import android.service.quicksettings.TileService

class WechatScanService : TileService() {
    override fun onClick() {
        super.onClick()

        val intent = Intent("com.tencent.mm.action.BIZSHORTCUT").setComponent(ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"))
        startActivityAndCollapse(intent)
    }
}