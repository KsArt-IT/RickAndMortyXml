package pro.ksart.rickandmorty.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import pro.ksart.rickandmorty.databinding.ItemLoadStateFooterBinding

class CharactersLoadStateViewHolder(
    private val binding: ItemLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.also {
            it.setOnClickListener { retry.invoke() }
        }
    }

    fun bind(loadState: LoadState) {
        binding.run {
            errorMessage.text = if (loadState is LoadState.Error) loadState.error.localizedMessage
            else ""
            progressState.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMessage.isVisible = loadState is LoadState.Error
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit) = CharactersLoadStateViewHolder(
            ItemLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
    }
}
