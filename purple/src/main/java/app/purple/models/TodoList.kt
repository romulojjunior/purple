package app.purple.models

import com.orm.SugarRecord
import java.io.Serializable

class TodoList : SugarRecord(), Serializable {
    var title: String? = null

    fun todoItems() : MutableList<TodoItem>? {
        return SugarRecord.find(TodoItem::class.java, "todo_list_id = ?", id.toString())
    }

    fun deleteTodoItems() : Boolean {
        val result =  SugarRecord.deleteAll(TodoItem::class.java, "todo_list_id = ?", id.toString())
        return  result > 0
    }

    // Statics

    companion object {

        fun findById(id: Long) : TodoList? {
            return SugarRecord.findById(TodoList::class.java, id)
        }

        fun findAll(): MutableList<TodoList>? {
            return SugarRecord.listAll(TodoList::class.java)
        }
    }
}