package com.example.pixltest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.pixltest.BR
import com.example.pixltest.R
import com.example.pixltest.model.SearchImageResponse
import com.example.pixltest.view_model.MainActivityVM

class ImagesRVAdapter(val viewModel: MainActivityVM) :
    RecyclerView.Adapter<ImagesRVAdapter.ViewHolder>() {

    lateinit var binding: ViewDataBinding
    private var imageValues: ArrayList<SearchImageResponse.Value> = ArrayList()

    class ViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: MainActivityVM, imageValues: SearchImageResponse.Value) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.imageValue, imageValues)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_view_image,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, imageValues[position])

    }

    override fun getItemCount(): Int {
        return imageValues.size
    }

    fun setImageValues(values: List<SearchImageResponse.Value>) {
        this.imageValues.addAll(values)
        notifyItemRangeInserted(itemCount + 1, values.size)
    }

    fun removeAllData() {
        imageValues.removeAll(imageValues)
        notifyDataSetChanged()
    }


}