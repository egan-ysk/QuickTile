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

        // 点击事件 、获取钉钉打卡页面的 scheme， 进行跳转
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(AppConstant.DING_DING_CLOCK_SCHEME)
        startActivityAndCollapse(intent)

    }

    override fun onBind(intent: Intent?): IBinder {
        return super.onBind(intent)
    }

}