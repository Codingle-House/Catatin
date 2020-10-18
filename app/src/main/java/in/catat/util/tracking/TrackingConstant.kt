package `in`.catat.util.tracking

/**
 * Created by pertadima on 18,October,2020
 */

object TrackingConstant {
    object TrackingEvent {
        const val ON_BOARDING_SKIP_CLICKED = "onBoardingSkipClick"
        const val ON_BOARDING_SKIP_CANCELED = "onBoardingSkipCanceled"
        const val ON_BOARDING_SKIP_CONFIRMED = "onBoardingSKipConfirmed"

        const val FILTER_CLICKED = "onFilterClicked"
        const val FILTER_SELECTED = "onFilterSelected"

        const val ADD_NOTES_CLICKED = "onAddNotesClicked"
        const val NOTES_OPENED = "onNotesOpened"

        const val SEARCH_BUTTON_CLICKED = "onSearchButtonClicked"
        const val SEARCH_EVENT = "onSearchEvent"

        const val ATTACHMENT_CLICKED = "onAttachmentClicked"
        const val ATTACHMENT_SELECTED = "onAttachmentSelected"

        const val NOTES_DELETED = "onNotesDeleted"

        const val LOGIN_GOOGLE_CLICKED = "onLoginGoogleClicked"
        const val LOGIN_GOOGLE_SUCCEED = "onLoginGoogleSucceed"
        const val LOGIN_GOOGLE_FAILED = "onLoginGoogleFailed"

        const val LOGOUT_EVENT = "onLogoutEvent"

        const val SUBSCRIPTION_OPENED = "onSubscriptionPageOpened"
    }

    object TrackingContext {
        const val SELECTED_FILTER = "selectedFilter"
        const val IS_FROM = "isFrom"
        const val SELECTED_NOTES_TYPE = "selectedNotesType"
        const val IS_NEW_NOTES = "isNewNotes"
        const val KEYWORD = "keyword"
        const val RESULT_FOUND = "resultFound"
        const val SELECTED_ATTACHMENT_TYPE = "selectedAttachmentType"
        const val EMAIL = "email"
        const val NAME = "name"
    }
}

typealias trackingEvent = TrackingConstant.TrackingEvent
typealias trackingContext = TrackingConstant.TrackingContext