package com.dog.childgrowthmonitor

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dog.childgrowthmonitor.util.GLOBAL_CONTEXT
import com.dog.childgrowthmonitor.util.ToastMessage
import com.dog.childgrowthmonitor.util.styleAppName
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.InputStream

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = styleAppName(resources) ?: getString(R.string.app_name)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController: NavController = findNavController(R.id.nav_controller)

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_add_child
        )
            .setDrawerLayout(drawerLayout)
            .build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        assets.open("countries.json")
        GLOBAL_CONTEXT = applicationContext
    }

//    TODO no se que es esto
    private fun getImageFromAssets(imageName: String): Bitmap? {
        val assetManager: AssetManager = assets
        val inputStream: InputStream = assetManager.open(imageName)
        val bitmapImage = BitmapFactory.decodeStream(inputStream)
        return bitmapImage
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_controller)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_backup -> {
                ToastMessage("ESTE ES EL OTRO")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_backup -> {
                ToastMessage("ESTE ES EL OTRO DE ABAJO")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
