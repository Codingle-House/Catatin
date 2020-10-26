package `in`.catat.presentation.dialog

import `in`.catat.R
import `in`.catat.data.dto.CatatinFilterMenuDto
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.catat.uikit.adapter.GenericRecyclerViewAdapter
import id.catat.uikit.dialog.BaseCatatanDialog
import id.co.catatin.core.commons.DiffCallback
import kotlinx.android.synthetic.main.dialog_filter_catatin.*
import kotlinx.android.synthetic.main.item_notes_filter.view.*


/**
 * Created by pertadima on 26,October,2020
 */

class GeneralCatatinFilterMenuDialog(
    context: Context,
    private val diffCallback: DiffCallback,
    private val dataMenu: MutableList<CatatinFilterMenuDto> = mutableListOf(),
    private val onFilterSelected: (MutableList<CatatinFilterMenuDto>) -> Unit
) : BaseCatatanDialog(context) {

    private val filterAdapter by lazy {
        GenericRecyclerViewAdapter<CatatinFilterMenuDto>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_notes_filter,
            onBind = ::filterBindView,
            itemListener = ::filterItemListener
        )
    }

    override fun setupLayout() {
        setContentView(R.layout.dialog_filter_catatin)
    }

    override fun onCreateDialog() {
        setupRecyclerView()
        setupListener()
    }

    private fun setupListener() {
        dialog_button_filter.setOnClickListener {
            onFilterSelected.invoke(dataMenu)
            dismiss()
        }
    }

    private fun setupRecyclerView() {
        with(dialog_recyclerview_menufilter) {
            adapter = filterAdapter.apply {
                setData(dataMenu)
                setHasStableIds(true)
            }
            layoutManager = LinearLayoutManager(context)
        }
    }


    fun setData(menus: List<CatatinFilterMenuDto>) {
        with(dataMenu) {
            clear()
            addAll(menus)
        }
        filterAdapter.setData(dataMenu)
    }

    private fun filterBindView(data: CatatinFilterMenuDto, pos: Int, view: View) {
        view.dialog_textview_menufilter_item_title.text = context.getString(data.title)
        with(view.dialog_imageview_menufilter_check) {
            isVisible = data.isSelected
            animate().apply {
                scaleX(if (data.isSelected) 1F else 0F)
                scaleY(if (data.isSelected) 1F else 0F)
            }.duration = 400
        }
        view.dialog_view_menufilter_line.isVisible = pos != dataMenu.size - 1
    }

    private fun filterItemListener(data: CatatinFilterMenuDto, pos: Int, view: View) {
        val newDataMenu = mutableListOf<CatatinFilterMenuDto>()
        with(newDataMenu) {
            clear()
            addAll(dataMenu)
        }
        val selectedFilter = newDataMenu[pos].copy(
            isSelected = data.isSelected.not()
        )
        newDataMenu[pos] = selectedFilter
        setData(newDataMenu)
    }
}