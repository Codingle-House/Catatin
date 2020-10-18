package `in`.catat

import android.app.Application
import com.flurry.android.FlurryAgent
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by pertadima on 22,August,2020
 */

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FlurryAgent.Builder()
            .withLogEnabled(true)
            .build(this, FLURRY_API_KEY)
    }

    companion object {
        private const val FLURRY_API_KEY = "6TKW8HBH97FHCY9F26P6"
    }
}