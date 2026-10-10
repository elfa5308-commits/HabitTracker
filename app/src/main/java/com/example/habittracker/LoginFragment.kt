package com.example.habittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edtUsername = view.findViewById<EditText>(R.id.edtUsername)
        val edtPassword = view.findViewById<EditText>(R.id.edtPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val txtError = view.findViewById<TextView>(R.id.txtError)

        btnLogin.setOnClickListener {

            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            if (username == "student" && password == "123") {
                txtError.text = "Login berhasil"

                findNavController().navigate(
                    R.id.action_loginFragment_to_dashboardFragment
                )

            } else {

                txtError.text = "Username atau password salah"

            }
        }
    }
}