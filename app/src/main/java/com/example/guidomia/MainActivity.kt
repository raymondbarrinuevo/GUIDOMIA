package com.example.guidomia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.guidomia.databinding.ActivityMainBinding
import com.example.guidomia.db.Car
import com.example.guidomia.db.CarRepository
import com.example.guidomia.db.TacomaDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tacomaViewModel: TacomaViewModel
    private lateinit var adapter: CarRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = TacomaDatabase.getInstance(application).carDao
        val repository = CarRepository(dao)
        val factory = TacomaViewModelFactory(repository)
        tacomaViewModel = ViewModelProvider(this, factory).get(TacomaViewModel::class.java)
        binding.myViewModel = tacomaViewModel
        binding.lifecycleOwner = this

        tacomaViewModel.readAndSaveCarsData(applicationContext)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.carsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CarRecyclerViewAdapter(this)
        binding.carsRecyclerView.adapter = adapter
        (binding.carsRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        displayCarDataList()

    }

    private fun displayCarDataList() {
        tacomaViewModel.getSavedCarsData().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
}