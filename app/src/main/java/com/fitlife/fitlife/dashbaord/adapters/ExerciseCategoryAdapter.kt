package com.fitlife.fitlife.dashbaord.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitlife.fitlife.R
import com.fitlife.fitlife.databinding.CustomRowForExerciseCategoryBinding
import com.fitlife.fitlife.model.ExerciseDetail



class ExerciseCategoryAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var exercisesCategory: ArrayList<String>
    private lateinit var exercisesDetailsList: ArrayList<ExerciseDetail>
    private lateinit var exerciseDetailsAdapter: ExercisesDetailsAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: CustomRowForExerciseCategoryBinding = CustomRowForExerciseCategoryBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NotificationViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return exercisesCategory.size
    }

    inner class NotificationViewHolder(view: CustomRowForExerciseCategoryBinding) :
        RecyclerView.ViewHolder(view.root) {
        private var exerciseTitle = view.exercisesTitle
        private var arrowIndicator = view.ivGroupIndicator
        private var exerciseDetaillayout = view.exercisesDetailListLL
        private var exerciseDetailRv: RecyclerView = view.exersisesDetailRv


        fun bind(position: Int) {
            val exercisesCategory = exercisesCategory[position]
            exerciseTitle.text = exercisesCategory
            if (position == 0) {
                val exercises: ArrayList<ExerciseDetail> = ArrayList()

                val overHeadPress = ExerciseDetail(
                    R.drawable.over_head_press,
                    context.getString(R.string.Over_Head_Press),
                    context.getString(R.string.Over_Head_Press_desc),
                )
                val shoulderPress = ExerciseDetail(
                    R.drawable.shoulder_press,
                    context.getString(R.string.Shoulder_Press),
                    context.getString(R.string.Shoulder_Press_desc)
                )
                val sideLateralRaise = ExerciseDetail(
                    R.drawable.side_lateral,
                    context.getString(R.string.Side_Latera_Raise),
                    context.getString(R.string.Side_Latera_Raise_desc),
                )
                exercises.add(overHeadPress)
                exercises.add(shoulderPress)
                exercises.add(sideLateralRaise)
                exerciseDetailRv.apply {
                    layoutManager = LinearLayoutManager(context)
                    exerciseDetailsAdapter = ExercisesDetailsAdapter(context)
                    exerciseDetailsAdapter.setExerciseDetailList(exercises, position)
                    adapter = exerciseDetailsAdapter

                }
            } else if (position == 1) {
                val exercises: ArrayList<ExerciseDetail> = ArrayList()

                val bentOverBallRoll = ExerciseDetail(
                    R.drawable.bentoverbarbellrow,
                    context.getString(R.string.Bent_),
                    context.getString(R.string.Bent_Desc),
                )
                val latPullDown = ExerciseDetail(
                    R.drawable.latpulldown,
                    context.getString(R.string.Lat_Pull),
                    context.getString(R.string.Lat_Pull_desc),
                )
                val oneArmDummb = ExerciseDetail(
                    R.drawable.onearmdumbell,
                    context.getString(R.string.One_Arm),
                    context.getString(R.string.One_Arm_desc),
                )

                exercises.add(bentOverBallRoll)
                exercises.add(latPullDown)
                exercises.add(oneArmDummb)

                exerciseDetailRv.apply {
                    layoutManager = LinearLayoutManager(context)
                    exerciseDetailsAdapter = ExercisesDetailsAdapter(context)
                    exerciseDetailsAdapter.setExerciseDetailList(exercises, position)
                    adapter = exerciseDetailsAdapter

                }
            }

            if (position == 2) {
                val exercises: ArrayList<ExerciseDetail> = ArrayList()

                val concetrationCurl = ExerciseDetail(
                    R.drawable.concentrationcurl,
                    context.getString(R.string.Concetration),
                    context.getString(R.string.Concetration_Desc)
                )
                val EzbarReverseCurl = ExerciseDetail(
                    R.drawable.ezbar_reverse_curl,
                    context.getString(R.string.Ezbar),
                    context.getString(R.string.Ezbar_Desc),
                )
                val hammerCurl = ExerciseDetail(
                    R.drawable.hammer_curl,
                    context.getString(R.string.Hammer),
                    context.getString(R.string.Hammer_desc)

                )


                exercises.add(concetrationCurl)
                exercises.add(EzbarReverseCurl)
                exercises.add(hammerCurl)

                exerciseDetailRv.apply {
                    layoutManager = LinearLayoutManager(context)
                    exerciseDetailsAdapter = ExercisesDetailsAdapter(context)
                    exerciseDetailsAdapter.setExerciseDetailList(exercises, position)
                    adapter = exerciseDetailsAdapter

                }
            }

            if (position == 3) {
                val exercises: ArrayList<ExerciseDetail> = ArrayList()

                val barballSquat = ExerciseDetail(
                    R.drawable.barbellsquat,
                    context.getString(R.string.Barbell),
                    context.getString(R.string.Barbell_desc),
                )
                val legExtension = ExerciseDetail(
                    R.drawable.legextension,
                    context.getString(R.string.Leg_Extension),
                    context.getString(R.string.Leg_Extension_Desc),
                )

                val legPress = ExerciseDetail(
                    R.drawable.leg_press,
                    context.getString(R.string.Leg_Press_),
                    context.getString(R.string.Leg_Press_desc),
                )



                exercises.add(barballSquat)
                exercises.add(legExtension)
                exercises.add(legPress)

                exerciseDetailRv.apply {
                    layoutManager = LinearLayoutManager(context)
                    exerciseDetailsAdapter = ExercisesDetailsAdapter(context)
                    exerciseDetailsAdapter.setExerciseDetailList(exercises, position)
                    adapter = exerciseDetailsAdapter

                }
            }

            if (position == 4) {
                val exercises: ArrayList<ExerciseDetail> = ArrayList()

                val pshDown = ExerciseDetail(
                    R.drawable.push_down,
                    context.getString(R.string.Push_),
                    context.getString(R.string.Push_desc),
                )
                val skullCrusher = ExerciseDetail(
                    R.drawable.skullcrusher,
                    context.getString(R.string.Skull_Desc),
                )

                val tricepsDips = ExerciseDetail(
                    R.drawable.tricepdips,
                    context.getString(R.string.Triceps_Dps_Desc),
                )


                exercises.add(pshDown)
                exercises.add(skullCrusher)
                exercises.add(tricepsDips)

                exerciseDetailRv.apply {
                    layoutManager = LinearLayoutManager(context)
                    exerciseDetailsAdapter = ExercisesDetailsAdapter(context)
                    exerciseDetailsAdapter.setExerciseDetailList(exercises, position)
                    adapter = exerciseDetailsAdapter

                }
            }


            if (position == 5) {
                val exercises: ArrayList<ExerciseDetail> = ArrayList()

                val benchPress = ExerciseDetail(
                    R.drawable.bench_press,
                    context.getString(R.string.Bench_Press),
                    context.getString(R.string.Bench_Press_Desc),

                )
                val cableCrossOver = ExerciseDetail(
                    R.drawable.cable_cross_over,
                    context.getString(R.string.Cable),
                    context.getString(R.string.Cable_Desc),
                )

                val dumbleFly = ExerciseDetail(
                    R.drawable.dumbell_fly,
                    context.getString(R.string.umble_Fly),
                    context.getString(R.string.umble_Fl_desc),
                )


                exercises.add(benchPress)
                exercises.add(cableCrossOver)
                exercises.add(dumbleFly)

                exerciseDetailRv.apply {
                    layoutManager = LinearLayoutManager(context)
                    exerciseDetailsAdapter = ExercisesDetailsAdapter(context)
                    exerciseDetailsAdapter.setExerciseDetailList(exercises, position)
                    adapter = exerciseDetailsAdapter

                }
            }
            arrowIndicator.setOnClickListener {

                if (exerciseDetaillayout.visibility == View.GONE) {
                    exerciseDetaillayout.visibility = View.VISIBLE
                } else if (exerciseDetaillayout.visibility == View.VISIBLE)
                    exerciseDetaillayout.visibility = View.GONE
            }


        }

    }

    fun setExerciseList(
        exerciseList: ArrayList<String>,
    ) {
        this.exercisesCategory = exerciseList
    }
}