package com.example.pokemonbookapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.AugmentedImage
import com.google.ar.core.Frame
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    private val augmentedImageMap = HashMap<AugmentedImage, AugmentedImageNode>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = image_frag as ArFragment
    }

    private fun updateTrackedImages(frame: Frame) {
        val imageList = frame.getUpdatedTrackables(AugmentedImage::class.java)

        for (image in imageList) {
            if (image.trackingState == TrackingState.TRACKING) {
                if (!augmentedImageMap.containsKey(image)) {
                    AugmentedImageNode(this).apply {
                        setAugmentedImage(image)
                        augmentedImageMap[image] = this
                        arFragment.arSceneView.scene.addChild(this)

                    }
                } else if (image.trackingState == TrackingState.STOPPED) {
                    augmentedImageMap.remove(image)
                }
            }
        }

    }
}