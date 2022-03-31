package com.zjx.customview

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 *
 */
fun getBitmap(resources: Resources, resId: Int, width: Int): Bitmap {
    val options: BitmapFactory.Options = BitmapFactory.Options()
    options.inJustDecodeBounds = true//这个设置为true之后，decodeResource只会获取图片的宽高，不会去获取其它东西
    BitmapFactory.decodeResource(resources, resId, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    return BitmapFactory.decodeResource(resources, resId, options)
}

/******************************************** ViewBinding的优雅使用 *************************************************************/
inline fun <reified VB : ViewBinding> Activity.inflate()  = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> Dialog.inflate()  = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> Fragment.bindingView() = FragmentBindingDelegate(VB::class.java)

class FragmentBindingDelegate<VB : ViewBinding>(
    private val clazz: Class<VB>
): ReadOnlyProperty<Fragment, VB> {
    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object: LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestoryView() {
                    _binding = null
                }
            })
            _binding = clazz.getMethod("inflate", View::class.java)
                .invoke(null, thisRef.requireView()) as VB
            isInitialized = true
        }
        return binding
    }

}

class BindingViewHolder<VB : ViewBinding> (val binding : VB) : RecyclerView.ViewHolder(binding.root)