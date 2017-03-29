package app.appk.adapters

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.appk.R
import app.appk.dialogs.TodoItemFormDialog
import app.appk.models.Status
import app.appk.models.TodoItem
import com.pawegio.kandroid.find


class TodoItemAdapter(var context: Context,
                      var todoItems: MutableList<TodoItem>?,
                      var fragmentManager: FragmentManager)
                    : RecyclerView.Adapter<TodoItemAdapter.ViewHolder>() {

    val resourceId: Int = R.layout.adapter_todo_item

    override
    fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(todoItems?.get(position))
    }

    override
    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) : ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(resourceId, parent!!, false)
        val viewHolder = ViewHolder(context, view)
        viewHolder.onDeleteItem = { item -> removeItem(item!!) }
        viewHolder.onEditItem = { item -> showTodoItemFormDialog(item) }

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

    fun showTodoItemFormDialog(item: TodoItem) {
        val dialog = TodoItemFormDialog.newInstance(item)
        dialog.callback = object : TodoItemFormDialog.Callback {
            override
            fun onSave(todoItem: TodoItem?) {
                notifyDataSetChanged()
            }
        }

        dialog.show(fragmentManager, TodoItemFormDialog.TAG)
    }

    class ViewHolder(var context: Context, itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView? = null
        var descriptionTextView: TextView? = null
        var statusTextView: TextView? = null
        var deleteButton: View? = null

        var onDeleteItem: (todoItem: TodoItem?) -> Unit = {  }
        var onEditItem: (todoItem: TodoItem) -> Unit =  { }


        init {
            titleTextView = super.itemView?.find<TextView>(R.id.adapter_todo_item_TitleTextView)
            descriptionTextView = super.itemView?.find<TextView>(R.id.adapter_todo_item_DescriptionTextView)
            statusTextView = super.itemView?.find<TextView>(R.id.adapter_todo_item_StatusTextView)
            deleteButton = super.itemView?.find<View>(R.id.adapter_todo_item_DeleteImageView)
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
