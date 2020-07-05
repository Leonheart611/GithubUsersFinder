package mikaoctofrentzen.com.githubusersfinder.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment.*
import mikaoctofrentzen.com.githubusersfinder.R
import mikaoctofrentzen.com.githubusersfinder.ui.main.adapter.UserAdapter
import mikaoctofrentzen.com.githubusersfinder.util.EventObserver
import mikaoctofrentzen.com.githubusersfinder.util.checkInternet
import mikaoctofrentzen.com.githubusersfinder.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var loadingDialog: Dialog

    private val viewModel: MainViewModel by viewModel()
    private var query = ""
    private var page = 1
    private var isAlloaded = false
    private var userAdapter = UserAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
        setupClicklistener()
        setObseverable()
    }

    private fun setupView() {
        with(rv_user_list) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = userAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCOunt: Int = recyclerView.layoutManager?.itemCount ?: 0
                    val llm = recyclerView.layoutManager as LinearLayoutManager
                    if (llm.findLastCompletelyVisibleItemPosition() == totalItemCOunt - 1 && isAlloaded.not()) {
                        if (requireContext().checkInternet()) {
                            page++
                            viewModel.getUsers(query = query, page = page)
                        } else {
                            context?.showToast(resources.getString(R.string.no_internet_connection))
                        }
                    }
                }
            })
        }
        context?.let { context -> //Setup Loding Dialog
            loadingDialog = Dialog(context)
            with(loadingDialog) {
                setContentView(R.layout.loading_layout)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window
                    ?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                setCanceledOnTouchOutside(false)
            }
        }
    }

    private fun setupClicklistener() {
        et_github_username.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query = et_github_username.text.toString()
                if (requireContext().checkInternet()) {
                    if (query.isNotEmpty()) {
                        page = 1
                        viewModel.getUsers(query = query, page = page)
                    } else {
                        et_github_username.setError(resources.getString(R.string.github_user_serach_error))
                    }
                } else {
                    context?.showToast(resources.getString(R.string.no_internet_connection))
                }

                return@OnEditorActionListener true
            }
            false
        })
        btn_find.setOnClickListener {
            query = et_github_username.text.toString()
            if (requireContext().checkInternet()) {
                if (query.isNotEmpty()) {
                    page = 1
                    viewModel.getUsers(query = query, page = page)
                } else {
                    et_github_username.setError(resources.getString(R.string.github_user_serach_error))
                }
            } else {
                context?.showToast(resources.getString(R.string.no_internet_connection))
            }
        }
    }


    private fun setObseverable() {
        viewModel.userResultLiveData.observe(viewLifecycleOwner, Observer { users ->
            if (page == 1) {
                userAdapter.renewUser(users)
            } else {
                userAdapter.addUsers(users)
            }
        })
        viewModel.userAllloaded.observe(viewLifecycleOwner, EventObserver { isAlloaded = it })
        viewModel.showEmptyResult.observe(viewLifecycleOwner, EventObserver { showEmptyResult(it) })
        viewModel.errorMessage.observe(viewLifecycleOwner, EventObserver { context?.showToast(it) })
        viewModel.showLoading.observe(viewLifecycleOwner, EventObserver { showLoading(it) })
    }

    private fun showEmptyResult(show: Boolean) {
        if (show) {
            include_empty_result.visibility = View.VISIBLE
            rv_user_list.visibility = View.GONE
        } else {
            include_empty_result.visibility = View.GONE
            rv_user_list.visibility = View.VISIBLE
        }
    }

    private fun showLoading(show: Boolean) {
        if (show)
            loadingDialog.show()
        else
            loadingDialog.dismiss()
    }
}