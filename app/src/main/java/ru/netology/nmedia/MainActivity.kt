package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.servicecode.plural

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like?.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                likeCount?.text = plural(post.likes, 'K', 'M')
                shareCount?.text = plural(post.shared, 'K', 'M')
                viewsCount?.text = plural(post.viewed, 'K', 'M')
            }
        }

        binding.like?.setOnClickListener {
            viewModel.like()
        }

        binding.share?.setOnClickListener {
            viewModel.share()
        }
    }
}


