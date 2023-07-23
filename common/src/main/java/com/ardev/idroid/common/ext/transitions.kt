package com.ardev.idroid.common.ext

import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialContainerTransform.FADE_MODE_IN

fun getTransform(mStartView: View, mEndView: View): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
      startView = mStartView
      endView = mEndView
      addTarget(mEndView)
      setPathMotion(MaterialArcMotion())
      setFadeMode(FADE_MODE_IN)
      duration = 550
      scrimColor = Color.TRANSPARENT
    }
  }