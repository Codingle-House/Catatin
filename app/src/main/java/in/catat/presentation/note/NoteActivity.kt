package `in`.catat.presentation.note

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.InsertNoteDto
import `in`.catat.data.enum.NoteStatusEnum
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.util.DateUtil
import `in`.catat.util.RichTextItem
import `in`.catat.util.ShareUtil
import `in`.catat.util.constants.appConstant
import android.Manifest
import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isGone
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.showToast
import kotlinx.android.synthetic.main.activity_note.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


@AndroidEntryPoint
class NoteActivity : BaseActivity(R.layout.activity_note), EasyPermissions.PermissionCallbacks {
    @Inject
    lateinit var diffCallback: DiffCallback

    private val noteViewModel: NoteViewModel by viewModels()

    private val currentDate by lazy {
        DateUtil.getCurrentDate()
    }

    private val noteStatus by lazy {
        intent?.getSerializableExtra(NoteKey.STATUS) as NoteStatusEnum
    }

    private val noteId by lazy {
        intent?.getLongExtra(NoteKey.ID, 0L) ?: 0L
    }

    private val handler by lazy {
        Handler()
    }

    private val myRunnable = Runnable {
        note_adview_banner?.run {
            initializeAdMob()
            bringToFront()
            isGone = false
        }
    }

