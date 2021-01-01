package com.example.pokemonbookapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

private const val REQ_IMAGE_CODE = 0
class PokemonBookArFragment: ArFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
    }

    override fun getSessionConfiguration(session: Session?): Config {
        val config  = super.getSessionConfiguration(session)
        config.focusMode = Config.FocusMode.AUTO
        return config
    }

    private fun chooseImage(){
        Intent(Intent.ACTION_GET_CONTENT).run {
            type = "image/*"
            startActivityForResult(this, REQ_IMAGE_CODE)
        }
    }

}