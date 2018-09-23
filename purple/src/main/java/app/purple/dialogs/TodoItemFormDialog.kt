package app.purple.dialogs;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import app.purple.models.TodoItem
import android.support.v4.app.DialogFragment
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import app.purple.R
import app.purple.models.Status


class TodoItemFormDialog : DialogFragment() {

    private var todoItem: TodoItem? = null
    var callback: Callback? = null

    private var titleEdiText: EditText? = null
    private var descriptionEditText: EditText? = null
    private var statusRadioGroup: RadioGroup? = null
    private var submitButton: Button? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoItem = arguments?.getSerializable(ARG_TODO_ITEM) as? TodoItem
    }

    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_todo_item_form, container, false)
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadForm(view, todoItem)
    }

    private
    fun loadForm(view: View?, todoItem: TodoItem?) {
        titleEdiText = view?.findViewById(R.id.dialog_todo_item_form_TitleEditText)
        descriptionEditText = view?.findViewById(R.id.dialog_todo_item_form_DescriptionEditText)
        loadSubmitButton(view)
        loadRadioGroup(view)

        if (todoItem != null) {
            titleEdiText?.setText(todoItem.title)
            descriptionEditText?.setText(todoItem.description)

            when(todoItem.status.toString()) {
                "todo" ->  view?.findViewById<RadioButton>(
                        R.id.dialog_todo_item_form_TodoRadioButton)?.isChecked  = true

                "doing" -> view?.findViewById<RadioButton>(
                        R.id.dialog_todo_item_form_DoingRadioButton)?.isChecked  = true

                "done" -> view?.findViewById<RadioButton>(
                        R.id.dialog_todo_item_form_DoneRadioButton)?.isChecked  = true

                else -> view?.findViewById<RadioButton>(
                        R.id.dialog_todo_item_form_TodoRadioButton)?.isChecked  = true
            }
        }
    }

    private fun loadSubmitButton(view: View?) {
        val buttonText = if (todoItem != null) R.string.save else R.string.create
        submitButton = view?.findViewById(R.id.dialog_todo_item_form_SubmitButton)
        submitButton?.setText(buttonText)
        submitButton?.setOnClickListener { saveTodoItem() }
    }

    private fun loadRadioGroup(view: View?) {
        statusRadioGroup = view?.findViewById(R.id.dialog_todo_item_form_StatusRadioGroup)
        statusRadioGroup?.setOnCheckedChangeListener { _, id ->
            when(id) {
                R.id.dialog_todo_item_form_TodoRadioButton -> todoItem?.status = Status.todo
                R.id.dialog_todo_item_form_DoingRadioButton -> todoItem?.status = Status.doing
                R.id.dialog_todo_item_form_DoneRadioButton -> todoItem?.status = Status.done
                else -> todoItem?.status = Status.todo
            }
        }
    }

    private fun saveTodoItem() {
        if (todoItem == null) todoItem = TodoItem()

        todoItem?.title = titleEdiText?.text.toString()
        todoItem?.description = descriptionEditText?.text.toString()

        if (todoItem?.save()!! > 0) {
            callback?.onSave(todoItem)
            dismiss()
        }
    }

    // Statics

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
