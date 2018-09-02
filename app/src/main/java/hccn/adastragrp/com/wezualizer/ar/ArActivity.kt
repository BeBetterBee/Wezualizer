package hccn.adastragrp.com.wezualizer.ar

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import hccn.adastragrp.com.wezualizer.R
import hccn.adastragrp.com.wezualizer.ar.rendermodel.ParcelableVector3


class ArActivity : AppCompatActivity() {
    companion object {
        const val KEY_AR_GRAPH_DATA = "AR_DATA"
        const val KEY_AR_GRAPH_TITLE = "AR_TITLE"
        const val KEY_AR_GRAPH_BAR_COLOR = "AR_GRAPH_BAR_COLOR"
        const val KEY_AR_MAP_DATA = "AR_DATA"
    }

    lateinit var fragment: ArFragment
    lateinit var anchorNode: AnchorNode
    var arrayGraph: ArrayList<ParcelableVector3>? = null
    var arrayAnchors = ArrayList<AnchorNode>()
    var graphTitle = ""
    var barColor =  0

    private val OBJECT_INDENTATION = 0.4f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)
        supportActionBar?.hide()

        fragment = supportFragmentManager
                .findFragmentById(R.id.sceneform_fragment)
                as ArFragment
        arrayGraph = intent?.extras?.getParcelableArrayList(KEY_AR_GRAPH_DATA)
        graphTitle = intent?.extras?.getString(KEY_AR_GRAPH_TITLE).toString()
        barColor = intent?.extras?.getInt(KEY_AR_GRAPH_BAR_COLOR)!!

        fragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (arrayGraph != null) {
                if (::anchorNode.isInitialized) {
                    fragment = supportFragmentManager
                            .findFragmentById(R.id.sceneform_fragment)
                            as ArFragment
                }
                makeGraph(hitResult, barColor)
                makeNumbers(hitResult)
                makeNumbersXAxis(hitResult)
                makeGraphTitle(hitResult)
            }
        }
    }

    private fun makeGraphTitle(hitResult: HitResult) {
        ViewRenderable.builder()
                .setView(baseContext, R.layout.layout_graph_title)
                .build()
                .thenAccept { renderableResult ->
                    val textView = renderableResult.view as TextView
                    textView.text = graphTitle
                    showNode(hitResult.createAnchor(), TransformableNode(fragment.transformationSystem).apply {
                        renderable = renderableResult
                        localPosition = Vector3(-0.5f, 0.7f, 0f)
                    })
                }
    }

    private fun makeNumbers(hitResult: HitResult) {
        for (i in this!!.arrayGraph!!) {
            val position = (arrayGraph?.indexOf(i))

            ViewRenderable.builder()
                    .setView(baseContext, R.layout.layout_number_text)
                    .build()
                    .thenAccept { renderableResult ->
                        val textView = renderableResult.view as TextView
                        textView.text = (i.p1 * 10).toString()
                        showNode(hitResult.createAnchor(), TransformableNode(fragment.transformationSystem).apply {
                            renderable = renderableResult
                            localPosition = Vector3(position?.toFloat()!! * 0.25f, arrayGraph?.get(position)!!.p1 + 0.2f, 0f)
                        })
                    }
        }
    }

    private fun makeNumbersXAxis(hitResult: HitResult) {
        for (i in this!!.arrayGraph!!) {
            val position = (arrayGraph?.indexOf(i))

            ViewRenderable.builder()
                    .setView(baseContext, R.layout.layout_x_title)
                    .build()
                    .thenAccept { renderableResult ->
                        val textView = renderableResult.view as TextView
                        textView.text = (position?.toInt()?.plus(1)).toString()
                        showNode(hitResult.createAnchor(), TransformableNode(fragment.transformationSystem).apply {
                            renderable = renderableResult
                            localPosition = Vector3(position?.toFloat()!! * 0.25f, -0.1f, 0.4f)
                            localRotation = Quaternion.axisAngle(
                                    Vector3(1.0f, 0f, 0.0f), -90f)
                        })
                    }
        }
    }

    private fun makeGraph(hitResult: HitResult, color: Int) {
        MaterialFactory.makeOpaqueWithColor(this,
                com.google.ar.sceneform.rendering.Color(color))
                .thenAccept { material ->
                    for (i in this!!.arrayGraph!!) {
                        showNode(hitResult.createAnchor(), TransformableNode(fragment.transformationSystem).apply {
                            renderable = ShapeFactory.makeCube(i as Vector3, Vector3((arrayGraph?.indexOf(i))?.toFloat()!! * 0.25f, i.p1 / 2, 0.1f), material)
                        })
                    }
                }
    }

    private fun showNode(anchor: Anchor, nodes: TransformableNode) {
        val anchorNode = AnchorNode(anchor)
        nodes.setParent(anchorNode)
        nodes.select()
        fragment.arSceneView.scene.addChild(anchorNode)
    }
}
