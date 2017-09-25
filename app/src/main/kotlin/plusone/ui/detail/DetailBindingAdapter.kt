package plusone.ui.detail

import android.databinding.BindingAdapter
import android.widget.ImageView
import plusone.extensions.loadImage

@BindingAdapter("android:src")
fun setImageBinding(view: ImageView, url: String){
    view.loadImage(url)
}