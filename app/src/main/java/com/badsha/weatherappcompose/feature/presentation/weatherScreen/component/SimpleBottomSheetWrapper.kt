import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.badsha.weatherappcompose.feature.presentation.weatherScreen.WeatherViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleBottomSheetWrapper(content:@Composable () -> Unit,
                             viewModel: WeatherViewModel,
                             bottomSheetContent:@Composable () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    val coroutineScope = rememberCoroutineScope()
    viewModel.bottomSheetScope = coroutineScope
    viewModel.bottomSheetState = sheetState

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { bottomSheetContent() },
        modifier = Modifier.fillMaxSize()
    ) {
        content()
    }
}