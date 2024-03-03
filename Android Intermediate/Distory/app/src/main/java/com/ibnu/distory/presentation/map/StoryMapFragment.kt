package com.ibnu.distory.presentation.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.ibnu.distory.R
import com.ibnu.distory.base.BaseFragment
import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.databinding.FragmentStoryMapBinding
import com.ibnu.distory.utils.helper.addMultipleMarker
import com.ibnu.distory.utils.helper.boundsCameraToMarkers
import com.ibnu.distory.utils.helper.convertToLatLng
import com.ibnu.distory.utils.helper.setCustomStyle
import org.koin.android.ext.android.inject

class StoryMapFragment : BaseFragment<FragmentStoryMapBinding>() {

    private val viewModel: StoryMapViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStoryMapBinding {
        return FragmentStoryMapBinding.inflate(inflater, container, false)
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun initUI() {
        binding.toolbarMap.apply {
            title = context.getString(R.string.title_stories_location)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.container_map) as SupportMapFragment?

        mapFragment?.getMapAsync { googleMap ->
            googleMap.setCustomStyle(requireContext())
            viewModel.getRecentStories()
            viewModel.storyResult.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is ApiResponse.Loading -> {}
                    is ApiResponse.Error -> {}
                    is ApiResponse.Success -> {
                        googleMap.addMultipleMarker(response.data)
                        val listLocations = response.data.convertToLatLng()
                        googleMap.boundsCameraToMarkers(listLocations)
                        setButtonViews(googleMap, listLocations)

//                        val infoWindow =
//                            InfoWindowsPenginapan(requireActivity(), requireContext())
//                        googleMap.setInfoWindowAdapter(infoWindow)
                    }
                    else -> {}
                }
            }
        }

    }

    private fun setButtonViews(googleMap: GoogleMap, listLocation: List<LatLng>) {
        binding.btnBounds.setOnClickListener {
            googleMap.boundsCameraToMarkers(listLocation)
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }


}