package com.example.developerbuddy.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.developerbuddy.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DetailFragment.Listener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this, R.id.fragment)
        setupActionBarWithNavController(this, navController, null)
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
            .build()

        fab.setOnClickListener { view ->

        }

        fab.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about -> {
                showDialogue()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialogue() {
        val builder = AlertDialog.Builder(this@MainActivity)

        // Set the alert dialog title
        builder.setTitle("About DeveloperBuddy")

        // Display a message on alert dialog
        builder.setMessage("DeveloperBuddy was developed by Manuel carvalho")

        builder.setNeutralButton("Return") { _, _ ->

        }


        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun unHideFAB(b: Boolean) {
        if (b) {
            fab.show()
        } else {
            fab.hide()
        }
    }
}
