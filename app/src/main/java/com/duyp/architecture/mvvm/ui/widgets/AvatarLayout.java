package com.duyp.architecture.mvvm.ui.widgets;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

import com.duyp.androidutils.glide.loader.GlideLoader;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.helper.PrefGetter;

/**
 * Created by Kosh on 14 Nov 2016, 7:59 PM
 */

public class AvatarLayout extends FrameLayout {

    ShapedImageView avatar;
    private User user;

    public AvatarLayout(@NonNull Context context) {
        super(context);
    }

    public AvatarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AvatarLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) return;
        inflate(getContext(), R.layout.avatar_layout, this);
        setBackground();
        avatar = findViewById(R.id.avatar);
        if (PrefGetter.isRectAvatar()) {
            avatar.setShape(ShapedImageView.SHAPE_MODE_ROUND_RECT, 15);
        }
        avatar.setOnClickListener(v -> {
            // TODO: 10/24/17 navigate profile
        });
    }

    public void bindData(GlideLoader glideLoader, @Nullable User user) {
        this.user = user;
        if (user != null) {
            avatar.setContentDescription(user.getLogin());
            TooltipCompat.setTooltipText(avatar, user.getLogin());
            glideLoader.loadImage(user.getAvatarUrl(), avatar);
        } else {
            avatar.setOnClickListener(null);
            avatar.setOnLongClickListener(null);
            avatar.setImageResource(R.drawable.ic_github);
        }
    }
    
    private void setBackground() {
        if (PrefGetter.isRectAvatar()) {
            setBackgroundResource(R.drawable.rect_shape);
        } else {
            setBackgroundResource(R.drawable.circle_shape);
        }
    }
}
