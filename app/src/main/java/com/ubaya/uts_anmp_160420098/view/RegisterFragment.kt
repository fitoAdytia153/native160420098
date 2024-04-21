package com.ubaya.uts_anmp_160420098.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.uts_anmp_160420098.R
import com.ubaya.uts_anmp_160420098.databinding.FragmentProfileBinding
import com.ubaya.uts_anmp_160420098.databinding.FragmentRegisterBinding
import org.json.JSONException
import org.json.JSONObject

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.buttonRegister.setOnClickListener {
            registerAccount()
        }
        binding.btnBackLogin.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun registerAccount() {
        val namaDepan = binding.txtFrstName.text.toString()
        val namaBelakang= binding.txtLastName.text.toString()
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        if (namaDepan.isEmpty() || namaBelakang.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
        }

        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.0.171/hero/register.php"

        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val cleanedResponse = response.replace("<br>".toRegex(), "")
                try {
                    val obj = JSONObject(cleanedResponse)
                    val message = obj.getString("message")

                    if (message == "User registered successfully") {
                        Toast.makeText(context, "Registration successful", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error parsing JSON response: ${e.message}", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(context, "Error: ${volleyError.message}", Toast.LENGTH_LONG).show()
                volleyError.printStackTrace()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["namaDepan"] = namaDepan
                params["namaBelakang"] = namaBelakang
                params["user"] = username
                params["pass"] = password
                return params
            }
        }
        queue.add(stringRequest)
    }
}