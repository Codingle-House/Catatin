package `in`.catat.presentation.home

import `in`.catat.R
import `in`.catat.presentation.note.NoteActivity
import `in`.catat.presentation.search.SearchActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.catat.uikit.data.CatatanMenuModel
import id.catat.uikit.dialog.CatatinMenuDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
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
        setupView()
        setupListener()
    }

    private fun setupView() {
        home_textview_greeting.text = getString(R.string.general_text_hallo, showWelcomeMessage())
    }

    private fun setupListener() {
        home_imageview_search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        home_button_add.setOnClickListener {
            CatatinMenuDialog(
                context = this@MainActivity,
                title = getString(R.string.dialog_title_menu_add),
                dataMenu = catatanMenu,
                onMenuClick = { pos, data ->
                    startActivity(Intent(this, NoteActivity::class.java))
                }
            ).show()
        }
    }

    private fun showWelcomeMessage(): String {
        val calendar = Calendar.getInstance()
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        return getString(
            when (timeOfDay) {
                in GOOD_MORNING_RANGE -> R.string.general_text_good_morning
                in GOOD_MORNING_AFTERNOON -> R.string.general_text_good_afternoon
                in GOOD_MORNING_EVENING -> R.string.general_text_good_evening
                else -> R.string.general_text_good_night
            }
        )
    }

    companion object {
        private val GOOD_MORNING_RANGE = 0..11
        private val GOOD_MORNING_AFTERNOON = 12..15
        private val GOOD_MORNING_EVENING = 16..19
    }
}