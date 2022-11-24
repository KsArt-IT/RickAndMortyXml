package pro.ksart.rickandmorty.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.databinding.ItemCharacterBinding

class CharactersViewHolder(
    private val binding: ItemCharacterBinding,
    private val onClick: (CharacterRam, ImageView) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    var item: CharacterRam? = null
        private set

    init {
        binding.run { root.setOnClickListener { item?.let { onClick(it, imageView) } } }
    }

    fun bind(item: CharacterRam) {
        this.item = item

        binding.run {
            imageView.apply { transitionName = item.id.toString() }
                .load(item.image) {
                    crossfade(true)
                    placeholder(R.drawable.ic_download)
                    error(R.drawable.ic_error)
                    build()
                }
            name.text = item.name
            status.text = "${item.status} - ${item.species}"
            location.text = item.location.name
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (CharacterRam, ImageView) -> Unit,
        ) = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { CharactersViewHolder(it, onClick) }
    }
}
