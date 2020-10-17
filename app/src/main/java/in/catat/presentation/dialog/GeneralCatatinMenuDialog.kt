package `in`.catat.presentation.dialog

import `in`.catat.R
import `in`.catat.data.dto.CatatinMenuDto
import android.content.Context
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.catat.uikit.adapter.GenericRecyclerViewAdapter
import id.catat.uikit.dialog.BaseCatatanDialog
import id.co.catatin.core.commons.DiffCallback
import kotlinx.android.synthetic.main.dialog_item_menu_catatin.view.*
import kotlinx.android.synthetic.main.dialog_menu_catatin.*

/**
 * Created by pertadima on 23,August,2020
 */

class GeneralCatatinMenuDialog(
    context: Context,
    private val diffCallback: DiffCallback,
    private val title: String,
    private val dataMenu: MutableList<CatatinMenuDto> = mutableListOf(),
    private val onMenuClick: (Int, CatatinMenuDto) -> Unit
) : BaseCatatanDialog(context) {

    private val menuAdapter by lazy {
        GenericRecyclerViewAdapter<CatatinMenuDto>(
            diffCallback = diffCallback,
            holderResId = R.layout.dialog_item_menu_catatin,
            onBind = { data, pos, view ->
                view.dialog_textview_menu_item_title.text = context.getString(data.title)
                with(view.dialog_textview_menu_item_description) {
                    isGone = context.getString(data.description).isEmpty()
                    text = context.getString(data.description)
                }
                view.dialog_imageview_lock.isVisible = data.isPremiumContent
                view.dialog_view_line.isGone = pos == dataMenu.size - 1
            },
            itemListener = { data, pos, _ ->
                onMenuClick.invoke(pos, data)
                dismiss()
            }
        )
    }

    override fun setupLayout() {
        setContentView(R.layout.dialog_menu_catatin)
    }

    override fun onCreateDialog() {
        setupView()
        setupRecyclerView()
    }

    private fun setupView() {
        dialog_textview_menu_title.text = title
    }

    private fun setupRecyclerView() {
        with(dialog_recyclerview_menu) {
            adapter = menuAdapter.apply {
                setData(dataMenu)
            }
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun setData(menus: List<CatatinMenuDto>) {
        with(dataMenu) {
            clear()
            addAll(menus)
        }
        menuAdapter.setData(dataMenu)
    }
}