package plusone.ui.detail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import plusone.ApplicationComponent
import plusone.R
import plusone.data.remote.model.Repo
import plusone.databinding.ActivityDetailBinding
import plusone.extensions.enableToolbarBackButton
import plusone.ui.base.ViewModelActivity

open class DetailActivity : ViewModelActivity<DetailViewModel, ActivityDetailBinding>() {

    companion object {
        val EXTRA_REPO_OBJECT = "REPO_ITEM"

        fun newIntent(context: Context, repo: Repo): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_REPO_OBJECT, repo)
            return intent
        }
    }

    private val repo by lazy { intent.getParcelableExtra<Repo>(EXTRA_REPO_OBJECT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.detailToolbar)
        enableToolbarBackButton()
    }

    override fun onBind() {
        super.onBind()
        binding.viewModel = viewModel
    }

    override fun getViewBinding(): ActivityDetailBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }

    override fun injectDependencies(graph: ApplicationComponent) {
        graph.plus(DetailModule(this, repo))
                .injectTo(this)
    }
}
