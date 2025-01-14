package com.example.mygame_exercize

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygame_exercize.utilities.AlertManager
import com.example.mygame_exercize.utilities.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class LauncherActivity : AppCompatActivity() {

    private lateinit var startGame_BTN: ExtendedFloatingActionButton
    private lateinit var seeRecordsBTN : ExtendedFloatingActionButton
    private lateinit var speed_Switch: SwitchCompat //slow or fast toggle
    private lateinit var control_Switch: SwitchCompat // buttons or tilt toggle
    private var selectModeSpeed: Long? = Constants.Difficulties.MORE_DELAY
    private var selectModeControl: String? = Constants.PlayModes.BUTTONS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_launcher)
        findViews()
        initView()
        accessUserLocation()



    }
    //check and request location permissions from the user
    private fun accessUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Constants.PremittionLocation.LOCATION_PERMISSION_REQUEST
            )
        }
    }

    private fun initView() {
        //switch of speed
        //make default on both toggles so then thumb/circle will appear on the left and not right
        //speed_Switch.isChecked = false
        //control_Switch.isChecked = false

        speed_Switch.setOnCheckedChangeListener { buttonView, isChecked -> //to listen to changes of the switched and by that decide the game mode
            if(isChecked) {//if its true=>circle is on the right
                selectModeSpeed = Constants.Difficulties.LESS_DELAY
                AlertManager.getInstance().toast("u chose fast option, good luck")
            } else {//the thumb is on the left side
                selectModeSpeed = Constants.Difficulties.MORE_DELAY//LONG!
                AlertManager.getInstance().toast("slow is the safest option")
            }

        }

        control_Switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {//if true than is on tilt by phone motion
                selectModeControl = Constants.PlayModes.TILT
                AlertManager.getInstance().toast("you chose tilt option")
            } else {
                selectModeControl = Constants.PlayModes.BUTTONS
                Log.d("switch control", "initView: entered to the button ")
                AlertManager.getInstance().toast("you chose the buttons option")
            }
        }

        startGame_BTN.setOnClickListener {
            if (selectModeSpeed !=null && selectModeControl != null){//as long as selectedMode isn't null, then change the activity
                changeActivity()
        }
}
        seeRecordsBTN.setOnClickListener {
            recordsChangeActivity()
        }

    }

    private fun recordsChangeActivity() {//function to move from launcher to records activity
        val intent = Intent(this, ScoreAndMapActivity::class.java)//sending to MainActivity specificly
        startActivity(intent)
        finish()
    }

    private fun changeActivity() {
        val intent = Intent(this, MainActivity::class.java)//sending to MainActivity specificly
        val bundle = Bundle()
        bundle.putLong(Constants.BundleKeys.SPEED_MODE_KEY, selectModeSpeed!!)
        bundle.putString(Constants.BundleKeys.CONTROL_MODE_KEY,selectModeControl)
        //sending trough bundle the key and its value select mode to another activity using it

        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun findViews() {

        speed_Switch = findViewById(R.id.switch_BTN)
        control_Switch = findViewById(R.id.switch_BTN2)
        startGame_BTN = findViewById(R.id.BTN_startgame)
        seeRecordsBTN = findViewById(R.id.launcher_move_records2)
    }
}