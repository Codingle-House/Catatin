package `in`.catat.presentation.home

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinFilterMenuDto
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.UserNotesDto
import `in`.catat.data.enum.NoteStatusEnum
import `in`.catat.presentation.dialog.filter.GeneralCatatinFilterMenuDialog
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.note.NoteActivity
import `in`.catat.presentation.pin.LoginPinActivity
import `in`.catat.presentation.search.SearchActivity
import `in`.catat.presentation.settings.SettingsActivity
import `in`.catat.presentation.sketch.SketchActivity
import `in`.catat.presentation.todo.TodoActivity
import `in`.catat.util.AnimationConstant.DEFAULT_ANIMATION_DURATION
import `in`.catat.util.AnimationConstant.FULL_SCALE
import `in`.catat.util.AnimationConstant.HIDE_SCALE
import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.widget.TextView
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
import id.co.catatin.core.ext.setSpannableForegroundColor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_notes_card.view.*
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity(R.layout.activity_main) {

    @Inject
    lateinit var diffCallback: DiffCallback

    private val homeViewModel: HomeViewModel by viewModels()

    private val notesTypeDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@HomeActivity,
            diffCallback = diffCallback,
            title = getString(R.string.dialog_title_menu_add),
            onMenuClick = ::handleMenuDialogClick
        )
    }

    private val filterTypeDialog by lazy {
        GeneralCatatinFilterMenuDialog(
            context = this@HomeActivity,
            diffCallback = diffCallback,
            onFilterSelected = ::handleFilterListener
        )
    }

    private val notesAdapter by lazy {
        GenericRecyclerViewAdapter<UserNotesDto>(
            diffCallback = diffCallback,
            holderResId = R.layout.item_notes_card,
            onBind = ::bindNotesAdapter,
            itemListener = ::notesListener
        )
    }

    override fun onViewCreated() {
        setupToolbarView()
        setupListener()
        setupFilterListener()
        getData()
        setupRecyclerView()
        setupDefaultFilter()
    }

    override fun onViewModelObserver() {
        with(homeViewModel) {
            observeUserNotes().onResult {
                notesAdapter.setData(it)
                home_textview_content_title.styleContentTitle(it.size)
                home_viewflipper_content.displayedChild = if (it.isEmpty()) {
                    EMPTY_STATE
                } else {
                    AVAILABLE_STATE
                }
            }

            observeNotesTypes().onResult {
                notesTypeDialog.setData(it)
            }

            observeNotesFilter().onResult {
                filterTypeDialog.setData(it)
            }
        }
    }

    private fun getData() {
        homeViewModel.getUserNotes()
    }

    private fun setupDefaultFilter() {
        with(home_imageview_filter_indicator) {
            animate().apply {
                scaleX(HIDE_SCALE)
                scaleY(HIDE_SCALE)
            }.duration = 0
        }
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
                        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
                        true
                    }
                    R.id.home_action_setting -> {
                        startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
                        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
                        true
                    }
                    else -> super.onOptionsItemSelected(it)
                }
            }
        }
    }

    private fun setupListener() {
        home_button_add.setOnClickListener {
            notesTypeDialog.show()
        }
    }

    private fun setupFilterListener() {
        home_imageview_filter.setOnClickListener {
            filterTypeDialog.show()
        }
    }


    private fun handleMenuDialogClick(post: Int, data: CatatinMenuDto) {
        when (getString(data.title)) {
            getString(R.string.dialog_title_menu_notes) -> {
                startActivity(
                    Intent(this, NoteActivity::class.java).putExtra(
                        NoteActivity.NoteKey.STATUS, NoteStatusEnum.CREATE
                    )
                )
            }
            getString(R.string.dialog_title_menu_todo) -> {
                startActivity(
                    Intent(this, TodoActivity::class.java).putExtra(
                        TodoActivity.TodoKey.STATUS, NoteStatusEnum.CREATE
                    )
                )
            }
            else -> {
                startActivity(
                    Intent(this, SketchActivity::class.java).putExtra(
                        SketchActivity.SketchKey.STATUS, NoteStatusEnum.CREATE
                    )
                )
            }
        }
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
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

    private fun notesListener(data: UserNotesDto, pos: Int, view: View) {
        if (data.isLocked) {
            startActivity(
                Intent(this, LoginPinActivity::class.java)
            )
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
            return
        }

        when (data.type) {
            UserNotesDto.NoteType.NOTE -> startActivity(
                Intent(this, NoteActivity::class.java).putExtra(
                    NoteActivity.NoteKey.STATUS, NoteStatusEnum.EDIT
                )
            )
            UserNotesDto.NoteType.TODO -> startActivity(
                Intent(this, TodoActivity::class.java).putExtra(
                    TodoActivity.TodoKey.STATUS, NoteStatusEnum.EDIT
                )
            )
            UserNotesDto.NoteType.SKETCH -> startActivity(
                Intent(this, SketchActivity::class.java).putExtra(
                    SketchActivity.SketchKey.STATUS, NoteStatusEnum.EDIT
                )
            )
        }
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
    }

    private fun TextView.styleContentTitle(contentSize: Int) {
        val firstWord = SpannableString(
            getString(R.string.home_text_total_note).substringBefore(CONTENT_STYLE_DELIMITED)
        ).apply {
            setSpannableForegroundColor(this@HomeActivity)
        }
        text = firstWord
        val notesCount = SpannableString(contentSize.toString()).apply {
            setSpannableForegroundColor(this@HomeActivity, R.color.colorAccent)
        }
        append(notesCount)
        val secondWord: Spannable = SpannableString(
            getString(R.string.home_text_total_note).substringAfter(CONTENT_STYLE_DELIMITED)
        ).apply {
            setSpannableForegroundColor(this@HomeActivity)
        }

        append(secondWord)
    }

    private fun handleFilterListener(filteredMenu: MutableList<CatatinFilterMenuDto>) {
        val isShown = filteredMenu.any { it.isSelected }.not()
        homeViewModel.setNotesFilter(filteredMenu)
        with(home_imageview_filter_indicator) {
            animate().apply {
                scaleX(if (isShown.not()) FULL_SCALE else HIDE_SCALE)
                scaleY(if (isShown.not()) FULL_SCALE else HIDE_SCALE)
            }.duration = DEFAULT_ANIMATION_DURATION
        }
    }


    companion object {
        private val GOOD_MORNING_RANGE = 0..11
        private val GOOD_MORNING_AFTERNOON = 12..15
        private val GOOD_MORNING_EVENING = 16..19

        private const val EMPTY_STATE = 0
        private const val AVAILABLE_STATE = 1

        private const val GRID_SPAN_COUNT = 2

        private const val CONTENT_STYLE_DELIMITED = "%s"
    }
}