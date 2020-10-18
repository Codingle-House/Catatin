package `in`.catat.presentation.pin

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.NumpadDto
import `in`.catat.data.dto.NumpadIndicatorDto
import android.view.View
import androidx.core.view.isGone
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.catat.uikit.adapter.GenericRecyclerViewAdapter
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.commons.EqualSpaceItemDecoration
import id.co.catatin.core.ext.getDrawableCompat
import kotlinx.android.synthetic.main.activity_pin.*
import kotlinx.android.synthetic.main.item_pin_indicator.view.*
import kotlinx.android.synthetic.main.item_pin_numpad.view.*
import javax.inject.Inject

/**
 * Created by pertadima on 18,October,2020
 */

@AndroidEntryPoint
class PinActivity : BaseActivity(R.layout.activity_pin) {
    @Inject
    lateinit var diffCallback: DiffCallback

    private var currentIndicator: Int = -1

    private val indicatorList by lazy {
        listOf(
            NumpadIndicatorDto(),
            NumpadIndicatorDto(),
            NumpadIndicatorDto(),
            NumpadIndicatorDto(),
            NumpadIndicatorDto(),
            NumpadIndicatorDto()
        )
    }
    private val indicatorAdapter by lazy {
        GenericRecyclerViewAdapter<NumpadIndicatorDto>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_pin_indicator,
            onBind = ::bindNumpadIndicator
        )
    }

    private val numpadAdapter by lazy {
        GenericRecyclerViewAdapter<NumpadDto>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_pin_numpad,
            onBind = ::bindNumpadAdapter,
            fadeAnimation = false,
            itemListener = ::itemListenerNumpadAdapter
        )
    }

    override fun onViewCreated() {
        setupRecyclerView()
    }

    override fun onViewModelObserver() {

    }

    private fun setupRecyclerView() {
        with(pin_recyclerview_number) {
            adapter = numpadAdapter.apply {
                setData(
                    listOf(
                        NumpadDto(text = "1"),
                        NumpadDto(text = "2"),
                        NumpadDto(text = "3"),
                        NumpadDto(text = "4"),
                        NumpadDto(text = "5"),
                        NumpadDto(text = "6"),
                        NumpadDto(text = "7"),
                        NumpadDto(text = "8"),
                        NumpadDto(text = "9"),
                        NumpadDto(text = ""),
                        NumpadDto(text = "0"),
                        NumpadDto(isDelete = true)
                    )
                )
            }
            layoutManager = GridLayoutManager(this@PinActivity, 3)
            addItemDecoration(EqualSpaceItemDecoration())
        }

        with(pin_recyclerview_indicator) {
            adapter = indicatorAdapter.apply {
                setData(indicatorList)
            }
            layoutManager =
                LinearLayoutManager(this@PinActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(EqualSpaceItemDecoration(spaceHeight = 10))
        }
    }

    private fun bindNumpadAdapter(data: NumpadDto, pos: Int, view: View) {
        with(view.numpad_textview_number) {
            text = data.text
            isGone = data.isDelete
        }

        with(view.numpad_imageview_delete) {
            isGone = data.isDelete.not()
        }
    }

    private fun bindNumpadIndicator(data: NumpadIndicatorDto, pos: Int, view: View) {
        with(view.pin_imageview_indicator) {
            setImageDrawable(
                getDrawableCompat(
                    if (data.isSelected) {
                        R.drawable.pin_ic_selected
                    } else {
                        R.drawable.general_ic_circle_outline
                    }
                )
            )
            animate().apply {
                scaleX(if (data.isSelected) 1F else 0.7F)
                scaleY(if (data.isSelected) 1F else 0.7F)
            }.duration = 500
        }
    }

    private fun itemListenerNumpadAdapter(data: NumpadDto, pos: Int, view: View) {
        if (data.isDelete) {
            if (currentIndicator == -1) {
                return
            }
            val newIndicatorList =
                indicatorAdapter.getItem().mapIndexed { index, numpadIndicatorDto ->
                    NumpadIndicatorDto(if (numpadIndicatorDto.isSelected.not()) false else (index == currentIndicator).not())
                }
            indicatorAdapter.setNotifyItemChanged(newIndicatorList, currentIndicator)
            currentIndicator = currentIndicator.dec()
        } else {
            if (currentIndicator == 5) {
                return
            }
            currentIndicator = currentIndicator.inc()
            val newIndicatorList =
                indicatorAdapter.getItem().mapIndexed { index, numpadIndicatorDto ->
                    NumpadIndicatorDto(if (numpadIndicatorDto.isSelected) true else index == currentIndicator)
                }
            indicatorAdapter.setNotifyItemChanged(newIndicatorList, currentIndicator)
        }
    }
}