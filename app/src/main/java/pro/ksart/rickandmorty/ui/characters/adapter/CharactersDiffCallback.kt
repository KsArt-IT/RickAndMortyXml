package pro.ksart.rickandmorty.ui.characters.adapter

import androidx.recyclerview.widget.DiffUtil
import pro.ksart.rickandmorty.data.entity.CharacterRam

class CharactersDiffCallback : DiffUtil.ItemCallback<CharacterRam>() {
    override fun areItemsTheSame(oldItem: CharacterRam, newItem: CharacterRam): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterRam, newItem: CharacterRam): Boolean {
        return oldItem == newItem
    }
}
