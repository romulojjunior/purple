package app.appk.mvp.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import app.appk.R
import app.appk.models.TodoList
import app.appk.mvp.presenters.MainActivityPresenter
import app.appk.mvp.presenters.MainPresenter
import com.pawegio.kandroid.find
import com.pawegio.kandroid.toast

class MainActivity : AppCompatActivity(), MainView {
    var mainPresenter: MainPresenter? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter = MainActivityPresenter(this)

        val button = find<Button>(R.id.activity_main_HelloButton)
        button.setOnClickListener { mainPresenter?.fetchTodoLists() }

        var a = TodoList()
        a.title = "aaa"
        a.save()
    }

    override
    fun showMessage(message: String) {
        toast(message)
    }
}
