package app.appk.mvp.models

import app.appk.models.TodoList
import com.orm.SugarRecord

class MainActivityModel : MainModel {

    override
    fun todoLists(callback: (MutableList<TodoList>) -> Unit) {
        callback(SugarRecord.listAll(TodoList::class.java))
    }
}