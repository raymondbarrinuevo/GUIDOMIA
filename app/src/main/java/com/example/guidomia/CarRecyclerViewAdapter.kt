package com.example.guidomia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guidomia.databinding.ItemCarBinding
import com.example.guidomia.db.Car
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class CarRecyclerViewAdapter(var context: Context) :
    RecyclerView.Adapter<MyViewHolder>() {

    private val cars = ArrayList<Car>()
    var selectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCarBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_car, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(cars[position], context)

        if (selectedPosition == position) {
            holder.binding.viewProsCons.visibility = View.VISIBLE
        } else {
            holder.binding.viewProsCons.visibility = View.GONE
        }

        holder.binding.itemView.setOnClickListener {
            if (selectedPosition != position) {
                notifyItemChanged(selectedPosition)
                notifyItemChanged(position)
                selectedPosition = position
            }
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    fun setList(cars: List<Car>) {
        this.cars.clear()
        this.cars.addAll(cars)
    }

}

class MyViewHolder(val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var prosAdapter: ProsAndConsRecyclerViewAdapter
    private lateinit var consAdapter: ProsAndConsRecyclerViewAdapter


    fun bind(car: Car, context: Context) {
        binding.tvName.text = car.model
        binding.tvPrice.text = "Price: ${format(car.customerPrice.toLong())}"
        binding.rating.rating = car.rating.toFloat()

        when (car.make) {
            "Land Rover" -> {
                binding.ivCar.setImageResource(R.drawable.range_rover)
            }
            "Alpine" -> {
                binding.ivCar.setImageResource(R.drawable.alpine_roadster)
            }
            "Mercedes Benz" -> {
                binding.ivCar.setImageResource(R.drawable.mercedez_benz_glc)
            }
            "BMW" -> {
                binding.ivCar.setImageResource(R.drawable.bmw)
            }
        }

        initProsRecyclerView(car.prosList, context)
        initConsRecyclerView(car.consList, context)
    }

    private fun format(count: Long): String {
        val array = arrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val value = floor(log10(count.toDouble())).toInt()
        val base = value / 3
        if (value >= 3 && base < array.size) {
            return DecimalFormat("#0").format(
                count / 10.0.pow((base * 3).toDouble())
            ) + array[base]
        } else {
            return DecimalFormat("#,##0").format(count)
        }
    }

    private fun initProsRecyclerView(prosList: List<String>, context: Context) {
        binding.prosRecyclerView.layoutManager = LinearLayoutManager(context)
        prosAdapter = ProsAndConsRecyclerViewAdapter()
        binding.prosRecyclerView.adapter = prosAdapter
        displayProsDataList(prosList)
    }

    private fun displayProsDataList(prosList: List<String>) {
        prosAdapter.setList(prosList)
        prosAdapter.notifyDataSetChanged()
    }

    private fun initConsRecyclerView(consList: List<String>, context: Context) {
        binding.consRecyclerView.layoutManager = LinearLayoutManager(context)
        consAdapter = ProsAndConsRecyclerViewAdapter()
        binding.consRecyclerView.adapter = consAdapter
        displayConsDataList(consList)
    }

    private fun displayConsDataList(consList: List<String>) {
        consAdapter.setList(consList)
        consAdapter.notifyDataSetChanged()
    }
}