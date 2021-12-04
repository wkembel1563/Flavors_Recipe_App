package com.example.flavors_prototype

//names of variables for recipe and country, must match firebase database node names
data class Recipe(var Place : String ?= null,
                  var Recipe : String ?= null,
                  var CookTime : String ?= null,
                  var PrepTime : String ?= null,
                  var Instructions : String ?= null)
/* Ingredients will be access directly with their own reference pointer */

// place = countryname
// recipe = dish name