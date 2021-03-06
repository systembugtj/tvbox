package me.systembug.tv.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.ButtonBarLayout;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import java.util.ArrayList;
import java.util.List;

import me.systemb.tv.view.R;

/**
 * Created by systembug on 5/6/16.
 */
public class ButtonBox extends RelativeLayout {


    Button mActionItem;
    ImageView mMore;
    TextView mTitle;
    TextView mDesc;
    int mCurrent = 0;
    List<String> mOptions = new ArrayList<String>();
    OnClickListener mClickedListener = null;

    public ButtonBox(Context context) {
        this(context, null);
    }

    public ButtonBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    public void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View v = LayoutInflater.from(context).inflate(R.layout.button_box, null);


        mMore = (ImageView) v.findViewById(R.id.more_arrow);

        mTitle = (TextView) v.findViewById(R.id.action_title);
        mDesc = (TextView) v.findViewById(R.id.action_description);
        mActionItem = (Button) v.findViewById(R.id.action_item);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonBox);

            mTitle.setText(a.getString(R.styleable.ButtonBox_button_title));
            mDesc.setText(a.getString(R.styleable.ButtonBox_button_description));
            mActionItem.setText(a.getString(R.styleable.ButtonBox_button_name));

            Drawable rightArrow = a.getDrawable(R.styleable.ButtonBox_more_arrow);
            if (rightArrow != null) {
                mMore.setImageDrawable(rightArrow);
            } else {
                mMore.setImageDrawable(new IconDrawable(getContext(),
                        MaterialIcons.md_keyboard_arrow_right).color(Color.WHITE).actionBarSize());
            }
        }
        this.setFocusable(true);
        // Disable descendant handle focus, we are one.
        this.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        this.setFocusableInTouchMode(true);
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireClickEvent();
            }
        });
        addView(v);
    }


    private void fireClickEvent() {
        if(mClickedListener != null) {
            mClickedListener.onClick(this);
        }
    }

    public ButtonBox clicked(@NonNull OnClickListener listener) {
        mClickedListener = listener;
        return this;
    }

    public ButtonBox more(@NonNull Drawable icon) {
        mMore.setImageDrawable(icon);
        return this;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                fireClickEvent();
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}