    private val settingsDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@NoteActivity,
            title = getString(R.string.general_text_setting),
            diffCallback = diffCallback,
            onMenuClick = ::handleMenuDialogClick
        )
    }

    private val attachmentDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@NoteActivity,
            title = getString(R.string.dialog_title_menu_attachment),
            diffCallback = diffCallback,
            onMenuClick = ::handleMenuAttachmentDialogClick
        )
    }

    private var scrollerAtEnd = false
    private var isFullScreen = false

    private var createdAt = ""

    override fun onViewCreated() {
        getNoteData()
        initRichTextView()
        setupAppToolbar()
        setupView()
        setupToolbarArrow()
        setupAdMob()
    }

    private fun getNoteData() {
        if (noteStatus == NoteStatusEnum.EDIT) {
            noteViewModel.getSingleNote(noteId)
        }
    }

    override fun onViewModelObserver() {
        with(noteViewModel) {
            observeAttachmentMenu().onResult {
                attachmentDialog.setData(it)
            }

            observeSettingsMenu().onResult {
                settingsDialog.setData(it)
            }

            observeSingleNote().onResult {
                createdAt = it.createdAt
                note_edittext_title.setText(it.title)
                note_richtext_form.fromHtml(it.content)
            }
        }
    }

    private fun setupAdMob() {
        handler.postDelayed(myRunnable, ADMOB_DELAY)
    }

    private fun setupAppToolbar() {
        with(note_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            inflateMenu(R.menu.catatin_menu_note)
            menu.findItem(R.id.note_menu_delete).isVisible = noteStatus == NoteStatusEnum.EDIT
            setOnMenuItemClickListener { handleMenuClick(it) }
        }
    }

    private fun setupView() {
        note_textview_description.text =
            getString(
                R.string.note_text_description,
                currentDate,
                note_richtext_form.length().toString()
            )

        note_richtext_form.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                note_textview_description.text =
                    getString(R.string.note_text_description, currentDate, s.length.toString())
            }
        })
    }

    private fun initRichTextView() {
        val toolItems = RichTextItem.richTextDefaultItem(this)
        note_richtext_toolbar.addToolbarItems(toolItems)
        note_richtext_form.setToolbar(note_richtext_toolbar)
    }

    private fun setupToolbarArrow() {
        if (note_richtext_toolbar is ARE_ToolbarDefault) {
            note_richtext_toolbar.viewTreeObserver.addOnScrollChangedListener {
                val scrollX = note_richtext_toolbar.scrollX
                val scrollWidth = note_richtext_toolbar.width
                val fullWidth = note_richtext_toolbar.getChildAt(0).width

                note_imageview_arrow.rotateAnimation(scrollX + scrollWidth < fullWidth)
            }
        }
        note_imageview_arrow.setOnClickListener {
            val fullWidth = note_richtext_toolbar.getChildAt(0).width

            note_richtext_toolbar.smoothScrollBy(
                if (scrollerAtEnd) -Integer.MAX_VALUE else fullWidth,
                0
            )
        }
    }

    private fun handleMenuAttachmentDialogClick(post: Int, data: CatatinMenuDto) {
        when (getString(data.title)) {
            getString(R.string.dialog_title_menu_file) -> checkStoragePermission {
                val intent = Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT)

                //TODO: CHANGE TO NEW API
                startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
            }
            else -> {

            }
        }
    }

    private fun handleMenuDialogClick(post: Int, data: CatatinMenuDto) {
        when (getString(data.title)) {
            getString(R.string.dialog_title_menu_fullscreen) -> handleFullScreen()
            getString(R.string.dialog_title_menu_alarm) -> {

            }
            getString(R.string.dialog_title_menu_share) -> shareActionSetting()
            getString(R.string.dialog_title_menu_lock) -> {

            }
            getString(R.string.dialog_title_menu_copy) -> {

            }
            getString(R.string.dialog_title_menu_focus) -> {

            }
            else -> {

            }
        }
    }

    private fun handleFullScreen() {
        note_appbar.isGone = true
        note_edittext_title.isGone = true
        isFullScreen = true
        showToast(R.string.general_text_fullscreen_close)
    }

    override fun onBackPressed() {
        if (isFullScreen) {
            isFullScreen = false
            note_appbar.isGone = false
            note_edittext_title.isGone = false
        } else {
            finish()
        }
    }

    private fun showDeleteDialog() {
        GeneralCatatinDialog(
            context = this,
            image = R.drawable.notes_ic_delete,
            title = getString(R.string.general_title_delete_note),
            description = getString(R.string.general_text_delete_note),
            yesTextButton = getString(R.string.general_text_yes),
            yesClickListener = {
                noteViewModel.doDeleteSingleNote(noteId)
                finish()
            },
            noTextButton = getString(R.string.general_text_no)
        ).show()
    }

    override fun onPause() {
        super.onPause()
        actionSave()
    }

    private fun shareActionSetting() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = ShareUtil.PLAIN_TYPE
            intent.putExtra(Intent.EXTRA_SUBJECT, note_edittext_title.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, note_richtext_form.text.toString())
        }

        startActivity(Intent.createChooser(intent, ShareUtil.CHOOSER))
    }

    private fun View.rotateAnimation(isNextPage: Boolean) {
        scrollerAtEnd = !isNextPage
        animate()
            .setDuration(ANIMATION_DURATION)
            .rotation(if (!isNextPage) LINEAR_ROTATION else DEFAULT_ROTATION)
    }

    private fun handleMenuClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.note_menu_setting -> {
                settingsDialog.show()
                true
            }
            R.id.note_menu_attachment -> {
                attachmentDialog.show()
                true
            }
            R.id.note_menu_delete -> {
                showDeleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    private fun actionSave() {
        val title = note_edittext_title.text.toString()
        val content = note_richtext_form.text.toString()
        if (title.isEmpty() && content.isEmpty()) return

        val noteDto = InsertNoteDto(
            title = note_edittext_title.text.toString(),
            content = note_richtext_form.html.toString(),
            type = appConstant.TYPE_NOTE,
            createdAt = createdAt
        )

        if (noteStatus == NoteStatusEnum.CREATE) {
            val insertNoteDto = noteDto.copy(
                createdAt = currentDate
            )
            noteViewModel.doInsertNote(insertNoteDto)
        } else {
            val updateNoteDto = noteDto.copy(
                id = noteId,
                updatedAt = currentDate
            )
            noteViewModel.doUpdateNote(updateNoteDto)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(myRunnable)
        super.onDestroy()
    }

    @AfterPermissionGranted(Permission.STORAGE)
    private fun checkStoragePermission(onHasPermission: () -> Unit) {
        val perms = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            onHasPermission.invoke()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this@NoteActivity,
                "",
                Permission.STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    object Permission {
        const val STORAGE = 101
    }

    object NoteKey {
        const val STATUS = "NoteActivity.STATUS"
        const val ID = "NoteActivity.ID"
    }

    companion object {
        private const val DEFAULT_ROTATION = 0F
        private const val LINEAR_ROTATION = 180F
        private const val ANIMATION_DURATION = 300L
        private const val ADMOB_DELAY = 5000L
    }
}