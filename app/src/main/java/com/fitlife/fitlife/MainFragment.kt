package com.fitlife.fitlife

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fitlife.fitlife.dashbaord.DashbaordActivity
import com.fitlife.fitlife.databinding.FragmentMainBinding
import com.fitlife.fitlife.resources.ON_LOGIN_BTN_CLICKED
import com.fitlife.fitlife.resources.ON_SIGN_UP_BTN_CLICKED
import com.fitlife.fitlife.room_db.dao.FitLifeDao
import com.fitlife.fitlife.room_db.database.FitLifeDatabase
import com.fitlife.fitlife.room_db.entities.LoggedUserInformation
import com.fitlife.fitlife.room_db.entities.User
import java.util.*
import kotlin.system.exitProcess


class MainFragment : Fragment() {
    private lateinit var mainFragment: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var mContext: Context
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var progressBar: ProgressBar
    private lateinit var dao: FitLifeDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainFragment = FragmentMainBinding.inflate(inflater, container, false)
        mainFragment.viewModel = viewModel
        mContext = requireActivity()
        dao = FitLifeDatabase.getInstance(requireContext()).schoolDao()

        initViews()
        bindObservers()
        try{
            val list = ArrayList<String>()
            list.add("Select Language")
            list.add("English")
            list.add("Turkish")

            val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mainFragment.chnageLangauge.adapter = adapter
            mainFragment.chnageLangauge.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    when (position) {
                        0 -> {
                        }
                        1 -> setLocale(requireActivity() as FragmentActivity,"en")
                        2 -> setLocale(requireActivity() as FragmentActivity,"tr")

                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }





        return mainFragment.root
    }

    private fun initViews() {
        email = mainFragment.emailEt
        password = mainFragment.passwordEt
        progressBar = mainFragment.progressbar
    }

    private fun bindObservers() {
        mainFragment?.viewModel?.clickEvent?.observe(requireActivity(), Observer {
            when (it) {
                ON_LOGIN_BTN_CLICKED -> {
                    if (validate()) {
                        progressBar.visibility = View.VISIBLE
                        val userEmail = email.text.toString()
                        val userPassword = password.text.toString()
                        val userData: User = dao.getUserData(userEmail, userPassword)
                        try {
                            if (userEmail.equals(userData.email) && userPassword.equals(userData.password)) {
                                progressBar.visibility = View.GONE
                                val loggedUserInformation = LoggedUserInformation(
                                    userData.name,
                                    userData.email,
                                    userData.gender,
                                    userData.password
                                )
                                dao.saveLoggedUser(loggedUserInformation)
                                val intent = Intent(activity, DashbaordActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                    mContext,
                                    getString(R.string.User_Found_Login),
                                    Toast.LENGTH_SHORT
                                ).show()

                                requireActivity().finish()

                            } else if (userData.email.equals(null)) {
                                progressBar.visibility = View.GONE

                                Toast.makeText(
                                    mContext,
                                    getString(R.string.user_not_login),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                progressBar.visibility = View.GONE

                                Toast.makeText(
                                    mContext,
                                    getString(R.string.user_not_login),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                mContext,
                                getString(R.string.user_not_login),
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("TAG", "bindObservers: " + e.message)
                        }

//                        for(i in usersList){
//                            if(i.email.equals(userEmail) || i.password.equals(userPassword)){
//                                progressBar.visibility=View.GONE
//
////                                dao.SaveLoggedUser(i)
//                                Log.e("TAG", "bindObservers: "+i )
//                                Log.e("TAG", "bindObservers: "+i.email )
//                                Log.e("TAG", "bindObservers: "+i.password )
//                                Log.e("TAG", "bindObservers: "+userEmail )
//                                Log.e("TAG", "bindObservers: "+userPassword )
//                                val intent = Intent(activity, DashbaordActivity::class.java)
//                                startActivity(intent)
//                            }else{
//                                progressBar.visibility=View.GONE
//                                Toast.makeText(
//                                    mContext,
//                                    "User not Found",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//
//                            }
//                        }
                    }

                }

                ON_SIGN_UP_BTN_CLICKED -> {
                    findNavController().navigate(
                        R.id.action_mainFragment_to_fragmentRegistration,
                    )
                }
            }
        })

    }

    fun validate(): Boolean {
        if (email.text.toString().equals("") || email.text.toString() == null) {
            email!!.error = getString(R.string.name_is_required)
            email!!.requestFocus()
            return false
        } else if (password.text.toString().equals("") || password.text.toString().equals("")) {
            password!!.error = getString(R.string.password_isrequired)
            password!!.requestFocus()
            return false
        } else {
            return true
        }

    }

    fun setLocale(activity: FragmentActivity, languageCode: String?) {
        try {
            val res: Resources = requireActivity().resources
            val locale = Locale(languageCode!!) // for example "en"
            val conf: Configuration = res.getConfiguration()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                conf.setLocale(locale)
                conf.setLayoutDirection(locale)
            } else {
                conf.locale = locale
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                requireActivity().applicationContext.createConfigurationContext(conf)
            }
            res.updateConfiguration(conf, null)
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)


        } catch (e: Exception) {
            Log.e("TAG", "setLocale: ${e.message}", )
        }
    }
}