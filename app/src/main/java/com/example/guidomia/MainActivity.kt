package com.example.guidomia

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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

        initRecyclerView()

        movieViewModel.movieList.observe(this, {
            adapter.setList(it)
        })

        movieViewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        movieViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        movieViewModel.getAllMovies()
    }


    private fun initRecyclerView() {
        binding.carsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CarRecyclerViewAdapter(this)
        binding.carsRecyclerView.adapter = adapter
        (binding.carsRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
    }

    private fun displayCarDataList() {
        tacomaViewModel.getSavedCarsData().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun initSpinnerMake() {
        if (binding.spMake.selectedItem != null) {
            if (binding.spModel.selectedItem.toString() != "Any model") {
                tacomaViewModel.getAllMake(binding.spModel.selectedItem.toString())
                    .observe(this@MainActivity, Observer {
                        val adapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_spinner_item, it
                        )
                        binding.spMake.adapter = adapter
                    })
            } else {
                tacomaViewModel.getAllMake().observe(this, Observer {
                    val allMake: ArrayList<String> = ArrayList()
                    allMake.addAll(it)
                    allMake.add(0, "Any make")

                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item, allMake
                    )
                    binding.spMake.adapter = adapter
                })
            }
        }
    }

    private fun makeSpinnerClickListener() {
        tacomaViewModel.getAllMake().observe(this, Observer {
            val allMake: ArrayList<String> = ArrayList()
            allMake.addAll(it)
            allMake.add(0, "Any make")

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, allMake
            )
            binding.spMake.adapter = adapter
        })

        binding.spMake.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (binding.spMake.selectedItem.toString() != "Any make") {
                    adapter.filterMake(binding.spMake.selectedItem.toString())
                } else {
                    displayCarDataList()
                }
                initSpinnerModel()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                displayCarDataList()
            }
        }
    }

    private fun initSpinnerModel() {
        if (binding.spMake.selectedItem != null) {
            if (binding.spMake.selectedItem.toString() != "Any make") {
                tacomaViewModel.getAllModel(binding.spMake.selectedItem.toString())
                    .observe(this@MainActivity, Observer {
                        val adapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_spinner_item, it
                        )
                        binding.spModel.adapter = adapter
                    })
            } else {
                tacomaViewModel.getAllModel().observe(this, Observer {
                    val allModel: ArrayList<String> = ArrayList()
                    allModel.addAll(it)
                    allModel.add(0, "Any model")
                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item, allModel
                    )
                    binding.spModel.adapter = adapter
                })
            }
        }
    }

    private fun modelSpinnerClickListener() {
        binding.spModel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (binding.spModel.selectedItem.toString() != "Any model") {
                    adapter.filterModel(binding.spModel.selectedItem.toString())
                } else {
                    displayCarDataList()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                displayCarDataList()
            }
        }
    }
}