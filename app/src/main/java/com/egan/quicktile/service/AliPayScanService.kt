package com.egan.quicktile.service

import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.service.quicksettings.TileService
import com.egan.quicktile.constant.AppConstant

/**
 * 启动支付宝扫一扫
 */
class AliPayScanService : TileService() {

    override fun onClick() {
        super.onClick()

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(AppConstant.SCHEME_ALIPAY_SCAN)
        startActivityAndCollapse(intent)

    }

}