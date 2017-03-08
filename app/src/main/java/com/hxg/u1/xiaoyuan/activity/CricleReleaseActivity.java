package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.model.StatusService;
import com.hxg.u1.xiaoyuan.utils.DialogUtil;
import com.hxg.u1.xiaoyuan.utils.ImageUtil;
import com.hxg.u1.xiaoyuan.utils.MainUtil;
import com.hxg.u1.xiaoyuan.widgets.TitleBar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CricleReleaseActivity extends Activity {
    private LinkedList<String> dataList = new LinkedList<String>();


    private static final int REQUEST_THUMBNAIL = 1;//请求缩略图
    @BindView(R.id.circle_release_title_bar)
    TitleBar mCircleReleaseTitleBar;
    @BindView(R.id.cirle_release_et)
    EditText mCirleReleaseEt;
    @BindView(R.id.cirlr_release_gv)
    GridView mCirlrReleaseGv;
    @BindView(R.id.circle_release)
    View mCircleRelease;
    private String mEt;
    private Handler mHandler;
    private ProgressDialog mDialog;
    private PopupWindow pop = null;
    private LinearLayout mLl_popup;
    private GridAdapter mAdapter;
    private List<Bitmap> mBitmaps=new ArrayList<>();
    private String mImagePath;
    private Bitmap mBitmap;
    private Bitmap mNewbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricle_release);
        ButterKnife.bind(this);
        initView();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mDialog = DialogUtil.showSpinnerDialog(CricleReleaseActivity.this);
                        break;
                    case 2:
                        mDialog.dismiss();
                        break;
                    case 3:
                        MainUtil.ToastUtil(CricleReleaseActivity.this, (String) msg.obj);
                        break;
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        // 先判断是否已经回收
        if(mBitmap != null && !mBitmap.isRecycled()){
            // 回收并且置为null
            mBitmap.recycle();
            mBitmap = null;
        }
        if(mNewbitmap != null && !mNewbitmap.isRecycled()){
            // 回收并且置为null
            mNewbitmap.recycle();
            mNewbitmap = null;
        }
        System.gc();
        super.onDestroy();
    }

    private void initView() {
        initTitle();
        initPop();
        initGrid();
    }

    //初始化gridview
    private void initGrid() {
        mCirlrReleaseGv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new GridAdapter(this);
//        mAdapter.update();
        mAdapter.notifyDataSetChanged();
        mCirlrReleaseGv.setAdapter(mAdapter);
        mCirlrReleaseGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mBitmaps.size()) {
                    mLl_popup.startAnimation(AnimationUtils.loadAnimation(CricleReleaseActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(mCircleRelease, Gravity.BOTTOM, 0, 0);
                } else {
//                    Intent intent = new Intent(MainActivity.this,
//                            GalleryActivity.class);
//                    intent.putExtra("position", "1");
//                    intent.putExtra("ID", arg2);
//                    startActivity(intent);
                }
            }
        });
    }

    //初始化popupwindows
    private void initPop() {
        pop = new PopupWindow(CricleReleaseActivity.this);
        View view = getLayoutInflater().inflate(R.layout.photo_popupwindows_item, null);
        mLl_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        parent.setOnClickListener(new MyOnClickListener());
        Button cameraBtn = (Button) view.findViewById(R.id.item_popupwindows_camera);
        cameraBtn.setOnClickListener(new MyOnClickListener());
        Button photoBtn = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        photoBtn.setOnClickListener(new MyOnClickListener());
        Button cancelBtn = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        cancelBtn.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_popupwindows_camera:
                    photo();
                    break;
                case R.id.item_popupwindows_Photo:

                    break;
            }
            pop.dismiss();
            mLl_popup.clearAnimation();
        }
    }

    //打开手机拍照
    private void photo() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_THUMBNAIL);
