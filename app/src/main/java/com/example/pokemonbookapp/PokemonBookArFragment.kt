package com.example.pokemonbookapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

private const val REQ_IMAGE_CODE = 0
private const val USE_DATABASE = true
class PokemonBookArFragment: ArFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(!USE_DATABASE) {
            chooseImage()
        }
    }

    override fun getSessionConfiguration(session: Session): Config {
        val config  = super.getSessionConfiguration(session)
        config.focusMode = Config.FocusMode.AUTO

        if(USE_DATABASE){
            config.augmentedImageDatabase = createAugmentedImagesDb(session)
        }
        return config
    }

    private fun chooseImage(){
        Intent(Intent.ACTION_GET_CONTENT).run {
            type = "image/*"
            startActivityForResult(this, REQ_IMAGE_CODE)
        }
    }

    private fun createAugmentedImagesDb(session : Session) : AugmentedImageDatabase?{
       return try{
           //Images detecting may not work. Will have to take photo and create .imgdb db file using the steps from
           //the course https://www.udemy.com/course/arcore-and-sceneform-masterclass-for-android-q/learn/lecture/18849818#overview
           val inputStream = resources.openRawResource(R.raw.ro3)
           AugmentedImageDatabase.deserialize(session,inputStream)
        }
        catch (ex: Exception){
            Log.e("ImageArFragment", "IOException while loading augmented image from storage", ex)
            null
        }
    }
}