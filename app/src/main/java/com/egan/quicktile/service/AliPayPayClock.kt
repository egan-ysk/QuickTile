package com.egan.quicktile.service

import android.content.Intent
import android.net.Uri
import android.service.quicksettings.TileService
import com.egan.quicktile.constant.AppConstant

/**
 * 支付宝付款码
 * @author egan
 * @date 2018/5/2 下午2:07.
 */
class AliPayPayClock : TileService() {

    override fun onClick() {
        super.onClick()

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(AppConstant.SCHEME_ALIPAY_PAY)
        startActivityAndCollapse(intent)

    }

}