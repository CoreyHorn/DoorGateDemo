package com.example.coreyhorn.doorgatedemo.models

sealed class RoomCommand {
    class WalkIn: RoomCommand()
    class WalkOut: RoomCommand()
}