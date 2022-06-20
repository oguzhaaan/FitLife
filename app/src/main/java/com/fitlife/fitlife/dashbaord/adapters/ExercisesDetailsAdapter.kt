package com.fitlife.fitlife.dashbaord.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fitlife.fitlife.R
import com.fitlife.fitlife.databinding.CustomRowForExerciseTypeBinding
import com.fitlife.fitlife.model.ExerciseDetail


class ExercisesDetailsAdapter (val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var exercisesdetail: ArrayList<ExerciseDetail>
    private  var positionItem:Int = 0
    lateinit var onExerciseClicked: OnClickRowExerciseListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: CustomRowForExerciseTypeBinding = CustomRowForExerciseTypeBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NotificationViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return exercisesdetail.size
    }

    inner class NotificationViewHolder(view: CustomRowForExerciseTypeBinding) :
        RecyclerView.ViewHolder(view.root) {
        private var exerciseImage = view.exericeImage
        private var exerciseName = view.exericeName
        private var exerciseDiscription = view.exericeDescription
        var navController: NavController? = null

        fun bind(position: Int) {
            val exercisesDetail: ExerciseDetail = exercisesdetail[position]
            exerciseName.text =exercisesDetail.exersiseName
            exerciseImage.setImageResource(exercisesDetail.exerciseImage!!.toInt())
            exerciseDiscription.text=exercisesDetail.exerciseDescription
            itemView.setOnClickListener {view->
                val bundle = Bundle()
                bundle.putInt("exerciseImage", exercisesDetail.exerciseImage!!)
                bundle.putString("exerciseName", exercisesDetail.exersiseName!!)
                bundle.putString("exerciseDescription", exercisesDetail.exerciseDescription!!)
                view.findNavController().navigate(R.id.action_exerciseCategories_to_aboutFragment, bundle)
                                Toast.makeText(
                                    context,
                                    "exerciseName"+exerciseName.text.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()

            }
        }

    }

    fun setExerciseDetailList(exerciseList: ArrayList<ExerciseDetail>,position: Int) {
        this.exercisesdetail = exerciseList
        this.positionItem=position
    }


    fun setOnClickMenuListener(onClickRowSchoolListener: OnClickRowExerciseListener) {
        this.onExerciseClicked = onClickRowSchoolListener
    }
    interface OnClickRowExerciseListener {
        fun onClickGym(position: Int, exercise: ExerciseDetail?)

    }
}