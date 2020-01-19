package net.kelmer.android.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class BottomScrollRecyclerview @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    interface BottomScrollListener {
        fun onBottomScrollReached()
    }

    var bottomScrollListener: BottomScrollListener? = null
    init {

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    bottomScrollListener?.onBottomScrollReached()
                }
            }
        })
    }
}
