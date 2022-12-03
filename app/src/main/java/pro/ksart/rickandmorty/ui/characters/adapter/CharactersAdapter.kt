package pro.ksart.rickandmorty.ui.characters.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.data.entity.UiAction

class CharactersAdapter(
    private val onClick: (UiAction<Int>) -> Unit
) : PagingDataAdapter<CharacterRam, CharactersViewHolder>(CharactersDiffCallback()) {

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder.create(parent, onClick)
    }
}
