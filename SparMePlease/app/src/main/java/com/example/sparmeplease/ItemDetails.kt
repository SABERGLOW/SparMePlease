package com.example.sparmeplease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sparmeplease.data.Item
import com.example.sparmeplease.databinding.ItemDetailsBinding
import com.example.sparmeplease.retro.MoneyAPI
import com.example.sparmeplease.retro.MoneyResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemDetails : AppCompatActivity()
{
    private lateinit var  binding: ItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var item: Item = intent.getSerializableExtra("itemId") as Item
        var itemPrice: Int? = item.price

        binding.itemNameDetail.setText(item.name)
        binding.itemDescriptionDetail.setText(item.description)

        //Find the category name
        val categories = resources.getStringArray(R.array.categories).toList()
        binding.itemCategoryDetail.setText(categories.get(item.category).toString())

        //Status of the item
        if(item.status)
        {
            binding.itemStatusDetail.setText("Purchased")
        }
        else
        {
            binding.itemStatusDetail.setText("Not Purchased")
        }

        binding.itemPriceDetail.setText(itemPrice.toString()+" HUF")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.frankfurter.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val currencyAPI = retrofit.create(MoneyAPI::class.java)

        binding.itemResult.setText("")
        binding.itemPriceDetail.setText(itemPrice.toString()+" HUF")

            val rateCall = currencyAPI.getRates("HUF")

            rateCall.enqueue(object : Callback<MoneyResult>
            {
                override fun onFailure(call: Call<MoneyResult>, t: Throwable)
                {
                    binding.itemResult.setText(t.message)
                }

                override fun onResponse(call: Call<MoneyResult>, response: Response<MoneyResult>)
                {
                    //HUF to EUR
                    var eurRate: Double? = response.body()?.rates?.EUR?.toDouble()
                    var priceInEUR = String.format("%.3f", itemPrice?.times(eurRate!!))
                    //HUF to PLN
                    var plnRate: Double? = response.body()?.rates?.PLN?.toDouble()
                    var priceInPLN = String.format("%.3f", itemPrice?.times(plnRate!!))
                    //HUF to TRY
                    var tryRate: Double? = response.body()?.rates?.TRY?.toDouble()
                    var priceInTRY = String.format("%.3f", itemPrice?.times(tryRate!!))

                    binding.itemPriceDetail.append("\n \n"+priceInEUR+" EUR")
                    binding.itemResult.append("EUR: "+eurRate+"\n")
                    binding.itemPriceDetail.append("\n"+priceInPLN+" PLN")
                    binding.itemResult.append("PLN: "+plnRate+"\n")
                    binding.itemPriceDetail.append("\n"+priceInTRY+" TRY")
                    binding.itemResult.append("TRY: "+tryRate+"\n")
                }
            })

    }
}