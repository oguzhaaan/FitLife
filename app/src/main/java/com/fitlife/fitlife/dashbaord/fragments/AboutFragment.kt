package com.fitlife.fitlife.dashbaord.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fitlife.fitlife.R
import com.fitlife.fitlife.dashbaord.DashbaordViewModel
import com.fitlife.fitlife.databinding.FragmentAboutBinding
import com.fitlife.fitlife.room_db.dao.FitLifeDao
import com.fitlife.fitlife.room_db.database.FitLifeDatabase
import com.fitlife.fitlife.room_db.entities.ExerciseDetailsEntitiy
import com.fitlife.fitlife.room_db.entities.LoggedUserInformation
import java.text.SimpleDateFormat
import java.util.*

class AboutFragment : Fragment() {
    private lateinit var aboutFramgment: FragmentAboutBinding

    private val viewModel: DashbaordViewModel by activityViewModels()
    private lateinit var mContext: Context
    private lateinit var exercseNameTv: TextView
    private lateinit var exercseDescriptionTv: TextView
    private lateinit var exercseImageIv: ImageView
    private lateinit var addExerciseButton: Button
    private lateinit var dao: FitLifeDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutFramgment = FragmentAboutBinding.inflate(inflater, container, false)
        aboutFramgment.viewModel = viewModel
        mContext = requireActivity()
        dao = FitLifeDatabase.getInstance(requireContext()).schoolDao()

        bindObservers()
        return aboutFramgment.root
    }

    private fun bindObservers() {
        val exerciseImage = arguments?.getInt("exerciseImage")
        val exerciseName = arguments?.getString("exerciseName")
        val exerciseDescription = arguments?.getString("exerciseDescription")
        exercseNameTv = aboutFramgment.exerciseName
        exercseDescriptionTv = aboutFramgment.exerciseDescription
        exercseImageIv = aboutFramgment.exerciseImage
        addExerciseButton = aboutFramgment.addexercise
        exercseImageIv.setImageResource(exerciseImage!!.toInt())
        exercseNameTv.text = exerciseName
        exercseDescriptionTv.text = exerciseDescription



        addExerciseButton.setOnClickListener {

            val getLoggedUser: LoggedUserInformation = dao.getLoginUserInformation()
            val userName: String = getLoggedUser.name
            val useremail: String = getLoggedUser.email
            val userGender: String = getLoggedUser.gender
            val currentDate: String =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            val builder = AlertDialog.Builder(
                mContext,
                androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog
            )
                .create()
            val view = layoutInflater.inflate(R.layout.custom_alert_dialog_for_adding, null)
            val setsEt = view.findViewById<EditText>(R.id.sets_count)
            val datePickerET = view.findViewById<EditText>(R.id.date_picker)
            val weightEt = view.findViewById<EditText>(R.id.weight_count)
            val repsEt = view.findViewById<EditText>(R.id.repitition_count)
            val addDetails = view.findViewById<Button>(R.id.add_details)
            val Dismiss = view.findViewById<Button>(R.id.dismiss)
            builder.setView(view)

            datePickerET.setOnClickListener {
                val newCalendar = Calendar.getInstance()
                val startDatePickerDialog = DatePickerDialog(mContext, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                        val newDate = Calendar.getInstance()
                        newDate[year, monthOfYear] = dayOfMonth
                        viewModel.date.set(SimpleDateFormat("dd-MM-yyyy").format(newDate.time))
                        datePickerET.setText(viewModel.date.get().toString())
                        Log.e("TAG", "onDateGet: "+viewModel.date.get() )
                    }
                }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
                    newCalendar[Calendar.DAY_OF_MONTH]
                )
                startDatePickerDialog.show()
            }


            addDetails.setOnClickListener {
                val ExerciseDetailsEntitiy = ExerciseDetailsEntitiy(
                    0,
                    useremail,
                    viewModel.date.get().toString(),
                    userName,
                    userGender,
                    exerciseImage,
                    exerciseName,
                    exerciseDescription,
                    sets = setsEt.text.toString(),
                    reps = repsEt.text.toString(),
                    weight = weightEt.text.toString()
                )
                val savedExercise = dao.saveTheExerciseOfTheUser(ExerciseDetailsEntitiy)
                val saveExerciseRowCount = dao.saveExerciseRowCount()
                val checkRow = saveExerciseRowCount - 1
                if (savedExercise > checkRow) {
                    Toast.makeText(
                        mContext,
                        getString(R.string.exercise_aaded_ssuc),
                        Toast.LENGTH_SHORT
                    ).show()
                    builder.dismiss()

                } else {
                    Toast.makeText(
                        mContext,
                        getString(R.string.exercise_not_aaded),
                        Toast.LENGTH_SHORT
                    ).show()
                    builder.dismiss()
                }

            }
            Dismiss.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }



    }

}