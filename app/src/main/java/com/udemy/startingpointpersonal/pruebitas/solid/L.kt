package com.udemy.startingpointpersonal.pruebitas.solid

/**
 * Liskov Substitution Principle
 */
open class Creature {

    fun move(){ println("Moving") }
    //fun fly(){ println("Flying") }
    fun swim(){ println("Swimming") }
}

class Human: Creature(){

}

class Bird: Creature(){
    fun fly(){ println("Flying") }
}

class Fish: Creature(){

}

fun main(){
    val human = Human()
    human.move()
    //human.fly()

    val fish = Fish()
    fish.move()
    //fish.fly()

    //se puede cambiar Creature() por el tipo Human o Bird. "Se puede cambiar una clase por una subclase y no debe tronar la aplicación"
    val creature = Creature()
    creature.move()
    creature.swim()
}