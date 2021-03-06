package com.example.gohome.User;

import androidx.annotation.RequiresApi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gohome.R;
import com.example.gohome.User.Activity.UserReportActivity;
import com.example.gohome.User.Activity.UserSendActivity;
import com.example.gohome.Utils.KickBackAnimator;
import com.example.gohome.ui.login.LoginActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class PopupMenu extends PopupWindow implements View.OnClickListener {
    private Activity mActivity;
    private Handler mHandler;
    private int mWidth, mHeight;

    private RelativeLayout popupLayout;
    private View rootView;
    private View bgView;
    private ImageView iv_close;
    private ButtonLayout bl_send, bl_report;

    private SharedPreferences sharedPreferences;
    private Integer userId;

    public PopupMenu(Activity activity) {
        mActivity = activity;
    }

    public void init(View view) {
        mHandler = new Handler();

        rootView = view;

        /*
         * decorView是window中的最顶层view，decorView有个getWindowVisibleDisplayFrame方法
         * 可以获取到程序显示的区域，包括标题栏，但不包括状态栏。
         * */
        Rect outRect = new Rect(); // 根据当前窗口可视区域大小更新outRect的值↓
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);

        DisplayMetrics metrics = new DisplayMetrics(); // 获取Activity的实际屏幕信息
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;
        setWidth(mWidth);
        setHeight(mHeight);

        popupLayout = (RelativeLayout) LayoutInflater.from(mActivity).inflate(R.layout.layout_user_popup_menu, null);
        setContentView(popupLayout);

        bgView = popupLayout.findViewById(R.id.rel_bg);
        bgView.setOnClickListener(this);

        popupLayout.findViewById(R.id.lin_bottomNav).setOnClickListener(this);

        iv_close = popupLayout.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);

//        BlurringView blurringView = popupLayout.findViewById(R.id.blurring_view);
//        blurringView.blurredView(view);//模糊背景

        setFocusable(true);
        setOutsideTouchable(true);
        setClippingEnabled(false);//使popup window全屏显示

        bl_send = popupLayout.findViewById(R.id.bl_send);
        bl_send.setNormalIcon(R.drawable.user_send);
        bl_send.setFocusIcon(R.drawable.user_send);
        bl_send.setIconText("送养");
        bl_send.setOnClickListener(this);

        bl_report = popupLayout.findViewById(R.id.bl_report);
        bl_report.setNormalIcon(R.drawable.user_report);
        bl_report.setFocusIcon(R.drawable.user_report);
        bl_report.setIconText("报告");
        bl_report.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view) {
        sharedPreferences = view.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        switch (view.getId()) {
            case R.id.rel_bg:
            case R.id.lin_bottomNav:
            case R.id.iv_close:
                if (isShowing())
                    close();
                break;
            case R.id.bl_send:
                if (userId == -1) {
                    NiftyDialogBuilder dialogBuilderSelect = NiftyDialogBuilder.getInstance(mActivity);
                    dialogBuilderSelect
                            .withTitle("请登录")
                            .withMessage("立即登录")
                            .withDialogColor(view.getResources().getColor(R.color.orange))
                            .withButton1Text("确定")
                            .withButton2Text("取消")
                            .setButton1Click(v -> {
                                dialogBuilderSelect.dismiss();
                                mActivity.startActivityForResult(new Intent(mActivity, LoginActivity.class),5);
                            })
                            .setButton2Click(v -> {
                                Toast.makeText(mActivity, "取消登录", Toast.LENGTH_SHORT).show();
                                dialogBuilderSelect.dismiss();
                            })
                            .show();
                    return;
                }
//                bl_send.setFocused(true);
//                bl_report.setFocused(false);
                close();
                Intent intent1 = new Intent(mActivity, UserSendActivity.class);
                intent1.putExtra("userId", userId);
                mActivity.startActivity(intent1);
                break;
            case R.id.bl_report:
                if (userId == -1) {
                    NiftyDialogBuilder dialogBuilderSelect = NiftyDialogBuilder.getInstance(mActivity);
                    dialogBuilderSelect
                            .withTitle("请登录")
                            .withMessage("立即登录")
                            .withDialogColor(view.getResources().getColor(R.color.orange))
                            .withButton1Text("确定")
                            .withButton2Text("取消")
                            .setButton1Click(v -> {
                                dialogBuilderSelect.dismiss();
                                mActivity.startActivityForResult(new Intent(mActivity, LoginActivity.class),5);
                            })
                            .setButton2Click(v -> {
                                Toast.makeText(mActivity, "取消登录", Toast.LENGTH_SHORT).show();
                                dialogBuilderSelect.dismiss();
                            })
                            .show();
                    return;
                }
//                bl_send.setFocused(false);
//                bl_report.setFocused(true);
                close();
                Intent intent2 = new Intent(mActivity, UserReportActivity.class);
                intent2.putExtra("userId", userId);
                mActivity.startActivity(intent2);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void show() {
        showAtLocation(rootView, Gravity.TOP | Gravity.START, 0, 0);
        mHandler.post(() -> {
            try {
                int x = mWidth / 2;
                int y = (int) (mHeight - fromDp2Px(25));
                Animator animator = ViewAnimationUtils.createCircularReveal(bgView,
                        x, y, 0, bgView.getHeight());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
//                            bgView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                            popupLayout.setVisibility(View.VISIBLE);
                    }
                });
                animator.setDuration(500);
                animator.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        showAnimation(popupLayout);
    }

    private void showAnimation(ViewGroup layout) {
        try {
            LinearLayout linearLayout = layout.findViewById(R.id.lin_bottomNav);
            mHandler.post(() -> { //加号旋转
                iv_close.animate().rotation(90).setDuration(500);
            });
            //菜单项弹出动画
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                final View child = linearLayout.getChildAt(i);
                child.setOnClickListener(this);
                child.setVisibility(View.INVISIBLE);
                mHandler.postDelayed(() -> {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child,
                            "translationY", 600, 0);
                    fadeAnim.setDuration(500);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(500);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }, i * 50 + 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void close() {
        mHandler.post(() -> iv_close.animate().rotation(-90).setDuration(500));
        try {
            int x = mWidth / 2;
            int y = (int) (mHeight - fromDp2Px(25));
            Animator animator = ViewAnimationUtils.createCircularReveal(bgView,
                    x, y, bgView.getHeight(), 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
//                    popupLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                    bgView.setVisibility(View.GONE);
                    dismiss();
                }
            });
            animator.setDuration(500);
            animator.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float fromDp2Px(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }
}
