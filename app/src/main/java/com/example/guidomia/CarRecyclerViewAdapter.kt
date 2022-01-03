package com.example.guidomia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guidomia.databinding.ItemCarBinding
import com.example.guidomia.db.Car
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class CarRecyclerViewAdapter(private val clickListener: (Car) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {

    private val cars = ArrayList<Car>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCarBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_car, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(cars[position], clickListener)
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

    fun bind(car: Car, clickListener: (Car) -> Unit) {
        binding.tvName.text = car.model
        binding.tvPrice.text = "Price: ${countViews(car.customerPrice.toLong())}"
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


        binding.itemView.setOnClickListener {
            clickListener(car)
        }


    }

    fun countViews(count: Long): String {
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
}