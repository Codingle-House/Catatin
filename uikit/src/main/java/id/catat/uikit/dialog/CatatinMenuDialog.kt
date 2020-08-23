package id.catat.uikit.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.catat.uikit.R
import id.catat.uikit.adapter.GenericRecyclerViewAdapter
import id.catat.uikit.data.CatatanMenuModel
import id.catat.uikit.util.DiffCallback
import kotlinx.android.synthetic.main.dialog_item_menu_catatin.view.*
import kotlinx.android.synthetic.main.dialog_menu_catatin.*

/**
 * Created by pertadima on 23,August,2020
 */

class CatatinMenuDialog(
    context: Context,
    private val title: String,
    private val dataMenu: List<CatatanMenuModel> = listOf()
) : Dialog(context, R.style.DialogSlideAnim) {

    private val diffCallback by lazy {
        DiffCallback()
    }

    private val menuAdapter by lazy {
        GenericRecyclerViewAdapter<CatatanMenuModel>(
            diffCallback = diffCallback,
            holderResId = R.layout.dialog_item_menu_catatin,
            onBind = { data, _, view ->
                view.dialog_textview_menu_item_title.text = data.title
                view.dialog_textview_menu_item_description.text = data.description
                view.dialog_imageview_lock.isVisible = data.isPremiumContent
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDialog()
        setContentView(R.layout.dialog_menu_catatin)
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

    private fun setupDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent);
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}