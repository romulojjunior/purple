package app.appk.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.appk.R
import app.appk.models.TodoItem
import com.pawegio.kandroid.find


class TodoItemAdapter(var todoItems: MutableList<TodoItem>?) : RecyclerView.Adapter<TodoItemAdapter.ViewHolder>() {
    val resourceId: Int = R.layout.adapter_todo_item

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(todoItems?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) : ViewHolder {
        val view =  LayoutInflater.from(parent?.context).inflate(resourceId, parent!!, false)
        val viewHolder = ViewHolder(view)
        viewHolder.onDeleteItem = { item -> removeItem(item!!) }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return todoItems?.size ?: 0
    }

    fun addItem(todoItem: TodoItem) {
        todoItems?.add(todoItem)
        notifyItemChanged(itemCount)
    }

    fun removeItem(todoItem: TodoItem) {
        todoItems?.remove(todoItem)
        todoItem?.delete()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView? = null
        var descriptionTextView: TextView? = null
        var statusTextView: TextView? = null
        var deleteButton: View? = null

        var onDeleteItem: (todoItem: TodoItem?) -> Unit = {  }


        init {
            titleTextView = super.itemView?.find<TextView>(R.id.adapter_todo_item_TitleTextView)
            descriptionTextView = super.itemView?.find<TextView>(R.id.adapter_todo_item_DescriptionTextView)
            statusTextView = super.itemView?.find<TextView>(R.id.adapter_todo_item_StatusTextView)
            deleteButton = super.itemView?.find<View>(R.id.adapter_todo_item_DeleteImageView)
        }

        fun bindView(item: TodoItem?) {
            titleTextView?.text = item?.title
            descriptionTextView?.text = item?.description
            statusTextView?.text = item?.status.toString()
            deleteButton?.setOnClickListener { onDeleteItem(item)}
        }
    }
}
