package cn.zectec.jetpackstudy.databinding

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

class ImageViewBindingAdapter {
    @BindingAdapter(value = ["netImageUrl", "defaultImageSource"], requireAll = false)
    fun setNetImageSource(imageView: ImageView, imageUrl: String, imageSource: Int) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get()
                .load(imageUrl)
                .into(imageView)
        } else {
            imageView.setImageResource(imageSource)
        }
    }
}