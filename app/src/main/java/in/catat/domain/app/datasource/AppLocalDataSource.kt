package `in`.catat.domain.app.datasource

import `in`.catat.R
import `in`.catat.data.dto.CatatinMenuDto
import javax.inject.Inject

/**
 * Created by pertadima on 06,October,2020
 */

class AppLocalDataSource @Inject constructor() {
    val settingsMenu: List<CatatinMenuDto>
        get() = listOf(
            CatatinMenuDto(title = R.string.dialog_title_menu_fullscreen),
            CatatinMenuDto(title = R.string.dialog_title_menu_alarm),
            CatatinMenuDto(title = R.string.dialog_title_menu_share),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_lock,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_focus,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_pdf,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            )
        )

    val attachmentMenuList: List<CatatinMenuDto>
        get() = listOf(
            CatatinMenuDto(title = R.string.dialog_title_menu_file),
            CatatinMenuDto(title = R.string.dialog_title_menu_location),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_video,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_lock,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_audio,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_sketch,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
        )

    val notesType: List<CatatinMenuDto>
        get() = listOf(
            CatatinMenuDto(R.string.dialog_title_menu_notes),
            CatatinMenuDto(R.string.dialog_title_menu_todo),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_draw,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            )
        )
}