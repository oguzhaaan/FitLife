package com.fitlife.fitlife

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fitlife.fitlife.databinding.FragmentRegistrationBinding
import com.fitlife.fitlife.room_db.dao.FitLifeDao
import com.fitlife.fitlife.room_db.database.FitLifeDatabase
import com.fitlife.fitlife.room_db.entities.User
import kotlinx.coroutines.launch

class FragmentRegistration : Fragment() {
    private lateinit var fragmentRegistration:FragmentRegistrationBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var mContext: Context

    private lateinit var dao: FitLifeDao

    var name: EditText? = null
    var gender: EditText? = null
    var email: EditText? = null
    var password: EditText? = null
    var confrimpassword: EditText? = null
    var genderRadio: RadioGroup? = null
    var male: RadioButton? = null
    var female: RadioButton? = null
    var progressBar: ProgressBar? = null
    var registerbtn: Button? = null
    var selectedgender: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRegistration = FragmentRegistrationBinding.inflate(inflater, container, false)
        fragmentRegistration.viewModel = viewModel
        mContext = requireActivity()
        dao = FitLifeDatabase.getInstance(requireContext()).schoolDao()
        initView()
        bindObservers()

        return fragmentRegistration.root

    }

    private fun initView() {
        name = fragmentRegistration.UserName
        genderRadio = fragmentRegistration.radioGrp
        male =fragmentRegistration.radioM
        female = fragmentRegistration.radioF
        email = fragmentRegistration.emailEtR
        password = fragmentRegistration.passwordEtR
        confrimpassword = fragmentRegistration.confirmPasswordEt
        progressBar = fragmentRegistration.progressR
        registerbtn= fragmentRegistration.btnRegister

    }

    private fun bindObservers() {

        registerbtn!!.setOnClickListener {
            try {
                val name_emp = name!!.text.toString().trim { it <= ' ' }
//                    String gender_emp = radiogenderButton.getText().toString().trim();
                //                    String gender_emp = radiogenderButton.getText().toString().trim();
                val email_emp = email!!.text.toString().trim { it <= ' ' }
                val password_emp = password!!.text.toString().trim { it <= ' ' }
                val confirm_pass_emp = confrimpassword!!.text.toString().trim { it <= ' ' }

//                    Toast.makeText(RegisterationActivity.this, ""+gender_emp, Toast.LENGTH_SHORT).show();

//                    Toast.makeText(RegisterationActivity.this, ""+gender_emp, Toast.LENGTH_SHORT).show();
                if (name_emp.isEmpty()) {
                    name!!.error = getString(R.string.name_is_Required)
                    name!!.requestFocus()
                    return@setOnClickListener
                }

                if (email_emp.isEmpty()) {
                    email!!.error = getString(R.string.email_is_required)
                    email!!.requestFocus()
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email_emp).matches()) {
                    email!!.error = getString(R.string.provide_valid_email)
                    email!!.requestFocus()
                    return@setOnClickListener
                }

                if (password_emp.isEmpty()) {
                    password!!.error = getString(R.string.password_req)
                    password!!.requestFocus()
                    return@setOnClickListener
                }
                if (confirm_pass_emp.isEmpty()) {
                    confrimpassword!!.error = getString(R.string.confrim_pass_req)
                    confrimpassword!!.requestFocus()
                    return@setOnClickListener
                }
                if (male!!.isChecked) {
                    selectedgender = male!!.text.toString().trim { it <= ' ' }
                }
                if (female!!.isChecked) {
                    selectedgender = female!!.text.toString().trim { it <= ' ' }
                }


                if (password_emp == confirm_pass_emp) {
                    progressBar!!.visibility=View.VISIBLE

                    val user = User(name_emp,email_emp,selectedgender.toString(),password_emp)
                    lifecycleScope.launch {
                        val rowCount:Long=  dao.getRowCount()
                        val addUserId:Long=  dao.insertUser(user)
                        Log.e("TAG", "UserModel: "+user)
                        Log.e("TAG", "addUserId: "+addUserId)
                        Log.e("TAG", "rowCount: "+rowCount)

                        if(addUserId>rowCount){
                            progressBar!!.visibility=View.GONE

                            Toast.makeText(
                                mContext,
                                getString(R.string.user_register_successfully),
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        else{
                            progressBar!!.visibility=View.GONE
                            Toast.makeText(
                                mContext,
                                getString(R.string.user_not_Reg),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                } else {
                    Toast.makeText(
                        mContext,
                        getString(R.string.password_must_6_charct),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("TAG", "exception: "+e.localizedMessage )
            }

        }
    }
}