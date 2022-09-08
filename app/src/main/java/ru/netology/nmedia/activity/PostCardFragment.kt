package ru.netology.nmedia.activity

import android.net.Uri
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.*
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PostCardFragment : Fragment() {

    companion object {
        var Bundle.idArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CardPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.idArg?.let {
            viewModel.getPostById(it.toLong())
        }?.let { post ->

            val onInteractionListener = object : OnInteractionListener {
                override fun onContent(post: Post) {
                    viewModel.edit(post)

                    findNavController().navigate(
                        R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        }
                    )
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)

                    findNavController().navigate(
                        R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        }
                    )
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                    findNavController().navigateUp()
                }

                override fun onShare(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)

                    viewModel.shareById(post.id)
                }

                override fun onPlayVideo(post: Post) {
                    if (Uri.parse(post.video).isHierarchical) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                    val intent = Intent().apply {
//                        action = Intent.ACTION_VIEW
//                        setDataAndType(Uri.parse(post.video), "video/*")
//                    }
                        val shareIntent =
                            Intent.createChooser(intent, getString(R.string.chooser_share_post))
                        startActivity(shareIntent)

                    } else {
                        Snackbar.make(
                            binding.root, R.string.error_ref_entry,
                            BaseTransientBottomBar.LENGTH_INDEFINITE
                        )
                            .setAction(android.R.string.ok) {
                                findNavController().navigateUp()
                            }
                            .show()
                    }
                }
            }

            postBinding(post, binding, onInteractionListener)
            viewModel.dataPost.observe(viewLifecycleOwner) {
                postBinding(it, binding, onInteractionListener)
            }
        }

        return binding.root
    }
}