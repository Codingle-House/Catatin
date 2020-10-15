package `in`.catat.presentation.home

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.UserNotesDto
import `in`.catat.data.model.CatatanMenuModel
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.note.NoteActivity
import `in`.catat.presentation.search.SearchActivity
import `in`.catat.presentation.settings.SettingsActivity
import `in`.catat.presentation.sketch.SketchActivity
import `in`.catat.presentation.todo.TodoActivity
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import id.catat.uikit.adapter.GenericRecyclerViewAdapter
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.commons.EqualSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_card_notes.view.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity(R.layout.activity_main) {

    @Inject
    lateinit var diffCallback: DiffCallback

    private val homeViewModel: HomeViewModel by viewModels()

    //TODO: IRFAN - MOVE TO INTERACTOR
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

    private val notesAdapter by lazy {
        GenericRecyclerViewAdapter<UserNotesDto>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_card_notes,
            onBind = ::bindNotesAdapter,
            itemListener = { data, pos, _ ->

            }
        )
    }

    override fun onViewCreated() {
        setupToolbarView()
        setupListener()
        getData()
        setupRecyclerView()
    }

    override fun onViewModelObserver() {
        with(homeViewModel) {
            observeUserNotes().onResult {
                notesAdapter.setData(it)

                home_textview_content_title.text =
                    getString(R.string.home_text_total_note, it.size.toString())

                home_viewflipper_content.displayedChild = if (it.isEmpty()) {
                    EMPTY_STATE
                } else {
                    AVAILABLE_STATE
                }
            }
        }
    }

    private fun getData() {
        homeViewModel.getUserNotes()
    }


    private fun setupRecyclerView() {
        with(home_recyclerview_notes) {
            adapter = notesAdapter
            layoutManager =
                StaggeredGridLayoutManager(GRID_SPAN_COUNT, LinearLayoutManager.VERTICAL)
            addItemDecoration(EqualSpaceItemDecoration())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 || dy < 0 && home_button_add.isShown) {
                        home_button_add.hide();
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        home_button_add.show();
                    }
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    private fun setupToolbarView() {
        with(home_toolbar) {
            title = getString(R.string.general_text_hallo, showWelcomeMessage())
            inflateMenu(R.menu.catatin_menu_home)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_action_search -> {
                        startActivity(Intent(this@HomeActivity, SearchActivity::class.java))
                        true
                    }
                    R.id.home_action_setting -> {
                        startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
                        true
                    }
                    else -> super.onOptionsItemSelected(it)
                }
            }
        }
    }

    private fun setupListener() {
        home_button_add.setOnClickListener {
            GeneralCatatinMenuDialog(
                context = this@HomeActivity,
                title = getString(R.string.dialog_title_menu_add),
                dataMenu = catatanMenu,
                onMenuClick = { _, data ->
                    handleMenuDialogClick(data)
                }
            ).show()
        }
    }


    private fun handleMenuDialogClick(data: CatatanMenuModel) {
        when (data.title) {
            getString(R.string.dialog_title_menu_notes) -> {
                startActivity(Intent(this, NoteActivity::class.java))
            }
            getString(R.string.dialog_title_menu_todo) -> {
                startActivity(Intent(this, TodoActivity::class.java))
            }
            else -> {
                startActivity(Intent(this, SketchActivity::class.java))
            }
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

    private fun bindNotesAdapter(data: UserNotesDto, pos: Int, view: View) {
        view.note_textview_notes_datetime.text = data.date
        view.note_textview_notes_title.text = data.title
        view.note_textview_notes_type.text = data.type
        view.note_textview_notes_islocked.isGone = data.isLocked.not()

        with(view.note_imageview_notes_image) {
            isGone = data.isLocked || data.image.isEmpty()
            load(data.image)
        }

        with(view.note_textview_notes_value) {
            text = data.description
            isGone = data.isLocked
        }
    }

    companion object {
        private val GOOD_MORNING_RANGE = 0..11
        private val GOOD_MORNING_AFTERNOON = 12..15
        private val GOOD_MORNING_EVENING = 16..19

        private const val EMPTY_STATE = 0
        private const val AVAILABLE_STATE = 1

        private const val GRID_SPAN_COUNT = 2
    }
}