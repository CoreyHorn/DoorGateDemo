package com.example.coreyhorn.doorgatedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.coreyhorn.doorgatedemo.models.RoomCommand
import com.example.coreyhorn.doorgatedemo.models.RoomCommand.WalkIn
import com.example.coreyhorn.doorgatedemo.models.RoomCommand.WalkOut
import com.example.coreyhorn.doorgatedemo.models.RoomState
import com.example.coreyhorn.doorgatedemo.models.RoomState.*
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind views to their commands
        val walkIns: Observable<RoomCommand> = RxView.clicks(btnWalkIn)
                .map { WalkIn() }

        val walkOuts: Observable<RoomCommand> = RxView.clicks(btnWalkOut)
                .map { WalkOut() }

        val commands: Observable<RoomCommand> = walkIns.mergeWith(walkOuts)

        // Create the RoomPresenter and subscribe to the updates.
        // This is for demo purposes only, we will need to decide on a plan for instantiating and persisting presenters.
        val presenter = RoomPresenter(commands)

        presenter.roomState
                // We won't need this if we use drivers
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            renderState(it)
        }
    }

    fun renderState(roomState: RoomState)
    {
        when (roomState) {
            is Open -> txtRoomState.text = "RoomState: Open"
            is Occupied -> txtRoomState.text = "RoomState: Occupied"
            is Locked -> txtRoomState.text = "RoomState: Locked"
        }
    }
}
