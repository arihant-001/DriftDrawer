package com.sdsmdg.aridj.popoutnavigationdrawer.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sdsmdg.aridj.popoutnavigationdrawer.R
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {
    private var listener: SettingFragmentClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        close_drawer.setOnClickListener {
            onButtonPressed(it)
        }
        close_drawer_anim.setOnClickListener {
            onButtonPressed(it)
        }
        open_drawer.setOnClickListener {
            onButtonPressed(it)
        }
        open_drawer_anim.setOnClickListener {
            onButtonPressed(it)
        }
    }
    private fun onButtonPressed(view: View) {
        when(view.id) {
            R.id.close_drawer -> listener?.onButtonClicked(true, false)
            R.id.close_drawer_anim -> listener?.onButtonClicked(true, true)
            R.id.open_drawer -> listener?.onButtonClicked(false, false)
            R.id.open_drawer_anim -> listener?.onButtonClicked(false, true)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SettingFragmentClickListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement SettingFragmentClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface SettingFragmentClickListener {

        fun onButtonClicked(close: Boolean, animated: Boolean)
    }

}
