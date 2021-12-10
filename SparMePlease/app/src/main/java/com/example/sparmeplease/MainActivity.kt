package com.example.sparmeplease

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sparmeplease.R
import com.example.sparmeplease.adapter.ItemAdapter
import com.example.sparmeplease.data.AppDatabase
import com.example.sparmeplease.data.Item
import com.example.sparmeplease.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewItemActivity.ItemHandler
{
    private lateinit var binding: ActivityMainBinding
    lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        //items are loaded from the database
        Thread{
            var itemList = AppDatabase.getInstance(this).itemDao().getAllItems()

            runOnUiThread{
                itemAdapter = ItemAdapter(this, itemList)
                recyclerItem.adapter = itemAdapter
            }
        }.start()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_additem)
        {
            var intentAddItem = Intent()
            intentAddItem.setClass(this,
                NewItemActivity::class.java)

            startActivity(intentAddItem)

        }
        else if (item.itemId == R.id.action_delete)
        {
            deleteAllItems()
            Toast.makeText(this, "List Deleted", Toast.LENGTH_LONG).show()

        }

        return super.onOptionsItemSelected(item)
    }

    override fun itemCreated(item: Item)
    {
        Thread{
            item.itemId = AppDatabase.getInstance(this).itemDao().insertItem(item)

            runOnUiThread {
                itemAdapter.addItem(item)
            }
        }.start()
    }

    fun deleteAllItems(){
        Thread{
            AppDatabase.getInstance(this).itemDao().deleteAllItems()

            runOnUiThread{
                itemAdapter.deleteAllItems()
            }
        }.start()
    }
}