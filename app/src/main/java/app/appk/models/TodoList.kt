package app.appk.models

import com.orm.SugarRecord

class TodoList : SugarRecord() {
    var title: String? = null

    fun todoItems() : MutableList<TodoItem>? {
        return SugarRecord.find(TodoItem::class.java, "todo_list_id = ?", id.toString())
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