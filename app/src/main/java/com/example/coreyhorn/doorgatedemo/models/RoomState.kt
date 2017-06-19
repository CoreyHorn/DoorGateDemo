package com.example.coreyhorn.doorgatedemo.models

sealed class RoomState {
    class Open: RoomState()
    class Occupied: RoomState()
    class Locked: RoomState()
}