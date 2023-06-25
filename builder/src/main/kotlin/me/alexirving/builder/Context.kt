package me.alexirving.builder

abstract class Context<B : Base<B, C>, C : Context<B, C>>(build: (C.() -> Unit))