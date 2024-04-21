package com.ubaya.uts_anmp_160420098.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.uts_anmp_160420098.databinding.FragmentProfileBinding
import com.ubaya.uts_anmp_160420098.model.Account
import com.ubaya.uts_anmp_160420098.viewmodel.ViewModel
import org.json.JSONException
import org.json.JSONObject

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences("account", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtUsername.setText(sharedPreferences.getString("username", ""))
        binding.txtFirstName.setText(sharedPreferences.getString("nama_depan", ""))
        binding.txtLastName.setText(sharedPreferences.getString("nama_belakang", ""))

        viewModel.accountLD.observe(viewLifecycleOwner) { acc ->
            if (acc != null) {
                binding.txtUsername.setText(acc.username)
                binding.txtFirstName.setText(acc.firstName)
                binding.txtLastName.setText(acc.lastName)
            }
        }

        binding.btnUpdate.setOnClickListener {
            updateAccount()
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun updateAccount() {
        val namaDepan = binding.txtFirstName.text.toString()
        val sharedNd = sharedPreferences.getString("nama_depan", "")

        val namaBelakang = binding.txtLastName.text.toString()
        val sharedNb = sharedPreferences.getString("nama_belakang", "")

        val username = binding.txtUsername.text.toString()
        val sharedUser = sharedPreferences.getString("username", "")

        val password = binding.txtPassword.text.toString()
        val sharedPass = sharedPreferences.getString("password", "")

        if (namaDepan == sharedNd && namaBelakang == sharedNb &&
            username == sharedUser && password == sharedPass) {
            Toast.makeText(context, "No changes to update", Toast.LENGTH_SHORT).show()
            return
        }

        val queue = Volley.newRequestQueue(context)
        val url = "http://192.168.0.171/hero/update_akun.php"

        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")

                    if (status == "success") {
                        // Mengupdate sharedPrefence dengan data yang baru
                        val editor = sharedPreferences?.edit()
                        if (username != sharedUser) {
                            editor?.putString("username", username)
                        }
                        if (namaDepan != sharedNb) {
                            editor?.putString("nama_depan", namaDepan)
                        }
                        if (namaBelakang != sharedNb) {
                            editor?.putString("nama_belakang", namaBelakang)
                        }
                        if (password.isNotEmpty() && password != sharedPass) {
                            editor?.putString("password", password)
                        }
                        editor?.apply()

                        viewModel.accountLD.postValue(Account(username, namaDepan, namaBelakang, password))
                        Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
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
                params["nama_depan"] = namaDepan
                params["nama_belakang"] = namaBelakang
                params["password"] = password
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun logout() {
        viewModel.clearAccount()
        findNavController().navigate(ProfileFragmentDirections.actionAfterLogout())
    }
}