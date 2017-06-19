# DoorGateDemo

Simple example app showing Command/Scan in Kotlin.

# Intentional Crashes
The app is designed to fail fast and will crash instead of entering an invalid state.
The room can either be Open, Occupied, or Locked.
The idea is that if noone is in the room, it is open.
When one person joins, it becomes occupied and when another joins it becomes locked.

Attempting to walk in a locked room crashes the app.
Attempting to walk out of an empty room crashes the app.

# Items that haven't been addressed

Configuration Changes

-This app will not persist state through rotation.

-This will be handled (most likely) by persisting presenters

Data Layer

Learning Markdown or w/e this is


