package plusone.ui.list

import plusone.data.remote.model.Repo
import plusone.ui.base.AbstractViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RepoViewModel(val repo: Repo) : AbstractViewModel() {

    private val clicks = PublishSubject.create<Unit>()

    fun getName() = repo.fullName

    fun getDescription() = repo.description

    fun onClick() {
        clicks.onNext(Unit)
    }

    fun clicks(): Observable<Unit> = clicks.hide()
}