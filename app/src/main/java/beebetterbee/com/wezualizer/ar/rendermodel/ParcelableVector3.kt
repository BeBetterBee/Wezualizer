package beebetterbee.com.wezualizer.ar.rendermodel

import android.os.Parcelable
import com.google.ar.sceneform.math.Vector3
import kotlinx.android.parcel.Parcelize

@Parcelize
class ParcelableVector3(val p0: Float = 0.2f,val  p1: Float,val p2: Float = 0.2f) : Vector3(p0, p1, p2), Parcelable {
}