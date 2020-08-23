package `in`.catat.presentation.search

import `in`.catat.R
import `in`.catat.presentation.note.NoteActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.catat.uikit.data.CatatanMenuModel
import id.catat.uikit.dialog.CatatinMenuDialog
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(R.layout.activity_search) {
    private val catatanMenu by lazy {
        listOf(
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_notes)),
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_todo)),
            CatatanMenuModel(
                title = getString(R.string.dialog_title_menu_draw),
                description = getString(R.string.dialog_text_menu_premium),
                isPremiumContent = true
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        search_button_add.setOnClickListener {
            CatatinMenuDialog(
                context = this@SearchActivity,
                title = getString(R.string.dialog_title_menu_add),
                dataMenu = catatanMenu,
                onMenuClick = { pos, data ->
                    startActivity(Intent(this, NoteActivity::class.java))
                }
            ).show()
        }
    }
}