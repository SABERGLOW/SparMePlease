package com.example.sparmeplease

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.sparmeplease.data.Item
import com.example.sparmeplease.data.*
import com.example.sparmeplease.databinding.ActivityNewItemBinding
import java.util.*

class NewItemActivity : AppCompatActivity()
{
    lateinit var binding: ActivityNewItemBinding
    lateinit var context: Context
    companion object
    {
        const val KEY_ADD = "KEY_ADD"
    }


    lateinit var etItemName: EditText
    lateinit var etItemDescription: EditText
    lateinit var etItemPrice: EditText
    lateinit var cbItemStatus: CheckBox
    lateinit var spinnerCategory: Spinner


    interface ItemHandler
    {
        fun itemCreated(item: Item)
    }
    lateinit var itemHandler: ItemHandler


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityNewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etItemName = binding.etItemName
        etItemDescription = binding.etDescription
        etItemPrice = binding.etPrice
        cbItemStatus = binding.cbStatus
        spinnerCategory = binding.spinnerCategory


        var categoryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )

        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerCategory.adapter = categoryAdapter
    }

    override fun onResume()
    {
        super.onResume()

        binding.btnAddItem.setOnClickListener{
            handleNewItemCreate()
        }
    }


    private fun handleNewItemCreate()
    {
        var item = Item(
                null,
                etItemName.text.toString(),
                spinnerCategory.selectedItemPosition.toInt(),
                etItemDescription.text.toString(),
                etItemPrice.text.toString().toInt(),
                cbItemStatus.isChecked
            )

        Thread{
            AppDatabase.getInstance(this).itemDao().insertItem(item)
        }.start()
        var intentMain = Intent(applicationContext, MainActivity::class.java)
        startActivity(intentMain)

    }
}