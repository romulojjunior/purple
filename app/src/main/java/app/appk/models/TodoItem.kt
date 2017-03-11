package app.appk.models
import com.orm.SugarRecord

enum  class Status {
    done,
}

class TodoItem : SugarRecord() {
    var todoListId: Int? = null
    var title: String? = null
    var description: String? = null
    var status: Status = Status.done

    fun todoList() : TodoList {
        return SugarRecord.findById(TodoList::class.java,  todoListId)
    }
}
