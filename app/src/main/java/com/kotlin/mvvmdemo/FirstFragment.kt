package com.kotlin.mvvmdemo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mvvmdemo.interfaces.ItemClickListener
import com.kotlin.mvvmdemo.interfaces.PaginationListener
import com.kotlin.mvvmdemo.models.Datum
import com.kotlin.mvvmdemo.viewmodels.UserViewModel
import com.tchnte.codingtask.adapter.UserListAdapter
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var userList: MutableList<Datum>? = null
    private var userAdapter: UserListAdapter? = null

    private var currentPage: Int = 1
    private var is_LastPage = false
    private var totalPage: Long? = 0
    private var is_Loading = false
    private var page_Size: Long? = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initControls()
        getUserData(1)
    }

    private fun initControls(){
        userList = ArrayList()
        userAdapter = UserListAdapter(userList!!, object : ItemClickListener {
            override fun OnItemClick(datum: Datum?) {
                Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
            }
        })
        rv_List.layoutManager = LinearLayoutManager(context)
        rv_List.hasFixedSize()
        rv_List?.adapter = userAdapter

        rv_List?.addOnScrollListener(object : PaginationListener(rv_List?.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                is_Loading = true
                currentPage++
                getUserData(currentPage)
            }

            override val isLastPage: Boolean
                get() = is_LastPage

            override val isLoading: Boolean
                get() = is_Loading

            override var pageSize: Long
                get() = page_Size!!
                set(value) {page_Size}
        })
    }

    private fun getUserData(pageCount: Int){

        val userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.getUserData(pageCount)?.observe(viewLifecycleOwner, Observer {
            if (currentPage != 1) userAdapter?.removeLoading()
            userAdapter?.addItems(it.data)
            totalPage = it.meta?.pagination?.pages
            page_Size = it.meta?.pagination?.limit
            // check weather is last page or not
            if (currentPage < totalPage!!) {
                userAdapter?.addLoading()
            } else {
                is_LastPage = true
            }
            is_Loading = false
        })
    }
}