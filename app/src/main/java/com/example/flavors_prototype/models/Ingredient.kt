package com.example.flavors_prototype.models

/* Ingredient class used to get the name/photo of each ingredient in database */
data class Ingredient(var name : String ?= null, 
                      var quantity : String ?= null, 
                      var url : String ?= null)

