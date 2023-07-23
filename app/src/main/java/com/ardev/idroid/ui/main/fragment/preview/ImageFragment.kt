package com.ardev.idroid.ui.main.fragment.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ardev.component.view.TouchImageView
import androidx.fragment.app.Fragment
import com.ardev.common.base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ardev.idroid.R
import java.io.File

class ImageFragment : BaseFragment<FragmentImageBinding>(FragmentImageBinding::inflate) {
	private val path: String
    	get() =  arguments!!.getString("path", "")
    private val file = File(path)
    companion object {
        fun newInstance(path: String): ImageFragment {
        	val args: Bundle = Bundle()
            args.putString("path", path)
            val fragment = ImageFragment()
            fragment.arguments = args
            return fragment
        }
    }
    

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding {
            if (file.isResourceXMLFileInDir("Drawable")) {
                
                try {
                    val vectorDrawable = VectorMasterDrawable.fromXMLFile(file)
                    Glide.with(requireContext())
                        .applyDefaultRequestOptions(RequestOptions().override(100).encodeQuality(80))
                        .load(vectorDrawable)
                        .into(image)
                } catch (e: Exception) {
                }
            } else {
                
                Glide.with(requireContext())
                    .applyDefaultRequestOptions(RequestOptions().override(100).encodeQuality(80))
                    .load(file)
                    .into(image)
            }
        }
    }
}
