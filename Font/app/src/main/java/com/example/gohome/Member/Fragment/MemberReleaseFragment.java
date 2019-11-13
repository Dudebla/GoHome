package com.example.gohome.Member.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gohome.Member.Adapter.GridImageAdapter;
import com.example.gohome.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.ButterKnife;
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class MemberReleaseFragment extends Fragment  {

    private static final int REQUEST_CODE_CHOOSE = 23;

    private List<LocalMedia> selectList = new ArrayList<>();   //照片存储列表

    private CircularProgressButton btn;
    private EditText et_releaseName;
    private EditText et_releaseAge;
    private EditText et_releaseType;
    private EditText et_releaseDescription;
    private RadioRealButtonGroup radGro_releaseGender;
    private RadioRealButtonGroup radGro_releaseSterilizine;
    private RadioRealButtonGroup radGro_releaseVaccine;
    private RadioRealButton radBut_releaseGenderBoy;
    private RadioRealButton radBut_releaseGenderGirl;
    private RadioRealButton radBut_releaseSterilizineYes;
    private RadioRealButton radBut_releaseSterilizineNo;
    private RadioRealButton radBut_releaseVaccineYes;
    private RadioRealButton radBut_releaseVaccineNo;
    private Button btn_releaseImg;
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    //最大照片数
    private int maxSelectNum = 9;
    //主题风格
    private int themeId;
    private CheckBox cb_mode;
    //状态栏背景色
    private int statusBarColorPrimaryDark;
    //向上箭头、向下箭头
    private int upResId,downResId;
    private int chooseMode = PictureMimeType.ofAll();







    int Gender;
    int Sterilizine;
    int Vaccine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ){
        View view = inflater.inflate(R.layout.fragment_member_release,null);
        ButterKnife.bind(this, view);

//        btn_submit = view.findViewById(R.id.btn_submit);
        btn = view.findViewById(R.id.btn_releaseSubmit);
        et_releaseName = view.findViewById(R.id.et_releaseName);
        et_releaseAge = view.findViewById(R.id.et_releaseAge);
        et_releaseType = view.findViewById(R.id.et_releaseType);
        et_releaseDescription = view.findViewById(R.id.et_releaseDescription);
        radGro_releaseGender = view.findViewById(R.id.radGro_releaseGender);
        radGro_releaseSterilizine = view.findViewById(R.id.radGro_releaseSterilizine);
        radGro_releaseVaccine = view.findViewById(R.id.radGro_releaseVaccine);
        radBut_releaseGenderBoy = view.findViewById(R.id.radBut_releaseGenderBoy);
        radBut_releaseGenderGirl = view.findViewById(R.id.radBut_releaseGenderGirl);
        radBut_releaseSterilizineYes = view.findViewById(R.id.radBut_releaseSterilizineYes);
        radBut_releaseSterilizineNo = view.findViewById(R.id.radBut_releaseSterilizineNo);
        radBut_releaseVaccineYes = view.findViewById(R.id.radBut_releaseVaccineYes);
        radBut_releaseVaccineNo = view.findViewById(R.id.radBut_releaseVaccineNo);
        recyclerView = view.findViewById(R.id.recycler);
        //主题风格id设置
        themeId = R.style.picture_default_style;
        //选择是否从相册选择或直接拍照
        cb_mode = view.findViewById(R.id.cb_mode);
        statusBarColorPrimaryDark = R.color.yellow;
        upResId = R.drawable.orange_arrow_up;
        downResId = R.drawable.orange_arrow_down;







        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void init() throws InterruptedException {

        Bitmap bitmapDone =  BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.ic_action_done);
        Bitmap bitmapFail = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.ic_action_fail);


        // onClickButton listener detects any click performed on buttons by touch
        radGro_releaseGender.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                Toast.makeText(getActivity(), "Clicked! Position: " + position, Toast.LENGTH_SHORT).show();
                Gender = position;
            }
        });
        radGro_releaseSterilizine.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                Toast.makeText(getActivity(), "Clicked! Position: " + position, Toast.LENGTH_SHORT).show();
                Sterilizine = position;
            }
        });
        radGro_releaseVaccine.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                Toast.makeText(getActivity(), "Clicked! Position: " + position, Toast.LENGTH_SHORT).show();
                Vaccine = position;
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation();

                //获取表单的数据
                System.out.println("宠物姓名："+et_releaseName.getText());
                System.out.println("宠物年龄："+et_releaseAge.getText());
                System.out.println("宠物类型："+et_releaseType.getText());
                System.out.println("宠物描述："+et_releaseDescription.getText());
                System.out.println("宠物性别："+Gender);
                System.out.println("绝育情况："+Sterilizine);
                System.out.println("疫苗情况："+Vaccine);


