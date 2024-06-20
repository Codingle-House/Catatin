package id.catat.uikit.pinview

import Catatin.R
import Catatin.databinding.ItemPinIndicatorBinding
import Catatin.databinding.ItemPinNumpadBinding
import Catatin.databinding.ViewPinviewBinding
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.core.view.isGone
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.catat.uikit.adapter.GenericRecyclerViewAdapter
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.commons.EqualSpaceItemDecoration
import id.co.catatin.core.ext.getDrawableCompat
import id.co.catatin.core.ext.toDp

/**
 * Created by pertadima on 18,October,2020
 */

class CatatinPinView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {

    private val binding by lazy {
        ViewPinviewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val maximalIndicator: Int = 6
    private var currentIndicator: Int = -1

    private val diffCallback by lazy { DiffCallback() }

    private var onPinDone: (PinAction) -> Unit = { _ -> kotlin.run { } }

    private val indicatorAdapter by lazy {
        GenericRecyclerViewAdapter<NumpadIndicatorDto, ItemPinIndicatorBinding>(
            diffCallback = diffCallback,
            bindingInflater = { inflater, viewGroup, attachToParent ->
                ItemPinIndicatorBinding.inflate(inflater, viewGroup, attachToParent)
            },
            onBind = ::bindNumpadIndicator
        )
    }

    private val numpadAdapter by lazy {
        GenericRecyclerViewAdapter(
            diffCallback = diffCallback,
            bindingInflater = { inflater, viewGroup, attachToParent ->
                ItemPinNumpadBinding.inflate(inflater, viewGroup, attachToParent)
            },
            onBind = ::bindNumpadAdapter,
            fadeAnimation = false,
            itemListener = ::itemListenerNumpadAdapter
        )
    }

    private val indicatorList by lazy {
        val indicators = mutableListOf<NumpadIndicatorDto>()
        for (x in 0 until maximalIndicator) indicators.add(NumpadIndicatorDto())
        indicators
    }

    private val numpadList by lazy {
        resources.getStringArray(R.array.numpad).toList().map {
            NumpadDto(text = it)
        }.toMutableList().apply {
            add(NumpadDto(isDelete = true))
        }
    }

    init {
        setupRecyclerView()
        binding.pinTextviewForgotpassword.setOnClickListener {
            onPinDone.invoke(PinAction.OnForgotPassword)
        }
    }

    private fun setupRecyclerView() {
        with(binding.pinRecyclerviewNumber) {
            adapter = numpadAdapter.apply {
                setData(numpadList)
            }
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(EqualSpaceItemDecoration(spaceHeight = context.toDp(5F)))
        }

        with(binding.pinRecyclerviewIndicator) {
            adapter = indicatorAdapter.apply {
                setData(indicatorList)
            }
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            addItemDecoration(EqualSpaceItemDecoration(spaceHeight = context.toDp(5F)))
        }
    }


    private fun bindNumpadAdapter(data: NumpadDto, pos: Int, view: ItemPinNumpadBinding) {
        with(view.numpadTextviewNumber) {
            text = data.text
            isGone = data.isDelete
        }

        with(view.numpadImageviewDelete) {
            isGone = data.isDelete.not()
        }
    }

    private fun bindNumpadIndicator(data: NumpadIndicatorDto, pos: Int, view: ItemPinIndicatorBinding) {
        with(view.pinImageviewIndicator) {
            setImageDrawable(
                context.getDrawableCompat(
                    if (data.isSelected) R.drawable.uikit_ic_selected else R.drawable.uikit_ic_circle_outline
                )
            )
            animate().apply {
                scaleX(if (data.isSelected) INDICATOR_SIZE_SELECTED else INDICATOR_SIZE_DEFAULT)
                scaleY(if (data.isSelected) INDICATOR_SIZE_SELECTED else INDICATOR_SIZE_DEFAULT)
            }.duration = INDICATOR_DURATION
        }
    }

    private fun itemListenerNumpadAdapter(data: NumpadDto, pos: Int, view: ItemPinNumpadBinding) {
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
            with(binding.pinCardviewError) {
                if (isGone.not()) isGone = true
            }
        } else {
            if (currentIndicator == indicatorList.size - 1) {
                return
            }
            currentIndicator = currentIndicator.inc()
            val newIndicatorList =
                indicatorAdapter.getItem().mapIndexed { index, numpadIndicatorDto ->
                    NumpadIndicatorDto(if (numpadIndicatorDto.isSelected) true else index == currentIndicator)
                }
            indicatorAdapter.setNotifyItemChanged(newIndicatorList, currentIndicator)
            if (indicatorAdapter.getItem().filter { indicator ->
                    indicator.isSelected
                }.size == indicatorList.size
            ) {
                onPinDone.invoke(PinAction.OnPinDone)
            }
        }
    }

    fun showErrorMessage(error: String) {
        binding.pinTextviewError.text = error
        val shakeAnim = AnimationUtils.loadAnimation(context, R.anim.shake_animation)
        with(binding.pinCardviewError) {
            isGone = false
            startAnimation(shakeAnim)
        }
    }

    fun bindView(title: String, description: String) {
        binding.pinTextviewTitle.text = title
        binding.pinTextviewDescription.text = description
    }

    fun setListener(onPinDone: (PinAction) -> Unit) {
        this.onPinDone = onPinDone
    }

    fun hideForgotPinView() {
        binding.pinTextviewForgotpassword.isGone = true
    }

    internal data class NumpadIndicatorDto(
        val isSelected: Boolean = false
    )

    internal data class NumpadDto(
        val text: String = "",
        val isDelete: Boolean = false
    )

    sealed class PinAction {
        object OnPinDone : PinAction()
        object OnForgotPassword : PinAction()
    }

    companion object {
        private const val INDICATOR_SIZE_SELECTED = 1F
        private const val INDICATOR_SIZE_DEFAULT = 0.7F
        private const val INDICATOR_DURATION = 500L
    }
}