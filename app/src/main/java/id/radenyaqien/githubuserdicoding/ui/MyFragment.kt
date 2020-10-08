package id.radenyaqien.githubuserdicoding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.adapter.FollowAdapter
import id.radenyaqien.githubuserdicoding.data.repository.SearchRepository
import id.radenyaqien.githubuserdicoding.data.retrofit.GithubInterface
import id.radenyaqien.githubuserdicoding.data.retrofit.Resource
import id.radenyaqien.githubuserdicoding.data.retrofit.Service
import id.radenyaqien.githubuserdicoding.ui.viewModels.MainViewModel
import id.radenyaqien.githubuserdicoding.ui.viewModels.ViewModelFactory
import id.radenyaqien.githubuserdicoding.util.snackbar
import kotlinx.android.synthetic.main.fragment_my.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var viewModel: MainViewModel? = null
    private lateinit var service: Service
    private lateinit var repository: SearchRepository
    private val myAdapter by lazy {
        FollowAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        service = Service()
        repository = SearchRepository(service.buildApi(GithubInterface::class.java))
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        initrv()
        progress_detail.visibility = View.GONE
        if (param1.equals("following")) {
            param2?.let {
                viewModel?.getFollowing(it)
                progress_detail.visibility = View.VISIBLE
            }
        } else {
            param2?.let {
                viewModel?.getFollower(it)
                progress_detail.visibility = View.VISIBLE
            }
        }
        viewModel?.followerResponse?.observe(viewLifecycleOwner, {
            progress_detail.visibility = View.GONE
            when (it) {
                is Resource.Success -> {
                    if (it.value.isNullOrEmpty()) {
                        root_detail.snackbar("User Belum memiliki Follower")
                    } else {
                        myAdapter.addItems(it.value)
                    }
                }
                is Resource.Failure -> {
                    root_detail.snackbar("Periksa Koneksi Anda")
                }
            }
        })

        viewModel?.followingResponse?.observe(viewLifecycleOwner, {
            progress_detail.visibility = View.GONE
            when (it) {
                is Resource.Success -> {
                    if (it.value.isNullOrEmpty()) {
                        root_detail.snackbar("User Belum memiliki Following")
                    } else {
                        myAdapter.addItems(it.value)

                    }
                }

                is Resource.Failure -> {
                    root_detail.snackbar("Periksa Koneksi Anda")
                }
            }
        })


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }

    private fun initrv() {
        rv_follow.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = myAdapter
        }

    }
}