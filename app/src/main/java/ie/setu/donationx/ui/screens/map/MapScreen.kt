package ie.setu.donationx.ui.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val irelandCenter = LatLng(53.4129, -8.2439)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(irelandCenter, 6f)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
    }
}
