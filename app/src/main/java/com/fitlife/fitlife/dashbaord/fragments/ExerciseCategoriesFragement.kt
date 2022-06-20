package com.fitlife.fitlife.dashbaord.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitlife.fitlife.R
import com.fitlife.fitlife.dashbaord.DashbaordViewModel
import com.fitlife.fitlife.dashbaord.adapters.ExerciseCategoryAdapter
import com.fitlife.fitlife.dashbaord.adapters.ExercisesDetailsAdapter
import com.fitlife.fitlife.databinding.FragmentExerciseCategoriesBinding
import com.fitlife.fitlife.model.ExerciseDetail

class ExerciseCategories : Fragment() {
    val exercisesCatogories: ArrayList<String> = ArrayList()
    private lateinit var exerciseCategoriesFragment: FragmentExerciseCategoriesBinding
    private lateinit var exerciseCategory: ExerciseCategoryAdapter
    private val viewModel: DashbaordViewModel by activityViewModels()
    private lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exerciseCategoriesFragment =
            FragmentExerciseCategoriesBinding.inflate(inflater, container, false)
        exerciseCategoriesFragment.viewModel = viewModel
        mContext = requireActivity()
        initAdapterAndRecyclerView()
        return exerciseCategoriesFragment.root
    }
    private fun initAdapterAndRecyclerView() {
        exercisesCatogories.clear()
        exercisesCatogories.add(getString(R.string.Shoulder))
        exercisesCatogories.add(getString(R.string.Back))
        exercisesCatogories.add(getString(R.string.Biceps))
        exercisesCatogories.add(getString(R.string.Legs))
        exercisesCatogories.add(getString(R.string.Triceps))
        exercisesCatogories.add(getString(R.string.Chest))
        exerciseCategoriesFragment.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(mContext)
            exerciseCategory = ExerciseCategoryAdapter(mContext)
            exerciseCategory.setExerciseList(exercisesCatogories)
            adapter = exerciseCategory

        }
    }

}