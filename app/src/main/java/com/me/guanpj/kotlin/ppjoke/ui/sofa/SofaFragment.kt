package com.me.guanpj.kotlin.ppjoke.ui.sofa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.reflect.TypeToken
import com.me.guanpj.kotlin.ppjoke.R
import com.me.guanpj.kotlin.ppjoke.databinding.FragmentSofaBinding
import com.me.guanpj.kotlin.ppjoke.model.Comment
import com.me.guanpj.kotlin.ppjoke.model.Feed
import com.me.guanpj.libnavannotation.FragmentDestination
import com.me.guanpj.libnet.ApiResponse
import com.me.guanpj.libnet.ApiService
import com.me.guanpj.libnet.Callback

@FragmentDestination(pageUrl = "main/tabs/sofa")
class SofaFragment : Fragment() {

    private lateinit var sofaViewModel: SofaViewModel
    lateinit var binding: FragmentSofaBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSofaBinding.inflate(inflater)

        sofaViewModel =
                ViewModelProviders.of(this).get(SofaViewModel::class.java)
        sofaViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })
        binding.textHome.setOnClickListener {
            ApiService.postRequest<Comment>("/comment/addComment")
                .addParam("itemId", 1581239433864)
                .addParam("userId", 1582391089)
                .addParam("width", 0)
                .addParam("height", 0)
                .addParam("commentText", "aaaaaaaaa")
                .responseType(object: TypeToken<Comment>(){}.type)
                .execute(object: Callback<Comment>(){
                    override fun onSuccess(response: ApiResponse<Comment>) {
                        val body = response.body
                        //textView.text = body?.get(0)?.feeds_text
                    }
                })
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()


        ApiService.getRequest<ArrayList<Feed>>("/feeds/queryHotFeedsList")
            .addParam("feedType", "all")
            .addParam("userId", 1582391089)
            .addParam("feedId", 0)
            .addParam("pageCount", 3)
            .responseType(object: TypeToken<ArrayList<Feed>>(){}.type)
            .execute(object: Callback<ArrayList<Feed>>(){
                override fun onSuccess(response: ApiResponse<ArrayList<Feed>>) {
                    val body = response.body
                    //textView.text = body?.get(0)?.feeds_text
                }
            })
    }
}
