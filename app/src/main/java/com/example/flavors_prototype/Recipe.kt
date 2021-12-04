package com.example.flavors_prototype

//names of variables for recipe and country, must match firebase database node names
data class Recipe(var Place : String ?= null,var Recipe : String ?= null,var CookTime : String ?= null,var PrepTime : String ?= null,var Instructions : String ?= null,var Ingredient1 : String ?= null,var Ingredient2 : String ?= null,var Ingredient3 : String ?= null,var Ingredient4 : String ?= null,var Ingredient5 : String ?= null,var Ingredient6 : String ?= null,var Ingredient7 : String ?= null,var Ingredient8 : String ?= null,var Ingredient9 : String ?= null, var image : String ?= null)
