package com.zesbe.chat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zesbe.chat.BuildConfig
import com.zesbe.chat.presentation.ui.chat.ChatScreen

/**
 * Navigation Graph
 *
 * Defines all screens and navigation routes.
 */
@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "chat_list"
    ) {
        // Chat List Screen (placeholder for now)
        composable("chat_list") {
            // ChatListScreen placeholder
            // For now, directly navigate to a new chat
            navController.navigate("chat/new_chat")
        }

        // Chat Screen
        composable(
            route = "chat/{chatId}",
            arguments = listOf(
                navArgument("chatId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: "new_chat"
            val apiKey = BuildConfig.GLM_API_KEY

            ChatScreen(
                chatId = chatId,
                apiKey = apiKey,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}
