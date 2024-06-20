package `in`.catat.presentation.dialog.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import id.catat.uikit.dialog.BaseCatatanDialog
import id.co.catatin.core.commons.DiffCallback
import `in`.catat.data.dto.CatatinFilterMenuDto
import `in`.catat.databinding.DialogFilterCatatinBinding


/**
 * Created by pertadima on 26,October,2020
 */

class GeneralCatatinFilterMenuDialog(
    context: Context,
    private val diffCallback: DiffCallback,
    private val onFilterSelected: (MutableList<CatatinFilterMenuDto>) -> Unit
) : BaseCatatanDialog<DialogFilterCatatinBinding>(context) {

    override val bindingInflater: (LayoutInflater) -> DialogFilterCatatinBinding
        get() = DialogFilterCatatinBinding::inflate

    private var dataMenu: MutableList<CatatinFilterMenuDto> = mutableListOf()

    private val filterAdapter by lazy {
        GeneralCacatinFilterMenuAdapter(
            context = context,
            diffCallback = diffCallback,
            itemListener = ::filterItemListener
        )
    }

    override fun onCreateDialog() {
        setupRecyclerView()
        setupListener()
    }

    private fun setupListener() = binding.dialogButtonFilter.setOnClickListener {
        dismiss()
    }

    private fun setupRecyclerView() = with(binding.dialogRecyclerviewMenufilter) {
        adapter = filterAdapter.apply {
            setData(dataMenu)
            setHasStableIds(true)
        }
        layoutManager = LinearLayoutManager(context)
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