package com.example.gohome.User;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gohome.R;

public class ButtonLayout extends LinearLayout {
    private int normalIcon;
    private int focusIcon;
    private boolean isFocused = false;
    private ImageView ivIcon;
    private TextView tvText;

    public ButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载布局文件，与setContentView()效果一样
        LayoutInflater.from(context).inflate(R.layout.layout_user_menu_icon, this);
        ivIcon = findViewById(R.id.user_iv_menu_icon);
        tvText = findViewById(R.id.user_tv_menu_text);
    }

    public void setNormalIcon(int normalIcon) {
        this.normalIcon = normalIcon;
        ivIcon.setImageResource(normalIcon);
    }

    public void setFocusIcon(int focusIcon) {
        this.focusIcon = focusIcon;
    }

    public void setIconText(String text) {
        tvText.setText(text);
    }

    public void setFocused(boolean isFocused) {
        //如果已经处在设置的状态中，就不进行操作
        if (this.isFocused != isFocused) {
            this.isFocused = isFocused;
            if (isFocused) {
                //设置获得焦点后的图片
                //文字颜色改变
                ivIcon.setImageResource(focusIcon);
                tvText.setTextColor(getResources().getColor(R.color.orange));
            } else {
                //设置获得普通状态的图片
                //文字颜色改变
                ivIcon.setImageResource(normalIcon);
                tvText.setTextColor(getResources().getColor(R.color.inactiveGray));
            }
        }
    }
}
