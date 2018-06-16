package com.sdsmdg.aridj.driftdrawerdemo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sdsmdg.aridj.driftdrawer.DriftDrawer
import com.sdsmdg.aridj.driftdrawer.DriftDrawerBuilder
import com.sdsmdg.aridj.driftdrawerdemo.fragments.EditorFragment
import com.sdsmdg.aridj.driftdrawerdemo.fragments.GalleryFragment
import com.sdsmdg.aridj.driftdrawerdemo.fragments.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SettingFragment.SettingFragmentClickListener {

    private lateinit var driftDrawer: DriftDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Sample App"

        val menus = ArrayList<Int>()
        menus.add(R.drawable.ic_archive_black_24dp)
        menus.add(R.drawable.ic_apps_black_24dp)
        menus.add(R.drawable.ic_border_color_black_24dp)
        menus.add(R.drawable.ic_build_black_24dp)
        // for demo

        driftDrawer = DriftDrawerBuilder(this, toolbar)
                .withMenus(menus)
                .withDrawerClosed(false)
                .withColors(Color.parseColor("#E91E63"), Color.parseColor("#9C27B0"))
                .withItemClickListener(navItemListener)
                .build()
        driftDrawer.setSelectedPosition(2)
    }

    private val navItemListener: (Int, View) -> Unit = { pos: Int, view: View ->
        when (pos) {
            1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, GalleryFragment())
                        .commit()
                supportActionBar?.title = "Gallery"
            }
            2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, EditorFragment())
                        .commit()
                supportActionBar?.title = "Editor"
            }
            3 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, SettingFragment())
                        .commit()
                supportActionBar?.title = "Settings"
            }
            else -> {

            }
        }
    }

    override fun onButtonClicked(close: Boolean, animated: Boolean) {
        if (close) {
            driftDrawer.closeDrawer(animated)
        } else {
            driftDrawer.openDrawer(animated)
        }
    }
}
