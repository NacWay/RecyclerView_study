package com.study.recyclingview_study.contacts

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.recyclingview_study.R
import com.study.recyclingview_study.SimpleItemTouchHelperCallback


class ContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        //создаем контакты
        val contactsList = mutableListOf<Contacts>()
            for (i in 0..100){
                contactsList.add(Contacts(i, "Name${i}", "+7${(2400000+Math.random()*2500000).toInt()}", false))
            }

        val addContactBtn : Button = findViewById(R.id.addContactBtn)
        val deleteBtn : Button = findViewById(R.id.deleteBtn)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerContacts)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ContactsAdapter(contactsList, this, true).also { recyclerView.adapter = it }
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        val contactsDiffUtil =
            ContactAdapterDiffUtil(adapter.contactsList, contactsList)
        val productDiffResult = DiffUtil.calculateDiff(contactsDiffUtil)

        adapter.isShow= false

        deleteBtn.setOnClickListener {

            var list: MutableList<Int>  = mutableListOf<Int>()
                for (i in 0..contactsList.size-1){
                    if (adapter.contactsList[i].cheked) {
                        list.add(i)

                }
            }

            for (i in list){
                adapter.contactsList.removeAt(i)
            }
            adapter.isShow = false
            adapter.notifyDataSetChanged()

        }



        //добавление нового контакта
        addContactBtn.setOnClickListener {
            //окно добавление будет в диалоге
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.add_new_contact_dialog, findViewById(R.id.add_new_contact_dialog))

            val enterName : EditText = dialogLayout.findViewById(R.id.enterName)
            val enterNumber : EditText = dialogLayout.findViewById(R.id.enterNumber)

            var newName: String
            var newNumber: String

            builder
                .setView(dialogLayout)
                .setPositiveButton("Сохранить"){dialog, which ->
                    //не стал заморачиваться с проверками введенными в eittext строками(можно и пустую добавить)
                    newName = enterName.text.toString()
                    newNumber = enterNumber.text.toString()
                    adapter.contactsList.add(Contacts(adapter.contactsList.size, newName, newNumber,false))

                    //сравниваем листы с помощью Diffutill
                    adapter.contactsList= contactsList
                    productDiffResult.dispatchUpdatesTo(adapter)
                    //recyclerView.adapter?.notifyItemInserted(adapter.nameList.size)
                } .setNegativeButton("Закрыть"){dialog, which ->
                    dialog.dismiss()
                }.show()
            }
        }
}
