package com.example.amphsesviewer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.example.amphsesviewer.ui.album.AlbumFragmentArgs
import kotlinx.android.synthetic.main.activity_main.*

const val REQUEST_GALLERY = 0

class MainActivity : AppCompatActivity() {

    private val args: MainActivityArgs by navArgs()

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var localBroadcastManager: LocalBroadcastManager

    fun setToolbarTitle(title: String?) {
        findViewById<Toolbar>(R.id.toolbar)?.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)

        val user = args.User
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.txt_user_name).text = user.userName

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_albums), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GALLERY -> {
                    val uri = data?.data
                    val intent = Intent(REQUEST_GALLERY.toString()).apply {
                        putExtra("imageUri", uri)
                    }
                    localBroadcastManager.sendBroadcast(intent)
                }
            }
        }
    }
}
