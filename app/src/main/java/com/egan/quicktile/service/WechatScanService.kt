package com.egan.quicktile.service

import android.content.ComponentName
import android.content.Intent
import android.service.quicksettings.TileService

/**
 * 微信扫一扫
 */
class WechatScanService : TileService() {
    override fun onClick() {
        super.onClick()

        val intent = Intent().setComponent(ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("LauncherUI.From.Scaner.Shortcut", true)
        startActivityAndCollapse(intent)
    }
}