package com.study.recyclingview_study.contacts

import androidx.recyclerview.widget.DiffUtil

class ContactAdapterDiffUtil(oldList: List<Contacts>, newList: List<Contacts>) :
    DiffUtil.Callback() {
    private val oldList: List<Contacts>
    private val newList: List<Contacts>

    init {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContacts: Contacts = oldList[oldItemPosition]
        val newContacts: Contacts = newList[newItemPosition]
        return oldContacts.id == newContacts.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList: Contacts = oldList[oldItemPosition]
        val newList: Contacts = newList[newItemPosition]
        return (oldList.name.equals(newList.name)
                && oldList.number === newList.number)
    }
}