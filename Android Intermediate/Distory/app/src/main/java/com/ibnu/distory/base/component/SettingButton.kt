package com.ibnu.distory.base.component

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ibnu.distory.R
import com.ibnu.distory.R.styleable.SettingButton

class SettingButton(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.layout_setting_button, this)

        val customAttributeStyle = context.obtainStyledAttributes(
            attrs,
            SettingButton,
            0, 0
        )

        val imgMenuIcon = findViewById<ImageView>(R.id.imgIcon)
        val tvMenuTitle = findViewById<TextView>(R.id.tvMenuTitle)

        try {
            imgMenuIcon.setImageDrawable(customAttributeStyle.getDrawable(R.styleable.SettingButton_menuIcon))
            tvMenuTitle.text = customAttributeStyle.getString(R.styleable.SettingButton_menuTitle)
        } finally {
            invalidate()
            customAttributeStyle.recycle()
        }
    }

}