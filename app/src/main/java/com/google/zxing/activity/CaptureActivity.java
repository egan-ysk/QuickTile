package com.google.zxing.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.egan.quicktile.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.camera.CameraManager;
import com.google.zxing.decoding.CaptureActivityHandler;
import com.google.zxing.decoding.InactivityTimer;
import com.google.zxing.view.ViewfinderView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Vector;
import java.util.stream.Collectors;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class CaptureActivity extends Activity implements Callback {

//    private static final int REQUEST_CODE_SCAN_GALLERY = 100;

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;
    //	private Button cancelScanButton;
//    public static final int RESULT_CODE_QR_SCAN = 0xA1;
//    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";

    private Dialog dialog = null;
    private TextView textView = null;






    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_content);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //getMenuInflater().inflate(R.menu.scanner_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        switch (item.getItemId()){
////            case R.id.scan_local:
////                //打开手机中的相册
////                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
////                innerIntent.setType("image/*");
////                Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
////                this.startActivityForResult(wrapperIntent, REQUEST_CODE_SCAN_GALLERY);
////                return true;
////        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
//        if (requestCode == RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_CODE_SCAN_GALLERY:
//                    //获取选中图片的路径
//                    Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
//                    if (cursor.moveToFirst()) {
//                        photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                    }
//                    cursor.close();
//
//                    mProgress = new ProgressDialog(CaptureActivity.this);
//                    mProgress.setMessage("正在扫描...");
//                    mProgress.setCancelable(false);
//                    mProgress.show();
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Result result = scanningImage(photo_path);
//                            if (result != null) {
////                                Message m = handler.obtainMessage();
////                                m.what = R.id.decode_succeeded;
////                                m.obj = result.getText();
////                                handler.sendMessage(m);
//                                Intent resultIntent = new Intent();
//                                Bundle bundle = new Bundle();
//                                bundle.putString(INTENT_EXTRA_KEY_QR_SCAN, result.getText());
////                                Logger.d("saomiao",result.getText());
////                                bundle.putParcelable("bitmap",result.get);
//                                resultIntent.putExtras(bundle);
//                                CaptureActivity.this.setResult(RESULT_CODE_QR_SCAN, resultIntent);
//
//                            } else {
//                                Message m = handler.obtainMessage();
//                                m.what = R.id.decode_failed;
//                                m.obj = "Scan failed!";
//                                handler.sendMessage(m);
//                            }
//                        }
//                    }).start();
//                    break;
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    /**
//     * 扫描二维码图片的方法
//     *
//     * @param path
//     * @return
//     */
//    public Result scanningImage(String path) {
//        if (TextUtils.isEmpty(path)) {
//            return null;
//        }
//        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
//        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // 先获取原大小
//        scanBitmap = BitmapFactory.decodeFile(path, options);
//        options.inJustDecodeBounds = false; // 获取新的大小
//        int sampleSize = (int) (options.outHeight / (float) 200);
//        if (sampleSize <= 0)
//            sampleSize = 1;
//        options.inSampleSize = sampleSize;
//        scanBitmap = BitmapFactory.decodeFile(path, options);
//        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
//        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//        QRCodeReader reader = new QRCodeReader();
//        try {
//            Result result = reader.decode(bitmap1, hints);
//            Log.d("YSK", "CaptureActivity:scanningImage >>> " + result);
//            return result;
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (ChecksumException e) {
//            e.printStackTrace();
//        } catch (FormatException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scanner_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

        //quit the scan view
//		cancelScanButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				CaptureActivity.this.finish();
//			}
//		});
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
//            setVolumeControlStream(AudioManager.STREAM_MUSIC);
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setOnCompletionListener(beepListener);
//
//            AssetFileDescriptor file = getResources().openRawResourceFd(
//                    R.raw.beep);
//            try {
//                mediaPlayer.setDataSource(file.getFileDescriptor(),
//                        file.getStartOffset(), file.getLength());
//                file.close();
//                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
//                mediaPlayer.prepare();
//            } catch (IOException e) {
//                mediaPlayer = null;
//            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString(INTENT_EXTRA_KEY_QR_SCAN, resultString);
//            // 不能使用Intent传递大于40kb的bitmap，可以使用一个单例对象存储这个bitmap
////            bundle.putParcelable("bitmap", barcode);
////            Logger.d("saomiao",resultString);
//            resultIntent.putExtras(bundle);
//            this.setResult(RESULT_CODE_QR_SCAN, resultIntent);
            // TODO: by maka 2018/5/9 上午10:07 获得数据
            showResult(resultString);
//            startIntent(resultString);
        }
//        CaptureActivity.this.finish();
    }

    private void showResult(final String resultString) {
        Log.d("egan", "CaptureActivity : showResult >>> " + String.valueOf("扫描结果" + resultString));
        if (null == dialog || null == textView) {
            dialog = new Dialog(this);
            dialog.setTitle("扫描结果");
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_dialog_scan_result, null);
            textView = view.findViewById(R.id.textViewResult);
            view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            view.findViewById(R.id.buttonBrowser).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(resultString));
                    startActivity(intent);
                    CaptureActivity.this.finish();
                }
            });
            view.findViewById(R.id.buttonClipboard).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 得到剪贴板管理器
                    ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (null != cmb){
                        cmb.setPrimaryClip(ClipData.newPlainText("扫描结果", resultString));
                        CaptureActivity.this.finish();
                    }else {
                        Toast.makeText(CaptureActivity.this, "复制失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.setContentView(view);
        }
        textView.setText(resultString);
        dialog.show();
    }

    private void startIntent(String result) {
        Log.d("YSK", result);
        Intent intent = null;
        if (result.toLowerCase().startsWith("wxp://")) {
//            intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("weixin://wap/pay"));
            intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("weixin://scanqrcode"));
        } else if (result.toLowerCase().contains("qr.alipay.com")) {
            // alipays://platformapi/startapp?saId=10000007&qrcode=https%3A%2F%2Fqr.alipay
            // .com%2Ffkx02932cpcptcqdglyuc6%3Ft%3D1525831417182
            try {
                String encode = URLEncoder.encode(result.toLowerCase(), "utf-8");
                String scheme = "alipays://platformapi/startapp?saId=10000007&qrcode=" + encode;
                intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(scheme));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(result.toLowerCase()));
        }
        if (intent != null) {
            startActivity(intent);
//            startActivity(new Intent(this, MainActivity.class).putExtra("url", result.toLowerCase()));
        }
    }
}