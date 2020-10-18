package `in`.catat.util.tracking

import com.flurry.android.FlurryAgent

/**
 * Created by pertadima on 18,October,2020
 */

class TrackingUtil() {
    fun startTrackingEvent(trackingEvent: String, trackingContext: HashMap<String, String>) {
        FlurryAgent.logEvent(trackingEvent, trackingContext)
    }
}