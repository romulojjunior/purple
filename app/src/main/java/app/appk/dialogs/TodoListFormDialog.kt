package app.appk.dialogs;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import app.appk.models.TodoList
import android.support.v4.app.DialogFragment
import android.widget.Button
import app.appk.R
import com.pawegio.kandroid.find


class TodoListFormDialog : DialogFragment() {

    private var todoList: TodoList? = null
    var callback: Callback? = null

    var titleEdiText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoList = arguments?.getSerializable(ARG_TODO_LIST) as? TodoList
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.dialog_todo_list_form, container, false)

        titleEdiText = view?.find<EditText>(R.id.dialog_todo_list_form_TitleEditText)

        loadForm(view, todoList)
        return view
    }

    private fun loadForm(view: View?, todoItem: TodoList?) {
        if (view != null && todoItem != null) {
            titleEdiText?.setText(todoItem.title)
        }

        val buttonSubmit = view?.find<Button>(R.id.dialog_todo_list_form_SubmitButton)
        buttonSubmit?.setText(loadButtonText())
        buttonSubmit?.setOnClickListener { saveTodoItem() }
    }

    private fun loadButtonText() : Int {
        if  (todoList != null)
            return R.string.save
        else
            return R.string.create
    }

    private fun saveTodoItem() {
        if (todoList == null) todoList = TodoList()

        // TODO: Validate fileds
        todoList?.title = titleEdiText?.text.toString()

        if (todoList?.save()!! > 0) {
            callback?.onSave(todoList)
        }
    }

    companion object {
        const val ARG_TODO_LIST = "ARG_TODO_LIST"
        const val TAG = "TodoListFormDialog"

        fun newInstance(todoList: TodoList?) : TodoListFormDialog {
            val dialogFragment = TodoListFormDialog()
            if (todoList != null) {
                var args = Bundle()
                args.putSerializable(ARG_TODO_LIST, todoList)
                dialogFragment.arguments = args
            }

            return dialogFragment
        }
    }

    // CALLBACKS

    interface Callback {
        fun onSave(todoList: TodoList?)
    }
}