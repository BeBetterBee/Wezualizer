package beebetterbee.com.wezualizer.ar.rendermodel


import android.os.Parcelable
import com.google.ar.sceneform.math.Vector3
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class GraphData(val array: ArrayList<ParcelableVector3>, val barColor: Int,val titleColor:Int, val graphTitle: String) : Parcelable