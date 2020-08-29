package com.egan.quicktile.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class AutoInstallAccessibilityService extends AccessibilityService {

    List<String> matchString = new ArrayList<String>() {{
        add("安装");
        add("确定");
        add("下一步");
        add("继续");
        add("完成");
        add("继续安装");
    }};

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();

        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType ==
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            parseInfo(source);
        }
    }

    @Override
    public void onInterrupt() {
    }

    private boolean parseInfo(AccessibilityNodeInfo source) {
        if (source != null) {
            if (Button.class.getName().equals(source.getClassName())) {
                String s = source.getText().toString();
                if (matchString.contains(s)) {
                    source.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true;
                }
            }
            for (int i = 0; i < source.getChildCount(); i++) {
                AccessibilityNodeInfo childNodeInfo = source.getChild(i);
                if (parseInfo(childNodeInfo)) {
                    return true;
                }
            }
        }
        return false;
    }
}
