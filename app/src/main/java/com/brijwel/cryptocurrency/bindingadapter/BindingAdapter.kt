package com.brijwel.cryptocurrency.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.brijwel.cryptocurrency.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by Brijwel on 06-02-2021.
 */
object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .thumbnail(0.25F)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("setPrice")
    fun setPrice(textView: TextView, price: BigDecimal) {
        val numberFormatter = NumberFormat.getCurrencyInstance(Locale.US)
        textView.text = numberFormatter.format(price)
    }

    @JvmStatic
    @BindingAdapter("setGlobalMarketCap")
    fun setGlobalMarketCap(textView: TextView, price: BigDecimal?) {
        price?.let {
            val numberFormatter = NumberFormat.getCurrencyInstance(Locale.US)
            textView.text = textView.context.getString(R.string.global_market_cap, numberFormatter.format(price))
        }

    }

    @JvmStatic
    @BindingAdapter("setPercentage")
    fun setPercentage(textView: TextView, percentage: Float) {
        val color = if (percentage >= 0) R.color.colorPositive else R.color.colorNegative
        var bigDecimal = BigDecimal(percentage.toString())
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
        val numberFormatter = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
        numberFormatter.positivePrefix = "+"
        textView.apply {
            setTextColor(ContextCompat.getColor(context, color))
            text = context.getString(R.string.percentage, numberFormatter.format(bigDecimal))
        }
    }


}