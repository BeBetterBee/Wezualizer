package hccn.adastragrp.com.wezualizer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import hccn.adastragrp.com.wezualizer.ar.ArActivity
import hccn.adastragrp.com.wezualizer.ar.ArActivity.Companion.KEY_AR_GRAPH_BAR_COLOR
import hccn.adastragrp.com.wezualizer.ar.ArActivity.Companion.KEY_AR_GRAPH_DATA
import hccn.adastragrp.com.wezualizer.ar.ArActivity.Companion.KEY_AR_GRAPH_TITLE
import hccn.adastragrp.com.wezualizer.ar.rendermodel.ParcelableVector3
import petrov.kristiyan.colorpicker.ColorPicker



class MainActivity : AppCompatActivity() {
    lateinit var edtValues: EditText
    lateinit var edtTitle: EditText
    lateinit var btnColor: Button

    private var colorGraph:Int = 1
    private val DIALOG_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        colorGraph = getColor(R.color.pink)
        val btn = findViewById<Button>(R.id.btn_start_ar)
        btnColor = findViewById<Button>(R.id.btn_set_color)
        btnColor.setBackgroundColor( colorGraph)
        edtValues = findViewById<EditText>(R.id.edt_values)
        edtTitle = findViewById<EditText>(R.id.edt_title)
        btnColor.setOnClickListener{
            showColorPicker()
        }
        btn.setOnClickListener { startAr() }
    }

    private fun showColorPicker(){


        val colorPicker = ColorPicker(this)
        colorPicker.show()
        colorPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
            override fun onChooseColor(position: Int, color: Int) {
            colorGraph = color
                btnColor.setBackgroundColor( colorGraph)
            }

            override fun onCancel() {
                // put code
            }
        })
    }

    private fun startAr() {
        var values = edtValues.text.toString().split("\n")


        val array = ArrayList<ParcelableVector3>()
        for (value in values) {
            array.add(ParcelableVector3(p1 = value.toFloat() / 10))
        }

        val intent = Intent(this, ArActivity::class.java)
        intent.putExtra(KEY_AR_GRAPH_DATA, array)
        intent.putExtra(KEY_AR_GRAPH_TITLE, edtTitle.text.toString())
        intent.putExtra(KEY_AR_GRAPH_BAR_COLOR, colorGraph)
        startActivity(intent)
    }
}
