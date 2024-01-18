package com.notfoundteam.careergrowapp.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.notfoundteam.careergrowapp.R
import com.notfoundteam.careergrowapp.databinding.ActivityMainBinding
import com.notfoundteam.careergrowapp.ui.ViewModelFactory
import com.notfoundteam.careergrowapp.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* kode bermasalah
        Log.d("MainActivity", "MainViewModel instance: $viewModel")
        observeSession()

        */

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        supportActionBar?.title = "CareerGrow"

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_milestone,
                R.id.navigation_notifications,
                R.id.navigation_profile,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    /* kode bermasalah
    private fun observeSession() {
        viewModel.getSession().observe(this){ user ->
            if (!user.isLogin) {
                // User belum login, arahkan ke LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                // User sudah login, tampilkan halaman MainActivity
                showLoading(false)
            }
        }
    }

     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searView = menu?.findItem(R.id.search)?.actionView as SearchView

        searView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searView.queryHint = resources.getString(R.string.search_hint)
        searView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //set search logic here
                searView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                showLoading(true)
                signOut()
                Toast.makeText(this, "Anda telah Logout dari CareerGrow", Toast.LENGTH_SHORT)
                    .show()
                true
            }

            R.id.action_setting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }

            else -> false
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun showLoading(isLoading : Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

}