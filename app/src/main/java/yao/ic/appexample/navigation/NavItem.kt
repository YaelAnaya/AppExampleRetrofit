package yao.ic.appexample.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.util.Locale

sealed class NavItem(
    val baseRoute: String,
    private val navArgs: List<NavArg> = emptyList()
){
    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(argKeys).joinToString("/")
    }
    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    fun createRoute(vararg args: String) = route.format(*args)

    object Amphibians: NavItem("amphibians")
    object AmphibianDetail: NavItem("amphibian_detail", listOf(NavArg.AmphibianName))

}

enum class NavArg(val key: String, val navType: NavType<*>){
     AmphibianName("amphibianName", NavType.StringType),
}
