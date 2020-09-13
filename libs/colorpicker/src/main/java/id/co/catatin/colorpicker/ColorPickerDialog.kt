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
import android.view.View
import android.widget.LinearLayout
import id.catat.uikit.dialog.BaseCatatanDialog
import kotlin.math.roundToInt

class ColorPickerDialog(
    context: Context,
    private val initialColor: Int
) : BaseCatatanDialog(context), ColorPickerView.OnColorChangedListener, View.OnClickListener {
    private var colorPicker: ColorPickerView? = null
    private var oldColor: ColorPickerPanelView? = null
    private var newColor: ColorPickerPanelView? = null
    private var listener: OnColorChangedListener? = null

    interface OnColorChangedListener {
        fun onColorChanged(color: Int)
    }

    val color: Int
        get() = colorPicker!!.color

    override fun onClick(v: View) {
        if (v.id == R.id.new_color_panel) {
            if (listener != null) {
                listener!!.onColorChanged(newColor!!.color)
            }
        }
        dismiss()
    }

    override fun setupLayout() {
        setContentView(R.layout.dialog_color_picker)
    }

    override fun onCreateDialog() {
        init(initialColor)
    }

    private fun init(color: Int) {
        setUp(color)
    }

    private fun setUp(color: Int) {
        colorPicker = findViewById<View>(R.id.color_picker_view) as ColorPickerView
        oldColor = findViewById<View>(R.id.old_color_panel) as ColorPickerPanelView
        newColor = findViewById<View>(R.id.new_color_panel) as ColorPickerPanelView
        (oldColor?.parent as LinearLayout).setPadding(
            colorPicker?.drawingOffset?.roundToInt() ?: 0,
            0,
            colorPicker?.drawingOffset?.roundToInt() ?: 0,
            0
        )
        oldColor?.setOnClickListener(this)
        newColor?.setOnClickListener(this)
        colorPicker?.setOnColorChangedListener(this)
        oldColor?.color = color
        colorPicker?.setColor(color, true)
    }

    override fun onColorChanged(color: Int) {
        newColor?.color = color
    }

    fun setOnColorChangedListener(listener: OnColorChangedListener?) {
        this.listener = listener
    }
}