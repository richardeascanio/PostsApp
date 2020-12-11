package com.richard.postsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.richard.postsapp.R
import com.richard.postsapp.adapter.PostAdapter
import com.richard.postsapp.data.Post
import com.richard.postsapp.ui.MainStateEvent
import com.richard.postsapp.ui.MainViewModel
import com.richard.postsapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.posts_fragment), View.OnClickListener {

    private val TAG = "POST_FRAGMENT"

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton
    private lateinit var postAdapter: PostAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvPosts)
        progressBar = view.findViewById(R.id.progressBar)
        fab = view.findViewById(R.id.fab)

        setupRecyclerView()

        fab.setOnClickListener(this)
        subscribeObservers()
        viewModel.getPosts()
    }

    private fun setupRecyclerView() = recyclerView.apply {
        postAdapter = PostAdapter()
        adapter = postAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when(dataState) {
                is DataState.Success<List<Post>> -> {
                    displayProgressBar(false)
                    postAdapter.submitList(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        }

        viewModel.currentPosts.observe(
                viewLifecycleOwner,
                { list ->
                    when (list.size) {
                        0 -> {
                            Toast.makeText(requireContext(), "No posts", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            postAdapter.submitList(list)
                        }
                    }
                },
        )
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Unknown error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        v?.let {
            // Check if view is null
            findNavController().navigate(R.id.action_postsFragment_to_addFragment)
        }
    }

}