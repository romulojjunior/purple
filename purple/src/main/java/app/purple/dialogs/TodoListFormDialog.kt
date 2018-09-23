package app.purple.dialogs;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import app.purple.models.TodoList
import android.support.v4.app.DialogFragment
import android.widget.Button
import app.purple.R


class TodoListFormDialog : DialogFragment() {

    private var todoList: TodoList? = null
    var callback: Callback? = null

    var titleEdiText: EditText? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoList = arguments?.getSerializable(ARG_TODO_LIST) as? TodoList
    }

    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialog_todo_list_form, container, false)

        titleEdiText = view?.findViewById(R.id.dialog_todo_list_form_TitleEditText)

        loadForm(view, todoList)
        return view
    }

    private
    fun loadForm(view: View?, todoItem: TodoList?) {
        if (view != null && todoItem != null) {
            titleEdiText?.setText(todoItem.title)
        }

        val buttonSubmit = view?.findViewById<Button>(R.id.dialog_todo_list_form_SubmitButton)
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

        // TODO: Validate fields
        todoList?.title = titleEdiText?.text.toString()

        if (todoList?.save()!! > 0) {
            callback?.onSave(todoList)
            dismiss()
        }
    }

    companion object {
        const val ARG_TODO_LIST = "ARG_TODO_LIST"
        const val TAG = "TodoListFormDialog"

        fun newInstance(todoList: TodoList?) : TodoListFormDialog {
            val dialogFragment = TodoListFormDialog()
            if (todoList != null) {
                val args = Bundle()
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