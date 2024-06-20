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

import Catatin.databinding.DialogColorPickerBinding
import android.content.Context
import android.graphics.Color.BLACK
import android.view.LayoutInflater
import id.catat.uikit.dialog.BaseCatatanDialog

class ColorPickerDialog(
    context: Context,
    private val initialColor: Int,
    private val onColorChange: (Int?) -> Unit
) : BaseCatatanDialog<DialogColorPickerBinding>(context), ColorPickerView.OnColorChangedListener {

    override val bindingInflater: (LayoutInflater) -> DialogColorPickerBinding
        get() = DialogColorPickerBinding::inflate

    val color: Int?
        get() = binding.catatinColorpickerColor.color

    override fun onCreateDialog() {
        init(initialColor)
        setupNewColorActionListener()
    }

    private fun init(color: Int) {
        setUp(color)
        binding.catatinColorpanelNewcolor.borderColor = BLACK
    }

    private fun setUp(color: Int) = with(binding) {
        catatinColorpickerColor.setOnColorChangedListener(this@ColorPickerDialog)
        catatinColorpanelNewcolor.color = color
        catatinTextviewHexcolor.text = catatinColorpanelNewcolor.color.convertColorToHex()
        catatinColorpickerColor.setColor(color, true)
    }

    private fun setupNewColorActionListener() = with(binding) {
        catatinColorpickerColor.setOnClickListener {
            onColorChange.invoke(catatinColorpanelNewcolor.color)
            dismiss()
        }
    }

    override fun onColorChanged(color: Int) = with(binding) {
        catatinColorpanelNewcolor.color = color
        catatinTextviewHexcolor.text = catatinColorpanelNewcolor.color.convertColorToHex()
    }

    private fun Int.convertColorToHex(): String {
        return String.format("#%06X", 0xFFFFFF and this)
    }
}