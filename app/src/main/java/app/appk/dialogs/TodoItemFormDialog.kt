package app.appk.dialogs;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import app.appk.models.TodoItem
import android.support.v4.app.DialogFragment
import android.widget.Button
import app.appk.R
import app.appk.models.Status
import com.pawegio.kandroid.find


class TodoItemFormDialog : DialogFragment() {

    private var todoItem: TodoItem? = null
    var callback: Callback? = null

    var titleEdiText: EditText? = null
    var descriptionEditText: EditText? = null
    var statusEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoItem = arguments?.getSerializable(ARG_TODO_ITEM) as? TodoItem
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialog_todo_item_form, container, false)

        titleEdiText = view?.find<EditText>(R.id.dialog_todo_item_form_TitleEditText)
        descriptionEditText = view?.find<EditText>(R.id.dialog_todo_item_form_DescriptionEditText)
        statusEditText = view?.find<EditText>(R.id.dialog_todo_item_form_StatusEditText)

        loadForm(view, todoItem)
        return view
    }

    private fun loadForm(view: View?, todoItem: TodoItem?) {
        if (view != null && todoItem != null) {
            titleEdiText?.setText(todoItem.title)
            descriptionEditText?.setText(todoItem.description)
            statusEditText?.setText(todoItem.status.toString())
        }

        val buttonSubmit = view?.find<Button>(R.id.dialog_todo_item_form_SubmitButton)
        buttonSubmit?.setText(loadButtonText())
        buttonSubmit?.setOnClickListener { saveTodoItem() }
    }

    private fun loadButtonText() : Int {
        if  (todoItem != null)
            return R.string.save
        else
            return R.string.create
    }

    private fun saveTodoItem() {
        if (todoItem == null) todoItem = TodoItem()

        // TODO: Validate fields
        todoItem?.title = titleEdiText?.text.toString()
        todoItem?.description = descriptionEditText?.text.toString()
        todoItem?.status = Status.todo

        if (todoItem?.save()!! > 0) {
            callback?.onSave(todoItem)
            dismiss()
        }
    }

    companion object {
        const val ARG_TODO_ITEM = "ARG_TODO_ITEM"
        const val TAG = "TodoItemFormDialog"

        fun newInstance(todoItem: TodoItem?) : TodoItemFormDialog {
            val dialogFragment = TodoItemFormDialog()
            if (todoItem != null) {
                val args = Bundle()
                args.putSerializable(ARG_TODO_ITEM, todoItem)
                dialogFragment.arguments = args
            }

            return dialogFragment
        }
    }

    // CALLBACKS

    interface Callback {
        fun onSave(todoItem: TodoItem?)
    }
}
