package com.study.recyclingview_study.contacts

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.study.recyclingview_study.ItemTouchHelperAdapter
import com.study.recyclingview_study.R
import java.util.Collections


class ContactsAdapter(
    var contactsList: MutableList<Contacts>,
    val context: Context,
    var isShow: Boolean,
)
    : RecyclerView.Adapter<ContactsAdapter.MyViewHolder>(),
    ItemTouchHelperAdapter {



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val phoneNumber: TextView = itemView.findViewById(R.id.phoneNumber)
        val name: TextView = itemView.findViewById(R.id.name)
        val id: TextView = itemView.findViewById(R.id.id)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkContact)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.contacts_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount()= contactsList.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = contactsList.get(position).name
        holder.phoneNumber.text = contactsList.get(position).number
        holder.id.text = contactsList.get(position).id.toString()
        //holder.checkBox.isChecked = contactsList.get(position).cheked

        holder.itemView.setOnLongClickListener{
            isShow=true
            notifyDataSetChanged()
            return@setOnLongClickListener true
        }

        holder.checkBox.setVisibility(if(isShow) VISIBLE else GONE)

        holder.checkBox.setOnClickListener {
            contactsList.get(position).cheked = true
        }

    }



    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(contactsList, i, i + 1)
                contactsList.removeAt(i)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(contactsList, i, i - 1)
                contactsList.removeAt(i)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogLayout = inflater.inflate(R.layout.add_new_contact_dialog, null)

            val enterName : EditText = dialogLayout.findViewById(R.id.enterName)
            val enterNumber : EditText = dialogLayout.findViewById(R.id.enterNumber)

            enterName.setText(contactsList[position].name)
            enterNumber.setText(contactsList[position].number)

            var newName: String
            var newNumber: String

            builder
                .setView(dialogLayout)
                .setPositiveButton("Сохранить"){dialog, which ->
                    //не стал заморачиваться с проверками введенными в eittext строками(можно и пустую добавить)
                    newName = enterName.text.toString()
                    newNumber = enterNumber.text.toString()
                    contactsList.add(position, Contacts(position, newName, newNumber, false))
                    //сравниваем листы с помощью Diffutill
                    val contactsDiffUtil =
                    ContactAdapterDiffUtil(contactsList, contactsList)
                    val productDiffResult = DiffUtil.calculateDiff(contactsDiffUtil)
                    contactsList.removeAt(position+1)
                    productDiffResult.dispatchUpdatesTo(this)
                    notifyItemChanged(position)
                    //recyclerView.adapter?.notifyItemInserted(adapter.nameList.size)
                } .setNegativeButton("Закрыть"){dialog, which ->
                    dialog.dismiss()
                }.show()
        }
}