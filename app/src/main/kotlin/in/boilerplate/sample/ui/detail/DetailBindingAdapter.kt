package in.boilerplate.sample.ui.detail

import android.databinding.BindingAdapter
import android.widget.ImageView
import in.boilerplate.sample.extensions.loadImage

@BindingAdapter("android:src")
fun setImageBinding(view: ImageView, url: String){
    view.loadImage(url)
}