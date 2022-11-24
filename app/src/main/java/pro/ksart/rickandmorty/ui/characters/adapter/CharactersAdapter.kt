package pro.ksart.rickandmorty.ui.characters.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import pro.ksart.rickandmorty.data.entity.CharacterRam

class CharactersAdapter(
    private val onClick: (CharacterRam, ImageView) -> Unit
) : PagingDataAdapter<CharacterRam, CharactersViewHolder>(CharactersDiffCallback()) {

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder.create(parent, onClick)
    }
}
