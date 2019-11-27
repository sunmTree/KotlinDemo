package com.kotlin.module.phone_number

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.phone_number_layout.*
import java.util.*

class PhoneNumberFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.phone_number_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val phoneNumberWatcher = PhoneNumberTextWatcher(Locale.getDefault().country)
        edit_phone_number.addTextChangedListener(phoneNumberWatcher)

        back.setOnClickListener { activity?.finish() }
    }

}