package me.alexirving.builder

abstract class Base<B : Base<B, C>, C : Context<B, C>>(val base: B) {
    abstract fun builder(): C

}