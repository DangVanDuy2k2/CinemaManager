package com.duydv.vn.cinemamanager.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.duydv.vn.cinemamanager.R
import com.duydv.vn.cinemamanager.constant.Constant
import com.duydv.vn.cinemamanager.databinding.ItemFoodDrinkBinding
import com.duydv.vn.cinemamanager.model.Food
import com.duydv.vn.cinemamanager.util.GlideUtils
import com.duydv.vn.cinemamanager.util.StringUtil

class FoodDrinkAdapter(
    private val mListFood: List<Food>?,
    private val iManagerFoodDrinkListener: IManagerFoodDrinkListener
) : RecyclerView.Adapter<FoodDrinkAdapter.FoodDrinkViewHolder>() {
    interface IManagerFoodDrinkListener {
        fun selectCount(food: Food, count: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodDrinkViewHolder {
        val itemFoodDrinkBinding =
            ItemFoodDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodDrinkViewHolder(itemFoodDrinkBinding)
    }

    override fun onBindViewHolder(holder: FoodDrinkViewHolder, position: Int) {
        val food = mListFood!![position]
        GlideUtils.loadUrl(food.image, holder.mItemFoodDrinkBinding.imgFood)
        holder.mItemFoodDrinkBinding.tvNameFood.text = food.name
        val strPrice = food.price.toString() + Constant.UNIT_CURRENCY
        holder.mItemFoodDrinkBinding.tvPriceFood.text = strPrice
        holder.mItemFoodDrinkBinding.tvStock.text = food.quantity.toString()
        holder.mItemFoodDrinkBinding.edtCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (!StringUtil.isEmpty(editable.toString().trim { it <= ' ' })) {
                    val count = editable.toString().toInt()
                    if (count > food.quantity) {
                        val context = holder.mItemFoodDrinkBinding.edtCount.context
                        Toast.makeText(
                            context,
                            context.getString(R.string.msg_count_invalid),
                            Toast.LENGTH_SHORT
                        ).show()
                        holder.mItemFoodDrinkBinding.edtCount.setText("")
                    } else {
                        iManagerFoodDrinkListener.selectCount(food, count)
                    }
                } else {
                    iManagerFoodDrinkListener.selectCount(food, 0)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return mListFood?.size ?: 0
    }

    class FoodDrinkViewHolder(val mItemFoodDrinkBinding: ItemFoodDrinkBinding) :
        RecyclerView.ViewHolder(mItemFoodDrinkBinding.root)
}