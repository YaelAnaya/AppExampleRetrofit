package yao.ic.appexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import yao.ic.appexample.ui.AmphibiansApp
import yao.ic.appexample.ui.screens.amphibians.AmphibiansViewModel
import yao.ic.appexample.ui.theme.AppExampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppExampleTheme {
                AmphibiansApp(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }

}



