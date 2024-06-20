package `in`.catat.presentation.note

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isGone
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.showToast
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.InsertNoteDto
import `in`.catat.data.enum.NoteStatusEnum
import `in`.catat.data.enum.NoteStatusEnum.EDIT
import `in`.catat.databinding.ActivityNoteBinding
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.note.NoteActivity.NoteKey.ID
import `in`.catat.presentation.note.NoteActivity.NoteKey.STATUS
import `in`.catat.presentation.note.NoteActivity.Permission.STORAGE
import `in`.catat.util.DateUtil.getCurrentDate
import `in`.catat.util.RichTextItem
import `in`.catat.util.ShareUtil
import `in`.catat.util.constants.appConstant
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks
import java.util.Calendar.getInstance
import javax.inject.Inject


@AndroidEntryPoint
class NoteActivity : BaseActivity<ActivityNoteBinding>(), PermissionCallbacks {

    override val bindingInflater: (LayoutInflater) -> ActivityNoteBinding
        get() = ActivityNoteBinding::inflate

    @Inject
    lateinit var diffCallback: DiffCallback

    private val noteViewModel: NoteViewModel by viewModels()

    private val currentDate by lazy { getCurrentDate() }

    private val currentNoteId by lazy { getInstance().timeInMillis }

    private val noteStatus by lazy { intent?.getSerializableExtra(STATUS) as NoteStatusEnum }

    private val noteId by lazy {
        intent?.getLongExtra(ID, 0L) ?: 0L
    }

    private val handler by lazy { Handler() }

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
    }

    private fun getNoteData() {
        if (noteStatus == EDIT) noteViewModel.getSingleNote(noteId)
    }

    override fun onViewModelObserver() = with(noteViewModel) {
        observeAttachmentMenu().onResult { attachmentDialog.setData(it) }
        observeSettingsMenu().onResult { settingsDialog.setData(it) }

        observeSingleNote().onResult {
            createdAt = it.createdAt
            binding.noteEdittextTitle.setText(it.title)
            binding.noteRichtextForm.fromHtml(it.content)
        }
    }

    private fun setupAppToolbar() {
        with(binding.noteToolbar) {
            setNavigationOnClickListener { finish() }
            inflateMenu(R.menu.catatin_menu_note)
            menu.findItem(R.id.note_menu_delete).isVisible = noteStatus == EDIT
            setOnMenuItemClickListener { handleMenuClick(it) }
        }
    }

    private fun setupView() = with(binding) {
        noteTextviewDescription.text = getString(
            R.string.note_text_description,
            currentDate,
            noteRichtextForm.length().toString()
        )

        noteRichtextForm.addTextChangedListener(object : TextWatcher {
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
                noteTextviewDescription.text =
                    getString(R.string.note_text_description, currentDate, s.length.toString())
            }
        })
    }

    private fun initRichTextView() = with(binding) {
        val toolItems = RichTextItem.richTextDefaultItem(this@NoteActivity)
        noteRichtextToolbar.addToolbarItems(toolItems)
        noteRichtextForm.setToolbar(noteRichtextToolbar)
    }

    private fun setupToolbarArrow() = with(binding) {
        noteRichtextToolbar.viewTreeObserver.addOnScrollChangedListener {
            val scrollX = noteRichtextToolbar.scrollX
            val scrollWidth = noteRichtextToolbar.width
            val fullWidth = noteRichtextToolbar.getChildAt(0).width

            noteImageviewArrow.rotateAnimation(scrollX + scrollWidth < fullWidth)
        }
        noteImageviewArrow.setOnClickListener {
            val fullWidth = noteRichtextToolbar.getChildAt(0).width
            noteRichtextToolbar.smoothScrollBy(if (scrollerAtEnd) -Integer.MAX_VALUE else fullWidth, 0)
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
        binding.noteAppbar.isGone = true
        binding.noteEdittextTitle.isGone = true
        isFullScreen = true
        showToast(R.string.general_text_fullscreen_close)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (isFullScreen) {
            isFullScreen = false
            binding.noteAppbar.isGone = false
            binding.noteEdittextTitle.isGone = false
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
            intent.putExtra(Intent.EXTRA_SUBJECT, binding.noteEdittextTitle.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, binding.noteRichtextForm.text.toString())
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
        val title = binding.noteEdittextTitle.text.toString()
        val content = binding.noteRichtextForm.text.toString()
        if (title.isEmpty() && content.isEmpty()) return

        val noteDto = InsertNoteDto(
            title = binding.noteEdittextTitle.text.toString(),
            content = binding.noteRichtextForm.html.toString(),
            type = appConstant.TYPE_NOTE,
            createdAt = createdAt
        )

        if (noteStatus == NoteStatusEnum.CREATE) {
            val insertNoteDto = noteDto.copy(
                id = currentNoteId,
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

    @AfterPermissionGranted(STORAGE)
    private fun checkStoragePermission(onHasPermission: () -> Unit) {
        val perms = arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            onHasPermission.invoke()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this@NoteActivity,
                "",
                STORAGE,
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE
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
    }
}