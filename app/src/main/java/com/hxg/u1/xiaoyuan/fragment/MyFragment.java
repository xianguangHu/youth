package com.hxg.u1.xiaoyuan.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.activity.MyDataActivity;
import com.hxg.u1.xiaoyuan.activity.SettingActivity;
import com.hxg.u1.xiaoyuan.bean.AvUser;
import com.hxg.u1.xiaoyuan.model.CallbackService;
import com.hxg.u1.xiaoyuan.model.UserService;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.utils.ImageUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


public class MyFragment extends Fragment {
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    @BindView(R.id.my_image_view)
    SimpleDraweeView mMyImageView;
    @BindView(R.id.my_fragment_iv_gender)
    ImageView mMyFragmentIvGender;
    @BindView(R.id.my_fragment_tv_username)
    TextView mMyFragmentTvUsername;
    @BindView(R.id.my_fragment_mydata)
    RelativeLayout mMyFragmentMydata;
    @BindView(R.id.my_fragment_setting)
    ImageView mMyFragmentSetting;
    private Uri mMTempUri;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        AvUser user = AvUser.getCurrentUser();
        Log.v("'uri====", AvUser.getCurrentUser().getImageUri());
        mMyImageView.setImageURI(Uri.parse(user.getImageUri()));
        if ("男".equals(user.getGender())) {
            mMyFragmentIvGender.setImageResource(R.mipmap.man_32);
        } else {
            mMyFragmentIvGender.setImageResource(R.mipmap.woman_32);
        }
        mMyFragmentTvUsername.setText(user.getUsername());
    }

    @OnClick({R.id.my_image_view, R.id.my_fragment_mydata,R.id.my_fragment_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_image_view:
                //修改头像
                showChoosePicDialog();
                break;
            case R.id.my_fragment_mydata:
                startActivity(new Intent(getActivity(), MyDataActivity.class));
                break;
            case R.id.my_fragment_setting://设置页面
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    /**
     * 弹出修改头像dialog
     */
    private void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("设置头像");
        String[] item = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE://选择本地照片
                        Intent local = new Intent(Intent.ACTION_GET_CONTENT);
                        local.setType("image/*");
                        startActivityForResult(local, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE://拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        mMTempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMTempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("返回了", requestCode + "");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    Intent intent1 = ImageUtil.PhotoCutUtil(mMTempUri);
                    startActivityForResult(intent1, Constant.CROP_SMALL_PICTURE);
                    break;
                case CHOOSE_PICTURE://相册
                    Intent intent = ImageUtil.PhotoCutUtil(data.getData());
                    startActivityForResult(intent, Constant.CROP_SMALL_PICTURE);
                    break;
                case Constant.CROP_SMALL_PICTURE: //得到裁剪的照片
                    if (data != null) {
                        //将照片显示到界面
                        setImage2View(data);
                    }
                    break;
            }
        }
    }

    //显示图片到界面
    private void setImage2View(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap photo = extras.getParcelable("data");
        //讲bitmap保存到服务器
        UserService.updateUserImage(photo, new CallbackService() {
            @Override
            public void callback(String uri, AVException e) {
                mMyImageView.setImageURI(Uri.parse(uri));
            }
        });
    }


}
