package com.egan.quicktile.constant

import java.net.URLEncoder

/**
 * @author egan on 2018/4/24.
 * @date 2018/4/24 下午9:00.
 */
object AppConstant {

    // 钉钉打卡页面的 scheme
    public final val SCHEME_DING_DING = "dingtalk://dingtalkclient/page/link?url=" +
            URLEncoder.encode("https://attend.dingtalk.com/attend/index.html")

    // 支付宝付款码的 scheme
    public final val SCHEME_ALIPAY_PAY = "alipays://platformapi/startapp?appId=20000056"

}