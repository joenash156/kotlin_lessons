package main

fun main() {
//  val name: String = "Joshua"
//  val age: Int = 24
//
//  // print my detail
//  println("My name is $name, and I am $age years old.")
//
//  // chek if I can drive
//  val status = if(age > 18) "Can drive" else "Cannot drive"
//  println("Status: $status")
//
//  // switch case with "when"
//  val day: Int = 3
//
//  val result = when(day) {
//    1, 3 -> "Monday"
//    2 -> "Tuesday"
//    else -> "None"
//  }
//  println(result)
//
//  // do while loop
//  var i: Int = 0
//  do {
//    println(i)
//    i++
//  } while(i < 5)
//
//  // create an array
//  val fruits = arrayOf("Orange", "Apple", "Banana", "Pear")
//  for(fruit in fruits) {
//    println(fruit)
//  }
//
//  // create ranges of values
//  for(char in 'a'..'x') {
//    println(char)
//  }
//
//  for(i in 0..fruits.size - 1) {
//    println(fruits[i])
//  }
//
//  println("Enter your details")
//  print("First name: ")
//  val fname: String = readln()
//  print("Last name: ")
//  val lname: String = readln()
//  print("Your age: ")
//  val userAge: Int = readln().toInt()
//
//  println(mySelf(fname, lname, userAge))
//
  val per = Person("Isaac", "Adisadel", 28, 5.5)
  per.setStage(8)
  per.display()

  println(per.getAge())

}

// let create our own functions
fun mySelf(fname: String, lname: String, age: Int): String {
  return "My name is $fname $lname. I am $age years old now."
}

class Person(
  private val name: String,
  private val school: String,
  private val age: Int,
  private val height: Double,
  private var stage: Int = 0
) {

  fun setStage(stage: Int) {
    this.stage = stage
  }

  fun getAge(): Int {
    return age
  }

  public fun display() {
    println("Details: Name: $name, Age: $age, School: $school, Height: $height, Stage: $stage")
  }
}
