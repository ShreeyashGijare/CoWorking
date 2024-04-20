package com.example.coworking.ui.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.coworking.R
import com.example.coworking.databinding.GlobalEditTextWidgetBinding
import com.google.android.material.textfield.TextInputLayout

class GlobalEditTextWidget(
    @get:JvmName("context") var context: Context,
    @get:JvmName("attrs") var attrs: AttributeSet?
) :
    ConstraintLayout(context, attrs) {

    private lateinit var mBinding: GlobalEditTextWidgetBinding
    private var textChangedAction: Runnable? = null


    init {
        initializeView()
    }

    private fun initializeView() {
        mBinding = GlobalEditTextWidgetBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.editText.height = dpToPixels(50, context)
        mBinding.editText.setPadding(
            dpToPixels(10, context),
            dpToPixels(5, context),
            dpToPixels(10, context),
            dpToPixels(5, context)
        )
        mBinding.editText.textSize = resources.getDimension(R.dimen.size_6)
        mBinding.editText.maxLines = 1
        mBinding.editText.addTextChangedListener(setTextWatcher())
    }

    fun getData(): String {
        return mBinding.editText.text.toString()
    }

    fun setData(string: String) {
        mBinding.editText.setText(string)
    }

    fun setTransFormation(method: TransformationMethod) {
        mBinding.editText.transformationMethod = method
        mBinding.textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        mBinding.textInputLayout.endIconDrawable =
            ContextCompat.getDrawable(context, R.drawable.show_hide_password_toggle_icon)
    }

    fun setInputType(inputType: Int) {
        mBinding.editText.inputType = inputType
    }

    private fun setTextWatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(
                searchString: CharSequence?,
                start: Int,
                before: Int,
                p3: Int
            ) {
                textChangedAction?.run()

            }

            override fun afterTextChanged(searchString: Editable?) {
            }
        }
    }

    private fun dpToPixels(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    fun setTextChangedAction(textChangedAction: Runnable) {
        this.textChangedAction = textChangedAction
    }

}