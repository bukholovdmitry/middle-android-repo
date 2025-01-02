package com.example.androidpracticumcustomview.ui.theme

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.androidpracticumcustomview.R

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (childCount > 0) {
            val width = right - left
            val height = bottom - top
            val childCount = childCount
            val elementHeight = height / 2
            val child = getChildAt(if (childCount == 1) 0 else 1)

            val translationYAnim = ObjectAnimator.ofFloat(child, "translationY", 0f)
            val objAnimatorVisibility = AnimatorInflater.loadAnimator(
                context,
                R.animator.animator_object_visibility
            ) as ObjectAnimator
            objAnimatorVisibility.duration = resources.getInteger(R.integer.alpha_duration).toLong()
            if (childCount == 1) {
                translationYAnim.setFloatValues(height / 4f, 0f)
            } else {
                translationYAnim.setFloatValues(-height / 4f, 0f)
            }
            translationYAnim.duration =
                resources.getInteger(R.integer.translation_duration).toLong()
            val animatorSet = AnimatorSet()
            animatorSet.apply {
                setTarget(child)
                playTogether(translationYAnim, objAnimatorVisibility)
                start()
            }
            val childWidth = child.measuredWidth
            val childLeft = (width - childWidth) / 2
            child.layout(
                childLeft,
                (childCount - 1) * elementHeight,
                childLeft + childWidth,
                childCount * elementHeight
            )
        }
    }

    override fun addView(child: View) {
        if (childCount < 2) {
            super.addView(child)
        } else {
            throw IllegalStateException(resources.getString(R.string.throw_message_many_childs))
        }
    }
}