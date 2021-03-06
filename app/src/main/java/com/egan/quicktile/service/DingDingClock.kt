package com.egan.quicktile.service

import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.service.quicksettings.TileService
import com.egan.quicktile.constant.AppConstant

/**
 * 启动到钉钉打卡页面
 * @author egan on 2018/4/24.
 * @date 2018/4/24 下午9:03.
 */
class DingDingClock : TileService() {

    override fun onClick() {
        super.onClick()

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(AppConstant.SCHEME_DING_DING)
        startActivityAndCollapse(intent)

    }

}