package `in`.catat.presentation.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.catat.uikit.adapter.GenericRecyclerViewAdapter
import id.catat.uikit.dialog.BaseCatatanDialog
import id.co.catatin.core.commons.DiffCallback
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.databinding.DialogItemMenuCatatinBinding
import `in`.catat.databinding.DialogMenuCatatinBinding

/**
 * Created by pertadima on 23,August,2020
 */

class GeneralCatatinMenuDialog(
    context: Context,
    private val diffCallback: DiffCallback,
    private val title: String,
    private val dataMenu: MutableList<CatatinMenuDto> = mutableListOf(),
    private val onMenuClick: (Int, CatatinMenuDto) -> Unit
) : BaseCatatanDialog<DialogMenuCatatinBinding>(context) {

    override val bindingInflater: (LayoutInflater) -> DialogMenuCatatinBinding
        get() = DialogMenuCatatinBinding::inflate

    private val menuAdapter by lazy {
        GenericRecyclerViewAdapter<CatatinMenuDto, DialogItemMenuCatatinBinding>(
            diffCallback = diffCallback,
            bindingInflater = { inflater, viewGroup, attachToParent ->
                DialogItemMenuCatatinBinding.inflate(inflater, viewGroup, attachToParent)
            },
            onBind = ::menuBindView,
            itemListener = ::menuItemListener
        )
    }

    override fun onCreateDialog() {
        setupView()
        setupRecyclerView()
    }

    private fun setupView() {
        binding.dialogTextviewMenuTitle.text = title
    }

    private fun setupRecyclerView() = with(binding.dialogRecyclerviewMenu) {
        adapter = menuAdapter.apply { setData(dataMenu) }
        layoutManager = LinearLayoutManager(context)
    }

    fun setData(menus: List<CatatinMenuDto>) {
        with(dataMenu) {
            clear()
            addAll(menus)
        }
        menuAdapter.setData(dataMenu)
    }

    private fun menuBindView(data: CatatinMenuDto, pos: Int, view: DialogItemMenuCatatinBinding) {
        view.dialogTextviewMenuItemTitle.text = context.getString(data.title)
        with(view.dialogTextviewMenuItemDescription) {
            isGone = context.getString(data.description).isEmpty()
            text = context.getString(data.description)
        }
        view.dialogImageviewLock.isVisible = data.isPremiumContent
        view.dialogViewLine.isGone = pos == dataMenu.size - 1
    }

    private fun menuItemListener(data: CatatinMenuDto, pos: Int, view: DialogItemMenuCatatinBinding) {
        onMenuClick.invoke(pos, data)
        dismiss()
    }
}