package com.jd.lib.util;

import android.graphics.Point;
import android.hardware.Camera;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class DisplayUtil {

    public static Point getBestCameraResolution(Camera.Parameters parameters, Point screenResolution) {
        float tmp;
        float mindiff = 100f;
        float x_d_y = (float) screenResolution.x / (float) screenResolution.y;
        Camera.Size best = null;
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (Camera.Size s : supportedPreviewSizes) {
            tmp = Math.abs(((float) s.height / (float) s.width) - x_d_y);
            if (tmp < mindiff) {
                mindiff = tmp;
                best = s;
            }
        }
        return new Point(best.width, best.height);
    }
}
