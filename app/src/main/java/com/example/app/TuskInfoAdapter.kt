package com.example.app

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.TuskItemBinding


interface TuskActionListener {
    fun onTuskRemove(tuskInfo: TuskInfo)
    fun onDescribeScreen(tuskInfo: TuskInfo )
}

class TuskInfoAdapter(private val tuskActionListener: TuskActionListener) :
    RecyclerView.Adapter<TuskInfoAdapter.TuskInfoViewHolder>(), View.OnClickListener {

    var data: List<TuskInfo> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class TuskInfoViewHolder(val binding: TuskItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = data.size


    override fun onClick(view: View?) {
        val tuskInfo: TuskInfo = view?.tag as TuskInfo
        when (view.id) {
            R.id.more -> showPopupMenu(view)
            else -> tuskActionListener.onDescribeScreen(tuskInfo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TuskInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TuskItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.tuskItemTitle.setOnClickListener(this)
        binding.more.setOnClickListener(this)
        return TuskInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TuskInfoViewHolder, position: Int) {
        val tusk = data[position] // Получение человека из списка данных по позиции
        val context = holder.itemView.context
        holder.itemView.tag = tusk
        holder.binding.tuskItemTitle.tag = tusk
        holder.binding.more.tag = tusk
        with(holder.binding) {
            tuskItemTitle.text = tusk.tuskTitle // Отрисовка имени пользователя

        }
    }
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val tusk = view.tag as TuskInfo
        val position = data.indexOfFirst { it.id == tusk.id }

        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_REMOVE -> tuskActionListener.onTuskRemove(tusk)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}
