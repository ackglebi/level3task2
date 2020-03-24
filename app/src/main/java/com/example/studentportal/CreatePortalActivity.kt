package com.example.studentportal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_portal.*
import kotlinx.android.synthetic.main.item_portal.*
import java.net.URL

const val PORTAL_EXTRA = "PORTAL_EXTRA"

class CreatePortalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_portal)

        initViews()
    }

    private fun initViews() {
        addPortalButton.setOnClickListener { onClickAddPortal() }
    }

    private fun onClickAddPortal() {

        // check if name is not blank and url is valid
        if (etTitle.text.toString().isNotBlank() &&
            Patterns.WEB_URL.matcher(etUrl.text.toString()).matches()) {

            // create portal
            val portal = Portal(
                etTitle.text.toString(),
                URL(etUrl.text.toString())
            )

            val portalIntent = Intent()
            portalIntent.putExtra(PORTAL_EXTRA, portal)
            setResult(Activity.RESULT_OK, portalIntent)
            finish()

        } else {
            Toast.makeText(this, "Title / URL not valid. Try again!",
                Toast.LENGTH_SHORT).show()
        }
    }
}
