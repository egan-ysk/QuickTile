package com.egan.quicktile.service

import android.content.Intent
import android.service.quicksettings.TileService
import com.google.zxing.activity.CaptureActivity

/**
 * @author egan on 2018/5/9.
 * @date 2018/5/9 上午10:22.
 */
class ScanService : TileService() {
    override fun onClick() {
        super.onClick()

        startActivityAndCollapse(Intent(this.applicationContext, CaptureActivity::class.java))
    }
}