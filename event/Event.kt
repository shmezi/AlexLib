package me.alexirving.lib.event

interface Event
interface CancellableEvent : Event {
    var cancelled: Boolean
}