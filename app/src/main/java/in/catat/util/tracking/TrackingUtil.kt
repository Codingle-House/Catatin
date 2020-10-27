package `in`.catat.util.tracking

import com.flurry.android.FlurryAgent

/**
 * Created by pertadima on 18,October,2020
 */

class TrackingUtil() {
    fun startTrackingEvent(
        trackingEvent: String,
        trackingContext: HashMap<String, String>? = null
    ) {
        trackingContext?.let {
            FlurryAgent.logEvent(trackingEvent, it)
        } ?: kotlin.run {
            FlurryAgent.logEvent(trackingEvent)
        }
    }
}