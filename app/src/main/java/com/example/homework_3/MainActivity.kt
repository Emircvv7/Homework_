package com.example.homework_3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_3.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = PixaAdapter(arrayListOf())
    private var page = 1
    private var oldWord = ""
    private var newWord = ""
    private val VISIBLE_THRESHOLD = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPixa.adapter = adapter
        initClickers()
        initScrollListener()
    }

    private fun initClickers() {
        with(binding) {
            nextBtn.setOnClickListener {
                newWord = searchEd.text.toString()
                if (newWord != oldWord) {
                    Toast.makeText(
                        this@MainActivity,
                        "Чтобы найти новое слово нажмите 'Search' ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    page++
                    getImages()
                }
            }
            serchBtn.setOnClickListener {
                adapter.clearData()
                page = 1
                getImages()
            }
        }
    }

    private fun initScrollListener() {
        binding.rvPixa.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    page++
                    getImages()
                }
            }
        })
    }

    private fun getImages() {
        oldWord = binding.searchEd.text.toString()
        RetrofitService().api.getImages(
            keyWordForSearch = oldWord,
            page = page
        )
            .enqueue(object : Callback<PixabayModel> {
                override fun onResponse(
                    call: Call<PixabayModel>,
                    response: Response<PixabayModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let {
                            adapter.updateData(it.hits)
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Ошибка загрузки изображений",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}
