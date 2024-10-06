package com.marceloacuna.myappsemana9

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marceloacuna.myappsemana9.Pages.Home
import com.marceloacuna.myappsemana9.Pages.Login
import com.marceloacuna.myappsemana9.Pages.MyApp
import com.marceloacuna.myappsemana9.Pages.Registrar



@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login, builder = {

        composable(Routes.Login,){
            Login(modifier,navController, authViewModel)
        }
        composable(Routes.Register,){
            Registrar(modifier,navController, authViewModel)
        }

        composable(Routes.home,){
            Home(modifier,navController, authViewModel)
        }

        composable(Routes.MyApp,){
            MyApp(modifier,navController,authViewModel)
        }

    })
}
