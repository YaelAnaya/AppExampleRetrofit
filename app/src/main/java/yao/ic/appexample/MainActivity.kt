package yao.ic.appexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import yao.ic.appexample.ui.TVMazeApp
import yao.ic.appexample.ui.screens.show_list.TVMazeViewModel
import yao.ic.appexample.ui.theme.AppExampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: TVMazeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = hiltViewModel()
            AppExampleTheme {
                TVMazeApp(
                    modifier = Modifier.fillMaxSize(),
                    tvMazeViewModel = viewModel
                )
            }
        }
    }

}



