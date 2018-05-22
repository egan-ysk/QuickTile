package com.egan.quicktile.service

import android.content.ComponentName
import android.content.Intent
import android.service.quicksettings.TileService

/**
 * @author egan on 2018/5/9.
 * @date 2018/5/9 上午10:22.
 */
class ScanService : TileService() {
    override fun onClick() {
        super.onClick()

        // startActivityAndCollapse(Intent(this.applicationContext, CaptureActivity::class.java))
//        val intent = Intent("com.tencent.mm.action.BIZSHORTCUT").setComponent(ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"))
        val intent = Intent("com.tencent.mm.action.BIZSHORTCUT").setComponent(ComponentName("com.tencent.mm", "com.tencent.mm.plugin.scanner.ui.BaseScanUI"))
        startActivityAndCollapse(intent)
    }
}