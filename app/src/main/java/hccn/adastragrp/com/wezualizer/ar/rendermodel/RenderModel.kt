package hccn.adastragrp.com.wezualizer.ar.rendermodel


import android.os.Parcelable
import com.google.ar.sceneform.math.Vector3
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class RenderModel(val array:ArrayList<@RawValue Vector3>):Parcelable