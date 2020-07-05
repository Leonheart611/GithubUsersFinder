package mikaoctofrentzen.com.githubusersfinder.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.main_fragment.*
import mikaoctofrentzen.com.githubusersfinder.R
import mikaoctofrentzen.com.githubusersfinder.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()
    private var query = ""
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupClicklistener()
        setObseverable()
    }

    private fun setupClicklistener() {
        btn_find.setOnClickListener {
            query = et_github_username.text.toString()
            if (query.isNotEmpty()) {
                viewModel.getUsers(query = query, page = page)
            } else {
                et_github_username.setError(resources.getString(R.string.github_user_serach_error))
            }
        }
    }


    private fun setObseverable() {
        viewModel.userResultLiveData.observe(viewLifecycleOwner, Observer { users ->
            this.context?.showToast(users.toString())
        })
    }

}