package com.emines_employee.screen.dashboard.orders

import com.emines_employee.model.Order

class OrderViewModel {

    companion object {

        fun getOrderList(): MutableList<Order> {
            val list = mutableListOf<Order>()
            list.add(
                Order(
                    1,
                    "Sell (Corporate)AC, Washing Machine",
                    "12 Kg",
                    "Rs 12/Kg",
                    23212,
                    "23Sep2023",
                    "Confirmed"
                )
            )
            list.add(
                Order(
                    2,
                    "Sell (Corporate)AC, Referegerator",
                    "12 Kg",
                    "Rs 120/Kg",
                    1622,
                    "24Sep2023",
                    "Placed"
                )
            )
            list.add(
                Order(
                    3,
                    "Sell (Corporate)AC, Microwavse Machine",
                    "12 Kg",
                    "Rs 112/Kg",
                    77,
                    "25Sep2023",
                    "Pending"
                )
            )
            list.add(
                Order(
                    4,
                    "Sell (Corporate)AC, Microwavse Machine",
                    "12 Kg",
                    "Rs 112/Kg",
                    73,
                    "22Sep2023",
                    "Pending"
                )
            )
            list.add(
                Order(
                    5,
                    "Sell (Corporate)AC, Computer Machine",
                    "12 Kg",
                    "Rs 112/Kg",
                    70,
                    "21Sep2023",
                    "Picked"
                )
            )
            list.add(
                Order(
                    6,
                    "Sell (Corporate)AC, Ac Machine",
                    "12 Kg",
                    "Rs 112/Kg",
                    89,
                    "27Sep2023",
                    "Picked"
                )
            )
            list.add(
                Order(
                    7,
                    "Sell (Corporate)AC, Ac Machine",
                    "12 Kg",
                    "Rs 112/Kg",
                    89,
                    "27Sep2023",
                    "Completed"
                )
            )
            return list
        }

    }
}