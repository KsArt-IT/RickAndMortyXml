package pro.ksart.rickandmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.data.entity.CharacterRam
import pro.ksart.rickandmorty.data.entity.UiEvent
import pro.ksart.rickandmorty.data.entity.UiState
import pro.ksart.rickandmorty.databinding.FragmentCharactersBinding
import pro.ksart.rickandmorty.ui.characters.adapter.CharactersAdapter
import pro.ksart.rickandmorty.ui.characters.adapter.CharactersLoadStateAdapter
import pro.ksart.rickandmorty.ui.toast

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters) {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel: CharactersViewModel by viewModels()

    private var _charactersAdapter: CharactersAdapter? = null
    private val charactersAdapter
        get() = checkNotNull(_charactersAdapter) { "MovieTop adapter is`t initialized" }

    // flip animation+Navigation
    private val navOptions by lazy {
        NavOptions.Builder()
            .setEnterAnim(R.animator.card_flip_right_in)
            .setExitAnim(R.animator.card_flip_right_out)
            .setPopEnterAnim(R.animator.card_flip_left_in)
            .setPopExitAnim(R.animator.card_flip_left_out)
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCharactersBinding.inflate(inflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initListener()
        initObserve()
    }

    override fun onDestroyView() {
        _binding = null
        _charactersAdapter = null
        super.onDestroyView()
    }

    private fun initAdapter() {
        _charactersAdapter = CharactersAdapter(::showCharacterDetail)
        binding.recycler.run {
            adapter = charactersAdapter.withLoadStateHeaderAndFooter(
                header = CharactersLoadStateAdapter { charactersAdapter.retry() },
                footer = CharactersLoadStateAdapter { charactersAdapter.retry() }
            )
            charactersAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            layoutManager = LinearLayoutManager(requireContext())

            setHasFixedSize(true)
            isNestedScrollingEnabled = false

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
    }

    private fun initListener() {
        binding.retryButton.setOnClickListener { charactersAdapter.retry() }
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collectLatest { state ->
                        when (state) {
                            is UiState.Success -> {
                                // в адаптер
                                charactersAdapter.submitData(state.data)
                            }
                            is UiState.Loading -> {}
                            is UiState.Error -> {}
                        }
                    }
                }
                launch {
                    viewModel.uiEvent.collectLatest { event ->
                        when (event) {
                            is UiEvent.Success -> {}
                            is UiEvent.Toast -> toast(event.stringId)
                            is UiEvent.Error -> toast(event.message)
                        }
                    }
                }
                launch {
                    charactersAdapter.loadStateFlow.collect { loadState ->
                        val isListEmpty =
                            loadState.refresh is LoadState.NotLoading && charactersAdapter.itemCount == 0
                        binding.run {
                            // show empty list
                            emptyListTextView.isVisible = isListEmpty
                            // Only show the list if refresh succeeds.
                            recycler.isVisible = !isListEmpty
                            // Show loading spinner during initial load or refresh.
                            progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                            // Show the retry state if initial load or refresh fails.
                            retryButton.isVisible = loadState.source.refresh is LoadState.Error
                        }

                    }
                }
            }
        }
    }

    private fun showCharacterDetail(character: CharacterRam, imageView: ImageView) {
    }
}
