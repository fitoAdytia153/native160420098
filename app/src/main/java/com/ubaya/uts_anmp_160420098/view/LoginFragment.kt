package com.ubaya.uts_anmp_160420098.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.uts_anmp_160420098.R
import com.ubaya.uts_anmp_160420098.databinding.FragmentLoginBinding
import org.json.JSONException
import org.json.JSONObject

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun login() {
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.0.171/hero/login.php"

        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")

                    if (status == "success") {
                        val account = jsonObject.getJSONObject("account")
                        saveAccount(account)
                        findNavController().navigate(R.id.actionAfterLogin)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(context, "JSON parsing error: " + e.message, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "Volley error: " + error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        queue.add(stringRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.txtRegister.setOnClickListener {
            navigateToRegisterFragment()
        }
    }

    private fun navigateToRegisterFragment() {
        findNavController().navigate(R.id.actionToRegister)
    }

    private fun saveAccount(account: JSONObject) {
        val sharedPreferences = requireActivity().getSharedPreferences("account", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("username", account.getString("username"))
            putString("nama_depan", account.getString("nama_depan"))
            putString("nama_belakang", account.getString("nama_belakang"))
            apply()
        }
    }
}