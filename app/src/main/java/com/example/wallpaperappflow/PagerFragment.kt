package com.example.wallpaperappflow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallpaperappflow.adapters.ImagesAdapter
import com.example.wallpaperappflow.databinding.FragmentPagerBinding
import com.example.wallpaperappflow.model.Photo
import com.example.wallpaperappflow.retrofit.ApiClient
import com.example.wallpaperappflow.utils.PaginationScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



private const val ARG_PARAM1 = "image"
private const val KEY = "21493032-eb25e8a3e2495ff3b365c172e"

class PagerFragment : Fragment() {
    private var currentPage = 1
    private var imageType: String? = null
    private lateinit var binding: FragmentPagerBinding
    private lateinit var imagesAdapter: ImagesAdapter

    private var isLoading = false
    private var isLastPage = false
    private var totalPage = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageType = it.getString(ARG_PARAM1) as String
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerBinding.inflate(layoutInflater, container, false)

        val linearLayoutManager = GridLayoutManager(requireContext(),3)
        binding.rv.layoutManager = linearLayoutManager
        binding.rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                loadPageData()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

        loadPageData()
        imagesAdapter = ImagesAdapter( object : ImagesAdapter.OnClickListener{
            override fun OnItemClick(url: String) {
                val intent = Intent(requireContext(), SecondActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }

        })
        binding.rv.adapter = imagesAdapter


        return binding.root

    }

    fun loadPageData() {
        imageType?.let {
            ApiClient.apiService.getImages(KEY,imageType!!,currentPage).enqueue(object : Callback<Photo>    {
                override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                    if (response.isSuccessful) {
                        binding.progress.visibility = View.INVISIBLE
                        imagesAdapter.addListData(response.body()?.hits?: emptyList())
                    }

                    if (currentPage != 1) {
                        imagesAdapter.removeLoading()
                    }
                    if (currentPage < totalPage) {
                        imagesAdapter.addLoading()
                    } else {
                        isLastPage = true
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<Photo>, t: Throwable) {

                }

            })
        }
    }
    companion object {

        fun newInstance(param1: String) =
            PagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}