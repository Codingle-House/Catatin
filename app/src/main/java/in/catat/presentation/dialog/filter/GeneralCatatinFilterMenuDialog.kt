package `in`.catat.presentation.dialog.filter

import `in`.catat.R
import `in`.catat.data.dto.CatatinFilterMenuDto
import `in`.catat.util.AnimationConstant.DEFAULT_ANIMATION_DURATION
import `in`.catat.util.AnimationConstant.FULL_SCALE
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val onFilterSelected: (MutableList<CatatinFilterMenuDto>) -> Unit
) : BaseCatatanDialog(context) {

    private var dataMenu: MutableList<CatatinFilterMenuDto> = mutableListOf()

    private val filterAdapter by lazy {
        GeneralCacatinFilterMenuAdapter(
            context = context,
            diffCallback = diffCallback,
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
        dataMenu = menus.toMutableList()
        filterAdapter.setData(dataMenu)
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

    override fun dismiss() {
        super.dismiss()
        onFilterSelected.invoke(dataMenu)
    }
}