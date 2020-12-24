package id.radenyaqien.githubuserdicoding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.radenyaqien.githubuserdicoding.adapter.FollowAdapter
import id.radenyaqien.githubuserdicoding.data.retrofit.Resource
import id.radenyaqien.githubuserdicoding.databinding.FragmentMyBinding
import id.radenyaqien.githubuserdicoding.ui.viewModels.MainViewModel
import id.radenyaqien.githubuserdicoding.util.snackbar


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class MyFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMyBinding
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
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initrv()
        binding.progressDetail.visibility = View.GONE
        if (param1.equals("following")) {
            param2?.let {
                viewModel.getFollowing(it)
                binding.progressDetail.visibility = View.VISIBLE
            }
        } else {
            param2?.let {
                viewModel.getFollower(it)
                binding.progressDetail.visibility = View.VISIBLE
            }
        }
        viewModel.followerResponse.observe(viewLifecycleOwner, {
            binding.progressDetail.visibility = View.GONE
            when (it) {
                is Resource.Success -> {
                    if (it.value.isNullOrEmpty()) {
                        binding.rootDetail.snackbar("User Belum memiliki Follower")
                    } else {
                        myAdapter.addItems(it.value)
                    }
                }
                is Resource.Failure -> {
                    binding.rootDetail.snackbar("Periksa Koneksi Anda")
                }
            }
        })

        viewModel.followingResponse.observe(viewLifecycleOwner, {
            binding.progressDetail.visibility = View.GONE
            when (it) {
                is Resource.Success -> {
                    if (it.value.isNullOrEmpty()) {
                        binding.root.snackbar("User Belum memiliki Following")
                    } else {
                        myAdapter.addItems(it.value)

                    }
                }

                is Resource.Failure -> {
                    binding.root.snackbar("Periksa Koneksi Anda")
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
        binding.rvFollow.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = myAdapter
        }

    }
}