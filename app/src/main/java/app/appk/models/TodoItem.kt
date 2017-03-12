package app.appk.models
import com.orm.SugarRecord
import java.io.Serializable

enum  class Status {
    done,
}

class TodoItem : SugarRecord(), Serializable {
    var todoListId: Long? = null
    var title: String? = null
    var description: String? = null
    var status: Status = Status.done

    fun todoList() : TodoList {
        return SugarRecord.findById(TodoList::class.java,  todoListId)
    }
}
