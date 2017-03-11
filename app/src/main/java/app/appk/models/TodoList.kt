package app.appk.models

import com.orm.SugarRecord

class TodoList : SugarRecord() {
    var title: String? = null

    fun todoItems() : MutableList<TodoItem> {
        return SugarRecord.find(TodoItem::class.java, "todoListId = ?", id.toString())
    }
}