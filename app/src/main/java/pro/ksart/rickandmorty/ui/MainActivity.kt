package pro.ksart.rickandmorty.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import pro.ksart.rickandmorty.R
import pro.ksart.rickandmorty.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesHelper.setTheme()

        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)
        initAppBar()
    }

    private fun initAppBar() {
        navController = (supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        // обработаем AppBarConfiguration, прописать все верхние уровни id
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.charactersFragment,
            )
        )

        setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_night -> {
                    preferencesHelper.switchDarkTheme()
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}
