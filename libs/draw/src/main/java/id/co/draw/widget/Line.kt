package id.co.draw.widget

import android.graphics.Path
import id.co.draw.widget.Action
import java.io.Writer

class Line(val x: Float, val y: Float) : Action {

    override fun perform(path: Path) {
        path.lineTo(x, y)
    }

    override fun perform(writer: Writer) {
        writer.write("L$x,$y")
    }
}