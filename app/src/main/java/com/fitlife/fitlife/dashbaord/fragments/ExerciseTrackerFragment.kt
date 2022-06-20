package com.fitlife.fitlife.dashbaord.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitlife.fitlife.R
import com.fitlife.fitlife.dashbaord.DashbaordActivity
import com.fitlife.fitlife.dashbaord.DashbaordViewModel
import com.fitlife.fitlife.dashbaord.adapters.ExerciseTrackingAdapter
import com.fitlife.fitlife.databinding.FragmentExerciseTrackerBinding
import com.fitlife.fitlife.room_db.dao.FitLifeDao
import com.fitlife.fitlife.room_db.database.FitLifeDatabase
import com.fitlife.fitlife.room_db.entities.ExerciseDetailsEntitiy
import com.fitlife.fitlife.room_db.entities.LoggedUserInformation


class ExerciseTrackerFragment : Fragment() {


    private lateinit var exerciseTrackingFragment: FragmentExerciseTrackerBinding
    private val viewModel: DashbaordViewModel by activityViewModels()
    private lateinit var mContext: Context
    private var main: DashbaordActivity? = null
    private lateinit var dao: FitLifeDao
    private lateinit var exerciseDetails: ExerciseTrackingAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity)
            main = activity as DashbaordActivity?
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        exerciseTrackingFragment =
            FragmentExerciseTrackerBinding.inflate(inflater, container, false)
        exerciseTrackingFragment.viewModel = viewModel
        mContext = requireActivity()
        ((requireActivity() as AppCompatActivity).supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        ((requireActivity() as AppCompatActivity).supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_menu_24)
        dao = FitLifeDatabase.getInstance(requireContext()).schoolDao()
        initRecyclerView()




        return exerciseTrackingFragment.root
    }

    private fun initRecyclerView() {
        val getAllExercise: ArrayList<ExerciseDetailsEntitiy> =
            dao.getSaveExerciseOfUser() as ArrayList<ExerciseDetailsEntitiy>
        val emptyLL:LinearLayout=exerciseTrackingFragment.emptyLL

        val getLoggedUser: LoggedUserInformation = dao.getLoginUserInformation()



        val loggedInUserExerciseOnly:ArrayList<ExerciseDetailsEntitiy> = ArrayList()
        Log.e("TAG", "getLoggedUser: "+getLoggedUser.email )
        loggedInUserExerciseOnly.clear()
        for(i in getAllExercise){
            if(getLoggedUser.email.equals(i.useremail)){
                loggedInUserExerciseOnly.add(i)
            }

        }
        if(loggedInUserExerciseOnly.isNullOrEmpty()){
            emptyLL.visibility=View.VISIBLE
        } else{
            emptyLL.visibility=View.GONE

        }


        exerciseTrackingFragment.exerciseTrackingRecyclerView.apply {
            layoutManager = LinearLayoutManager(mContext)
            exerciseDetails = ExerciseTrackingAdapter(mContext)

            exerciseDetails.setExerciseList(loggedInUserExerciseOnly)
            exerciseDetails.setOnClickMenuListener(mOnClickRowConsignmentListener)
            exerciseDetails.onClickRowSchool
            adapter = exerciseDetails

        }


        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                showLogoutDialog()

            }
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), onBackPressedCallback)


    }






    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custm_alert_logout, null)
        val btnOK = view.findViewById<Button>(R.id.btn_ok)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        builder.setView(view)
        btnOK.setOnClickListener {
            val getLoggedUser: LoggedUserInformation = dao.getLoginUserInformation()

            val deleteLoggedUser=dao.clearLoggedUserData(getLoggedUser)

            Log.e("TAG", "showLogoutDialog: $deleteLoggedUser", )
//
//            if(deleteLoggedUser>0){
//                Toast.makeText(
//                    mContext,
//                    "User LogOut",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            requireActivity().moveTaskToBack(true);
            requireActivity().finish();
        }
        btnCancel.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }



    private val mOnClickRowConsignmentListener: ExerciseTrackingAdapter.OnClickRowSchoolListener =
        object : ExerciseTrackingAdapter.OnClickRowSchoolListener {
            override fun onClickDeleteStudent(
                position: Int,
                exerciseDetail: ExerciseDetailsEntitiy?
            ) {
                Toast.makeText(
                    requireContext(),
                    "details clicked :" + position + "exersiseName :" + exerciseDetail!!.exersiseName,
                    Toast.LENGTH_SHORT
                ).show()

                val deleteExercise= dao.deleteExercise(exerciseDetail)


                if(deleteExercise==1){
                    Toast.makeText(
                        requireContext(),
                        " " + exerciseDetail!!.exersiseName +" is Deleted",
                        Toast.LENGTH_SHORT
                    ).show()

                    exerciseDetails.removeItemFromRecyclerView(position)
                    exerciseDetails.notifyDataSetChanged()
                }

            }
        }
}