package app.purple.features.home.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import app.purple.base.GenericActivity
import app.purple.R
import app.purple.adapters.FragmentAdapter
import app.purple.dialogs.TodoListFormDialog
import app.purple.models.TodoList
import app.purple.features.home.contracts.MainContract
import app.purple.features.home.presenters.MainActivityPresenter

class MainActivity : GenericActivity(), MainContract.View {
    var mainPresenter: MainContract.Presenter? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter = MainActivityPresenter(this)
        mainPresenter?.loadUI()
    }

    override
    fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val addMenuTodoList = menu?.add(getString(R.string.new_list))
        addMenuTodoList?.setOnMenuItemClickListener { onShowTodoListFormDialog(); false }
        addMenuTodoList?.setIcon(R.drawable.ic_list_add_white_24dp)
        addMenuTodoList?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return true
    }

    override
    fun onLoadViewPager(fragments: List<Fragment>?) {
        val viewPager = findViewById<ViewPager>(R.id.activity_main_ViewPager)
        if (fragments != null) viewPager.adapter = FragmentAdapter(supportFragmentManager, fragments)
    }

    override
    fun onChangeNewTodoLists() {
        onShowMessage("Reloading TodoLists")
        mainPresenter?.loadUI()
    }

    override
    fun onShowTodoListFormDialog() {
        val dialog = TodoListFormDialog.newInstance(null)
        dialog.callback = object : TodoListFormDialog.Callback {
            override
            fun onSave(todoList: TodoList?): Unit {
                if (todoList != null) {
                    onShowMessage(getString(R.string.save))
                    onChangeNewTodoLists()
                } else {
                    onShowMessage(getString(R.string.not_saved))
                }
            }
        }

        dialog.show(supportFragmentManager, TodoListFormDialog.TAG)
    }
}
