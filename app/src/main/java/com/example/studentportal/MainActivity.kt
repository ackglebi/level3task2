package com.example.studentportal

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_portal.*
import kotlin.math.log

const val REQUEST_PORTAL_CODE = 100

class MainActivity : AppCompatActivity() {

    private val portals = arrayListOf<Portal>()

    // TODO wat moet met het tweede argument meegegeven wordne
    private val portalAdapter = PortalAdapter(portals) { portalItem: Portal -> portalItemClicked(portalItem)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
        rvPortals.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvPortals.adapter = portalAdapter

        fab.setOnClickListener { onAddClick() }

    }

    private fun onAddClick() {

        // switch to create profile intent
        val createProfileIntent = Intent(this, CreatePortalActivity::class.java)
        startActivityForResult(createProfileIntent, REQUEST_PORTAL_CODE)
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("[i] onActivityResult", portals.size.toString())
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                REQUEST_PORTAL_CODE -> {
                    val portal = data!!.getParcelableExtra<Portal>(PORTAL_EXTRA)
                    portals.add(portal)
                    Log.i("[i] SIZE OF PORTALS", portals.size.toString())
                    portalAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    /**
     * used when clicked on a portal
     */
    private fun portalItemClicked(portalItem : Portal) {
        Toast.makeText(this, "Clicked: ${portalItem.title}", Toast.LENGTH_SHORT).show()

        val url = portalItem.url.toString()

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))

    }
}
