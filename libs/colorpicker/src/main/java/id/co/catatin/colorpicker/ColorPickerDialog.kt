/*
 * Copyright (C) 2010 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package id.co.catatin.colorpicker

import android.content.Context
import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import id.catat.uikit.dialog.BaseCatatanDialog
import java.lang.String

class ColorPickerDialog(
    context: Context,
    private val initialColor: Int,
    private val onColorChange: (Int?) -> Unit
) : BaseCatatanDialog(context), ColorPickerView.OnColorChangedListener {

    private var colorPicker: ColorPickerView? = null
    private var newColor: ColorPickerPanelView? = null
    private var hexCode: TextView? = null
    private var pickColor: Button? = null

    val color: Int?
        get() = colorPicker?.color

    override fun setupLayout() {
        setContentView(R.layout.dialog_color_picker)
    }

    override fun onCreateDialog() {
        init(initialColor)
        setupNewColorActionListener()
    }

    private fun init(color: Int) {
        setUp(color)
        newColor?.borderColor = Color.BLACK
    }

    private fun setUp(color: Int) {
        colorPicker = findViewById(R.id.catatin_colorpicker_color)
        newColor = findViewById(R.id.catatin_colorpanel_newcolor)
        hexCode = findViewById(R.id.catatin_textview_hexcolor)
        pickColor = findViewById(R.id.catatin_button_pick)

        colorPicker?.setOnColorChangedListener(this)
        newColor?.color = color
        hexCode?.text = newColor?.color?.convertColorToHex().toString()
        colorPicker?.setColor(color, true)
    }

    private fun setupNewColorActionListener() {
        pickColor?.setOnClickListener {
            onColorChange.invoke(newColor?.color)
            dismiss()
        }
    }

    override fun onColorChanged(color: Int) {
        newColor?.color = color
        hexCode?.text = newColor?.color?.convertColorToHex().toString()
    }

    private fun Int.convertColorToHex(): kotlin.String {
        return String.format("#%06X", 0xFFFFFF and this)
    }
}