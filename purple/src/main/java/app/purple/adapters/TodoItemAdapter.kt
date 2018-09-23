package app.purple.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.purple.R
import app.purple.models.Status
import app.purple.models.TodoItem


class TodoItemAdapter(var context: Context,
                      var todoItems: MutableList<TodoItem>?)
                    : RecyclerView.Adapter<TodoItemAdapter.ViewHolder>() {

    val resourceId: Int = R.layout.adapter_todo_item
    lateinit var onShowDialog: (item: TodoItem, callback: (item: TodoItem?) -> Unit) -> Unit

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(todoItems?.get(position))
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(resourceId, parent, false)
        val viewHolder = ViewHolder(context, view)
        viewHolder.onDeleteItem = { item -> removeItem(item!!) }
        viewHolder.onEditItem = { item ->
            onShowDialog(item) {
                notifyDataSetChanged()
            }
        }

        return viewHolder
    }

    override
    fun getItemCount(): Int {
        return todoItems?.size ?: 0
    }

    fun addItem(todoItem: TodoItem) {
        todoItems?.add(todoItem)
        notifyItemChanged(itemCount)
    }

    fun removeItem(todoItem: TodoItem) {
        todoItems?.remove(todoItem)
        todoItem.delete()
        notifyDataSetChanged()
    }

    class ViewHolder(var context: Context, itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView? = null
        var descriptionTextView: TextView? = null
        var statusTextView: TextView? = null
        var deleteButton: View? = null

        var onDeleteItem: (todoItem: TodoItem?) -> Unit = {  }
        var onEditItem: (todoItem: TodoItem) -> Unit =  { }


        init {
            titleTextView = super.itemView?.findViewById(R.id.adapter_todo_item_TitleTextView)
            descriptionTextView = super.itemView?.findViewById(R.id.adapter_todo_item_DescriptionTextView)
            statusTextView = super.itemView?.findViewById(R.id.adapter_todo_item_StatusTextView)
            deleteButton = super.itemView?.findViewById(R.id.adapter_todo_item_DeleteImageView)
        }

        fun bindView(item: TodoItem?) {
            titleTextView?.text = item?.title
            descriptionTextView?.text = item?.description
            bindStatusTextView(item?.status.toString())

            deleteButton?.setOnClickListener { onDeleteItem(item)}
            itemView.setOnLongClickListener {
                if (item != null) onEditItem(item); false
            }
        }

        private fun bindStatusTextView(status: String?) {
            if (status != null) {
                statusTextView?.text = status

                when(status) {
                    Status.todo.toString() -> statusTextView?.setTextColor(
                            ContextCompat.getColor(context, R.color.status_todo_color))
                    Status.doing.toString() ->  statusTextView?.setTextColor(
                            ContextCompat.getColor(context, R.color.status_doing_color))
                    Status.done.toString() ->  statusTextView?.setTextColor(
                            ContextCompat.getColor(context, R.color.status_done_color))
                }
            }
        }
    }
}
