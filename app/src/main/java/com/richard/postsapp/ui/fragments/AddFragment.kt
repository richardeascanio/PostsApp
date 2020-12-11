package com.richard.postsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.richard.postsapp.R
import com.richard.postsapp.data.Post
import com.richard.postsapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddFragment: Fragment(R.layout.add_post_fragment), View.OnClickListener {

    private lateinit var editTextTitle : EditText
    private lateinit var editTextBody : EditText
    private lateinit var button : Button

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextTitle = view.findViewById(R.id.editTextTitle)
        editTextBody = view.findViewById(R.id.editTextBody)
        button = view.findViewById(R.id.button)

        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (editTextTitle.text.isNotEmpty() && editTextBody.text.isNotEmpty()) {
            val newPost = Post(
                    1,
                    101,
                    editTextTitle.text.toString(),
                    editTextBody.text.toString()
            )
            viewModel.addNewPost(newPost)
        }
    }
}