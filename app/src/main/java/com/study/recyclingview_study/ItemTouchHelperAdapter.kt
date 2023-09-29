package com.study.recyclingview_study

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean
    fun onItemDismiss(position: Int)
}