// 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
             */
            //获取与应用相关联的路径
            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/"+formatter.format(new Date())+".jpg";
            Uri imageFileUri;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){////如果是7.0android系统
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, new File(imageFilePath, timestamp).getAbsolutePath());
                imageFileUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            }else {
                File imageFile = new File(imageFilePath,timestamp);// 通过路径创建保存文件
                mImagePath = imageFile.getAbsolutePath();
                imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, REQUEST_THUMBNAIL);
        } else {
            Toast.makeText(this, "内存卡不存在!", Toast.LENGTH_LONG).show();
        }
    }
    String[] proj = { MediaStore.MediaColumns.DATA };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_THUMBNAIL) {
//                bitmap = (Bitmap) data.getExtras().get("data");
////                FileUtil.saveBitmap(bitmap, fileName);
////
////                ImageItem takePhoto = new ImageItem();
////                takePhoto.setBitmap(bitmap);
////                Bimp.tempSelectBitmap.add(takePhoto);
//                mBitmaps.add(bitmap);
                String imagePath = "";
                Uri uri = null;
                if (data != null && data.getData() != null) {// 有数据返回直接使用返回的图片地址
                    uri = data.getData();
                    Cursor cursor = getContentResolver().query(uri, proj, null,
                            null, null);
                    if (cursor == null) {
                        uri = ImageUtil.getUri(this, data);
                    }
                    imagePath = ImageUtil.getFilePathByFileUri(this, uri);
                } else {// 无数据使用指定的图片路径
                    imagePath = mImagePath;
                }
                mBitmap = ImageUtil.getImageThumbnail(imagePath,ImageUtil.getWidth(CricleReleaseActivity.this),ImageUtil.getHeight(CricleReleaseActivity.this));
                int degree = ImageUtil.getBitmapDegree(imagePath);
                /**
                 * 把图片旋转为正的方向
                 */
                mNewbitmap = ImageUtil.rotateBitmapByDegree(mBitmap, degree);
                mBitmaps.add(mNewbitmap);
            }
        }
    }

    private void initTitle() {
        mCircleReleaseTitleBar.setLeftImageResource(R.mipmap.left);
        mCircleReleaseTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textView = (TextView) mCircleReleaseTitleBar.addAction(new TitleBar.TextAction("发送") {
            @Override
            public void performAction(View view) {
                //将数据发送到服务器
                mEt = mCirleReleaseEt.getText().toString();
                if (TextUtils.isEmpty(mEt) == false || mBitmaps.size()>0) {
                    new Thread() {
                        @Override
                        public void run() {
                            Message message = mHandler.obtainMessage();
                            message.what = 1;
                            mHandler.sendMessage(message);
                            StatusService.sendStatus(mEt, mBitmaps, new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    Message message1 = mHandler.obtainMessage();
                                    message1.what = 2;
                                    mHandler.sendMessage(message1);
                                    if (e != null) {
                                        Message message2 = mHandler.obtainMessage();
                                        message2.what = 3;
                                        message2.obj = e.getMessage();
                                        mHandler.sendMessage(message2);
                                    } else {
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                }
                            });
                        }
                    }.start();
                }
            }
        });
    }

    class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

//        public void update() {
//            loading();
//        }

        public int getCount() {
            if (mBitmaps.size() == 9) {
                return 9;
            }
            return (mBitmaps.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.published_gride_item,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == mBitmaps.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.addphoto));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(mBitmaps.get(position));
            }
//            update();
            mAdapter.notifyDataSetChanged();
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

//        Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 1:
//                        mAdapter.notifyDataSetChanged();
//                        break;
//                }
//                super.handleMessage(msg);
//            }
//        };
//
//        public void loading() {
//            new Thread(new Runnable() {
//                public void run() {
//                    while (true) {
//                        if (Bimp.max == mBitmaps.size()) {
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//                            break;
//                        } else {
//                            Bimp.max += 1;
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//                        }
//                    }
//                }
//            }).start();
//        }
    }
}

