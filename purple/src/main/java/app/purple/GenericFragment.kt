package app.purple

import android.support.v4.app.Fragment
import com.pawegio.kandroid.toast
import io.reactivex.disposables.CompositeDisposable

open class GenericFragment : Fragment() {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun onShowMessage(message: String) {
        toast(message)
    }
}