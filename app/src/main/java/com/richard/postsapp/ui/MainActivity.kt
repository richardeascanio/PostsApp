package com.richard.postsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import com.richard.postsapp.R
import com.richard.postsapp.data.Post
import com.richard.postsapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "AppDebug"
    private lateinit var text: TextView
    private lateinit var progressBar: ProgressBar

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.text)
        progressBar = findViewById(R.id.progressBar)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetPostEvents)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when(dataState) {
                is DataState.Success<List<Post>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
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
    }

    private fun displayError(message: String?) {
        if (message != null) {
            text.text = message
        } else {
            text.text = "Unknown error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlogTitles(posts: List<Post>) {
        val sb = StringBuilder()
        for ((pos, post) in posts.withIndex()) {
            sb.append("$pos ${ post.title } \n")
        }
        text.text = sb.toString()
    }
}