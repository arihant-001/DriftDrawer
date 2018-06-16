package com.sdsmdg.aridj.popoutnavigationdrawer

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sdsmdg.aridj.lib.PopOutDrawer
import com.sdsmdg.aridj.lib.PopOutNavBuilder
import com.sdsmdg.aridj.popoutnavigationdrawer.fragments.EditorFragment
import com.sdsmdg.aridj.popoutnavigationdrawer.fragments.GalleryFragment
import com.sdsmdg.aridj.popoutnavigationdrawer.fragments.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SettingFragment.SettingFragmentClickListener {

    private lateinit var popOutDrawer: PopOutDrawer

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

        popOutDrawer = PopOutNavBuilder(this, toolbar)
                .withMenus(menus)
                .withDrawerClosed(false)
                .withColors(Color.parseColor("#E91E63"), Color.parseColor("#9C27B0"))
                .withItemClickListener(navItemListener)
                .build()
        popOutDrawer.setSelectedPosition(2)
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
            popOutDrawer.closeDrawer(animated)
        } else {
            popOutDrawer.openDrawer(animated)
        }
    }
}
