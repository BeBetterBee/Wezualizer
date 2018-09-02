package hccn.adastragrp.com.wezualizer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import hccn.adastragrp.com.wezualizer.ar.ArActivity
import hccn.adastragrp.com.wezualizer.ar.ArActivity.Companion.KEY_AR_GRAPH_DATA
import hccn.adastragrp.com.wezualizer.ar.rendermodel.GraphData
import hccn.adastragrp.com.wezualizer.ar.rendermodel.ParcelableVector3
import hccn.adastragrp.com.wezualizer.databinding.MainActivityBinding
import hccn.adastragrp.com.wezualizer.ui.main.MainViewModel
import petrov.kristiyan.colorpicker.ColorPicker


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainActivityBinding

    private var colorGraphBar: Int = 1
    private var colorGraphTitle: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        lifecycle.addObserver(viewModel)
        binding.setVariable(BR.vm, viewModel)
        binding.executePendingBindings()


        colorGraphBar = getColor(R.color.pink)
        colorGraphTitle = getColor(R.color.primary_dark_material_light)
        binding.btnSetColorBar.setBackgroundColor(colorGraphBar)
        binding.btnSetColorTitle.setBackgroundColor(colorGraphTitle)

        binding.btnSetColorBar.setOnClickListener {
            showColorPicker(it as Button)
        }
        binding.btnSetColorTitle.setOnClickListener {
            showColorPicker(it as Button)
        }
        binding.btnStartAr.setOnClickListener { startAr() }
    }

    private fun showColorPicker(button: Button) {
        val colorPicker = ColorPicker(this)
        colorPicker.show()
        colorPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
            override fun onChooseColor(position: Int, color: Int) {
                button.setBackgroundColor(color)
                when(button.id){
                    R.id.btn_set_color_title -> colorGraphTitle = color
                    R.id.btn_set_color_bar -> colorGraphBar = color
                }
            }

            override fun onCancel() {
                // put code
            }
        })
    }

    private fun startAr() {
        var values = viewModel.graphValues.value?.split("\n")

        val array = ArrayList<ParcelableVector3>()
        if (values != null) {
            for (value in values) {
                array.add(ParcelableVector3(p1 = value.toFloat() / 10))
            }
        }

        val graphData = GraphData(array, colorGraphBar,colorGraphTitle, viewModel.graphTitle.value!!)

        val intent = Intent(this, ArActivity::class.java)
        intent.putExtra(KEY_AR_GRAPH_DATA, graphData)
        startActivity(intent)
    }
}
