package `in`.catat.presentation.sketch

import `in`.catat.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_sketch.*

class SketchActivity : AppCompatActivity(R.layout.activity_sketch) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        draw_canvass_view.setCanvassBackground(ContextCompat.getColor(this, R.color.colorPrimary))
    }
}