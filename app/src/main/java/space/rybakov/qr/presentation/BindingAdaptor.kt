package space.rybakov.qr.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import space.rybakov.qr.R

@BindingAdapter("rotationX")
fun bindRotationX(textView: TextView, rotation: Float){
    textView.text = String.format(
        textView.context.getString(R.string.X),
        rotation
    )
}

@BindingAdapter("rotationY")
fun bindRotationY(textView: TextView, rotation: Float){
    textView.text = String.format(
        textView.context.getString(R.string.Y),
        rotation
    )
}

@BindingAdapter("rotationZ")
fun bindRotationZ(textView: TextView, rotation: Float){
    textView.text = String.format(
        textView.context.getString(R.string.Z),
        rotation
    )
}

@BindingAdapter("horizonRotation")
fun bindHorizonRotation(imageView: ImageView, rotation: Float){
    imageView.rotation = -rotation;
}