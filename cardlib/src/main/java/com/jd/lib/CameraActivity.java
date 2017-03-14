package com.jd.lib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import com.jd.lib.util.DisplayUtil;
import com.jd.lib.util.ScreenUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class CameraActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    public CameraSurfaceView surfaceView;
    public Button mButton;

    public SurfaceHolder surfaceHolder;

    public Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initView();
    }

    private void initView() {
        mButton=(Button)findViewById(R.id.btn_take);
        surfaceView=(CameraSurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null,null,jpegCallback);
            }
        });
    }

    private Camera.PictureCallback jpegCallback = new Camera.PictureCallback(){

        public void onPictureTaken(byte[] data, Camera camera) {
            Camera.Parameters ps = camera.getParameters();
            if(ps.getPictureFormat() == PixelFormat.JPEG){
                //存储拍照获得的图片
                String path = savePicture(data);
                //将图片交给Image程序处理
//                Uri uri = Uri.fromFile(new File(path));
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                intent.setDataAndType(uri, "image/jpeg");
//                startActivity(intent);
            }
        }
    };

    private String savePicture(byte[] data) {
        String imgFileName = "face.jpg";
        FileOutputStream outputStream;
        try {
            File file = new File("/sdcard/" + imgFileName);
//            if(file.exists())
//                return null;
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgFileName;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera=null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceView.setWillNotDraw(false);
        surfaceHolder=holder;
        initCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceHolder = holder;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            holder.removeCallback(this);
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        surfaceView = null;
        surfaceHolder = null;
    }

    private void initCamera() {
        if (null == camera) {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            camera.setOneShotPreviewCallback(this);
        }
        if (true) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                Point p = DisplayUtil.getBestCameraResolution(parameters, ScreenUtils.getScreenMetrics(this));
                parameters.setPreviewSize(p.x, p.y);

                List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
                int fps = supportedPreviewFpsRange.get(supportedPreviewFpsRange.size() - 1)[1];
                parameters.setPreviewFpsRange(fps, fps);
                if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    parameters.set("orientation", "portrait");
                    parameters.set("rotation", 90);
                    camera.setDisplayOrientation(90);
                } else {
                    parameters.set("orientation", "landscape");
                    camera.setDisplayOrientation(0);
                }
                camera.setParameters(parameters);
                camera.setPreviewDisplay(surfaceHolder);
                camera.setPreviewCallback(this);
                camera.startPreview();
                camera.autoFocus(null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}
