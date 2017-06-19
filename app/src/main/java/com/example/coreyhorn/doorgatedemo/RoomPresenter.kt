package com.example.coreyhorn.doorgatedemo

import android.util.Log
import com.example.coreyhorn.doorgatedemo.models.RoomCommand
import com.example.coreyhorn.doorgatedemo.models.RoomCommand.WalkIn
import com.example.coreyhorn.doorgatedemo.models.RoomCommand.WalkOut
import com.example.coreyhorn.doorgatedemo.models.RoomState
import com.example.coreyhorn.doorgatedemo.models.RoomState.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RoomPresenter(commands: Observable<RoomCommand>) {

    // When we decide how we want to persist presenters, this subject may become a ReplaySubject or similar
    // We will also want to force this to emit on the main thread. Either by porting Drivers over to kotlin or using operators
    val roomState: PublishSubject<RoomState> = PublishSubject.create()

    // Init ensures this code is called no matter the number of additional constructors. I'm 90% on that one :)
    init {
        // Here the presenter passes the previous state and current command to the accumulator
        // This is the state the UI will subscribe to and update itself accordingly
        commands.scan(Open(), ::roomAccumulator)
                // Using startWith here ensures the initial view state is delivered immediately.
                .startWith(Open())
                .subscribe(roomState)
    }
}

// Have to define this function outside of RoomPresenter for function references to work. Should look into that a bit
// Could alternatively assign the function to a variable and use that variable in the scan
fun roomAccumulator(previousState: RoomState, command: RoomCommand): RoomState {

    // Switch statements with multiple values looks pretty appealing here :troll:
    // Want to find a better way of doing this. Even if we just stick it in a function somewhere
    when (previousState) {
        is Open -> {
            Log.d("stuff", "previous state is open")
            when (command) {
                is WalkIn -> return Occupied()
                is WalkOut -> throw InvalidStateException()
            }
        }
        is Occupied -> {
            when (command) {
                is WalkIn -> return Locked()
                is WalkOut -> return Open()
            }
        }
        is Locked -> {
            when (command) {
                is WalkIn -> throw InvalidStateException()
                is WalkOut -> return Occupied()
            }
        }
    }
}