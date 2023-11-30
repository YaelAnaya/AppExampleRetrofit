package yao.ic.appexample.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.util.Locale

sealed class NavItem(
    private val baseRoute: String,
    private val navArgs: List<NavArg> = emptyList(),
    val icon: ImageVector? = null
){
    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(argKeys).joinToString("/")
    }
    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    val title: String = if (navArgs.isEmpty()) baseRoute.capitalize(Locale.ENGLISH) else " "

    fun createRoute(args: String) = route.replaceFirst("{${NavArg.ShowID.key}}", args)

    object Shows: NavItem(baseRoute = "shows", icon = Icons.Outlined.LocalMovies)
    object ShowDetail: NavItem(baseRoute = "show_detail", listOf(NavArg.ShowID))
    object Favorites: NavItem(baseRoute = "favorites", icon = Icons.Rounded.Favorite)

}

enum class NavArg(val key: String, val navType: NavType<*>){
     ShowID("show_id", NavType.IntType),
}

val BOTTOM_NAV_ITEMS = listOf(
    NavItem.Shows,
    NavItem.Favorites
)