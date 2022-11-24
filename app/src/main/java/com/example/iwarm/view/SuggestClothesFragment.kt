package com.example.iwarm.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iwarm.R

class SuggestClothesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        Log.d("TAG", "onCreateView: ${arguments?.getBoolean("down")}")
        if (arguments?.getBoolean("down") == true) {
            Log.d("TAG", "onCreateView: ${arguments?.getBoolean("down")}")
        }

        return inflater.inflate(R.layout.fragment_suggest_clothes, container, false)
    }
}