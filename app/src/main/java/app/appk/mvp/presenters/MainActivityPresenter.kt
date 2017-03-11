package app.appk.mvp.presenters

import app.appk.mvp.models.MainActivityModel
import app.appk.mvp.views.MainView

class MainActivityPresenter(
        var mainView: MainView,
        var mainActivityModel: MainActivityModel = MainActivityModel()): MainPresenter {

    override
    fun fetchTodoLists() {
        mainActivityModel.todoLists { item ->
            mainView.showMessage(item.toString())
        }
    }
}