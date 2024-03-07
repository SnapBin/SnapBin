package com.example.snapbin.view.map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null
) {
    val mapViewState = rememberMapViewWithLifecycle()
    val context = LocalContext.current
    val nightMode = isSystemInDarkTheme()
    var shouldUpdate = true
    AndroidView(
        { mapViewState },
    ) { mapView ->
        run {
            mapView.clipToOutline = true
            val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
            locationOverlay.enableMyLocation()
            mapView.overlays.add(locationOverlay)
            if(nightMode) mapView.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
            onLoad?.invoke(mapView)
        }
    }
}
