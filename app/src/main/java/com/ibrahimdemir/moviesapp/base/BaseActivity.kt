package com.ibrahimdemir.moviesapp.base

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ibrahimdemir.moviesapp.extensions.createProgressDialog
import com.ibrahimdemir.moviesapp.extensions.fullScreen
import com.ibrahimdemir.moviesapp.extensions.hideKeyboard

abstract class BaseActivity : AppCompatActivity() {

    private var blockingPane: Dialog? = null

    private var blockingOperationCount = 0

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if (savedInstanceState == null) {
            initView()
        }
        fullScreen()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    v.hideKeyboard()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun showProgressBar() {
        if (blockingOperationCount == 0) {
            if (blockingPane == null) {
                blockingPane = createProgressDialog()
            }

            blockingPane?.let {
                if (!it.isShowing) {
                    it.show()
                }
            }
        }
        blockingOperationCount += 1
    }

    fun hideProgressBar() {
        blockingOperationCount -= 1
        if (blockingOperationCount == 0) {
            blockingPane?.dismiss()
            blockingPane = null
        }
    }
}