//                btn.setProgress(100000);      //模拟表单提交过程，但是好像并没有显示出来？？
                //设置提交成功的图标和颜色
                btn.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
                //设置提交失败的图标和颜色
                //btn.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);

                //提示提交成功toast
                Toast toast = TastyToast.makeText(getActivity(), "提交成功!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                //设置toast显示位置
                toast.setGravity(Gravity.CENTER,0,0);
                //调用show使得toast得以显示
                toast.show();

                //清空editText和选择框
                et_releaseName.setText("");
                et_releaseAge.setText("");
                et_releaseType.setText("");
                et_releaseDescription.setText("");
                radGro_releaseGender.deselect();
                radGro_releaseVaccine.deselect();
                radGro_releaseSterilizine.deselect();


                //还原提交按钮
//                btn.revertAnimation();

            }
        });

//        btn_releaseImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                photoAndCamera();
//
//            }
//        });

        //照片上传功能
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(getContext(), onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((position, v) -> {
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(MemberReleaseFragment.this).externalPictureVideo(media.getPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(MemberReleaseFragment.this).externalPictureAudio(media.getPath());
                        break;
                    default:
                        // 预览图片 可自定长按保存路径
                        PictureSelector.create(MemberReleaseFragment.this).themeStyle(themeId).openExternalPreview(position, selectList);
                        break;
                }
            }
        });



    }
//
//    //    启动相册、拍照：
//    public void photoAndCamera(){
//        PictureSelector.create(MemberReleaseFragment.this)
//                .openGallery(PictureMimeType.ofImage())
//                .forResult(PictureConfig.CHOOSE_REQUEST);
//    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = cb_mode.isChecked();
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(MemberReleaseFragment.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .cameraFileName("")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用，选图时不要用
                        .selectionMode(PictureConfig.MULTIPLE)// PictureConfig.MULTIPLE : PictureConfig.SINGLE 多选 or 单选
                        .isSingleDirectReturn(false)// 单选模式下是否直接返回
                        .previewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
//                        .isChangeStatusBarFontColor(isChangeStatusBarFontColor)// 是否关闭白色状态栏字体颜色
                        .setStatusBarColorPrimaryDark(statusBarColorPrimaryDark)// 状态栏背景色
                        .setUpArrowDrawable(upResId)// 设置标题栏右侧箭头图标
                        .setDownArrowDrawable(downResId)// 设置标题栏右侧箭头图标
                        .isOpenStyleCheckNumMode(true)// 是否开启数字选择模式 类似QQ相册
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                        .enableCrop(cb_crop.isChecked())// 是否裁剪
//                        .compress(cb_compress.isChecked())// 是否压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(true)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 废弃 改用cutOutQuality()
                        .cutOutQuality(90)// 裁剪输出质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled(true) // 裁剪是否可旋转图片
                        //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(MemberReleaseFragment.this)
                        .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(themeId)// 主题样式设置 具体参考 values/styles
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                        .selectionMode(PictureConfig.MULTIPLE)// PictureConfig.MULTIPLE : PictureConfig.SINGLE  多选 or 单选
                        .previewImage(true)// 是否可预览图片
//                        .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
//                        .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
//                        .isChangeStatusBarFontColor(isChangeStatusBarFontColor)// 是否关闭白色状态栏字体颜色
                        .setStatusBarColorPrimaryDark(statusBarColorPrimaryDark)// 状态栏背景色
                        .isOpenStyleCheckNumMode(true)// 是否开启数字选择模式 类似QQ相册
                        .setUpArrowDrawable(upResId)// 设置标题栏右侧箭头图标
                        .setDownArrowDrawable(downResId)// 设置标题栏右侧箭头图标
//                        .enableCrop(cb_crop.isChecked())// 是否裁剪
//                        .compress(cb_compress.isChecked())// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                        .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(true)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 废弃 改用cutOutQuality()
                        .cutOutQuality(90)// 裁剪输出质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    // 4.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    for (LocalMedia media : selectList) {
                        Log.i(TAG, "压缩---->" + media.getCompressPath());
                        Log.i(TAG, "原图---->" + media.getPath());
                        Log.i(TAG, "裁剪---->" + media.getCutPath());
                        Log.i(TAG, "Android Q 特有Path---->" + media.getAndroidQToPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }


}
