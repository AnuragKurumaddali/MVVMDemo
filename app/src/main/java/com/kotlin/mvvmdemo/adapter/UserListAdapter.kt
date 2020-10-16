package com.tchnte.codingtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mvvmdemo.R
import com.kotlin.mvvmdemo.interfaces.ItemClickListener
import com.kotlin.mvvmdemo.models.Datum
import com.kotlin.mvvmdemo.viewholder.BaseViewHolder

class UserListAdapter(lsDatum: MutableList<Datum>?, itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var isLoaderVisible = false
    private val mDatum: MutableList<Datum>? = lsDatum
    private val itemListener: ItemClickListener? = itemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.user_list_cell, parent, false)
            )
            VIEW_TYPE_LOADING -> ProgressHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.progress_loading, parent, false)
            )
            else ->
                ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.user_list_cell, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == mDatum?.size!! - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int {
        return mDatum?.size ?: 0
    }

    fun addItems(mDatum: List<Datum>?) {
        this.mDatum?.addAll(mDatum!!)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        mDatum?.add(Datum())
        notifyItemInserted(mDatum?.size!! - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position = mDatum?.size!! - 1
        val item:Datum? = getItem(position)
        if (item != null) {
            mDatum.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        mDatum?.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int):Datum? {
        return if(mDatum != null && mDatum.size > position)
            mDatum[position]
        else
            null
    }

    inner class ViewHolder internal constructor(itemView: View?) :
        BaseViewHolder(itemView!!) {
        var tv_Id: TextView? = null
        var tv_Status: TextView? = null
        var tv_UserName: TextView? = null
        var tv_UserEmail: TextView? = null
        var tv_Gender: TextView? = null
        var ll_listItem: LinearLayout? = null
        override fun clear() {}
        override fun onBind(position: Int?) {
            super.onBind(position)
            val item:Datum? = mDatum?.get(position!!)
            tv_Id?.text = item?.id.toString()
            tv_Status?.text = item?.status
            tv_UserName?.text = item?.name
            tv_UserEmail?.text = item?.email
            tv_Gender?.text = item?.gender

            ll_listItem?.setOnClickListener {
                itemListener?.OnItemClick(item!!)
            }
        }

        init {
            tv_Id = itemView?.findViewById(R.id.tv_Id)
            tv_Status = itemView?.findViewById(R.id.tv_Status)
            tv_UserName = itemView?.findViewById(R.id.tv_UserName)
            tv_UserEmail = itemView?.findViewById(R.id.tv_UserEmail)
            tv_Gender = itemView?.findViewById(R.id.tv_Gender)
            ll_listItem = itemView?.findViewById(R.id.ll_listItem)
        }
    }

    inner class ProgressHolder internal constructor(itemView: View?) :
        BaseViewHolder(itemView) {
        override fun clear() {}


    }

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1
    }

}