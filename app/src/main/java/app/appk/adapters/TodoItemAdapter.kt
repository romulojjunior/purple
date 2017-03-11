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
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoItems?.size ?: 0
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var titleTextView : TextView? = null

        init {
            titleTextView = super.itemView?.find<TextView>(R.id.adapter_todo_item_TitleTextView)
        }

        fun bindView(item: TodoItem?) {
            titleTextView?.text = item?.title
        }
    }
}
