package com.ardev.component.cardlist

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import java.util.UUID

data class CardModel(
    val id: String = UUID.randomUUID().toString(),
    @StringRes val title: Int = 0,
    @ColorInt val titleColor: Int = 0,
    @ColorInt val cardColor: Int = 0,
    val items: ArrayList<BaseItem> = arrayListOf(),
    val customAdapter: RecyclerView.Adapter<*>? = null
)

fun cardListOf(vararg mCards: CardModel): ArrayList<CardModel> {
    return arrayListOf(*mCards)
}
