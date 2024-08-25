package com.duydv.vn.cinemamanager.model

import java.io.Serializable

class Food : Serializable {
    var id: Long = 0
    var name: String? = null
    var image: String? = null
    var price = 0
    var quantity = 0

    constructor() {}
    constructor(id: Long, name: String?, image: String?, price: Int, quantity: Int) {
        this.id = id
        this.name = name
        this.image = image
        this.price = price
        this.quantity = quantity
    }
}