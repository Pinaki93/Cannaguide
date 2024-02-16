package dev.pinaki.cannaguide.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.pinaki.cannaguide.data.store.IntakeEntry
import dev.pinaki.cannaguide.feature.intakeentry.EditIntakeEntryViewModel
import dev.pinaki.cannaguide.feature.intakeentry.IntakeEntryListScreen
import dev.pinaki.cannaguide.feature.intakeentry.IntakeEntryViewModel
import dev.pinaki.cannaguide.feature.intakeentry.AddEditEntryScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        "intake"
    ) {
        navigation(
            startDestination = "home",
            route = "intake"
        ) {
            composable(route = "home") {
                val vm = viewModel<IntakeEntryViewModel>(initializer = {
                    IntakeEntryViewModel()
                })
                val state = vm.entries.collectAsState()
                IntakeEntryListScreen(
                    state = state.value,
                    addEntry = {
                        navController.navigate("add")
                    },
                    editEntry = {
                        navController.navigate("edit/${it.id}")
                    }
                )
            }
            composable(route = "add") {
                val vm = viewModel<IntakeEntryViewModel>(initializer = {
                    IntakeEntryViewModel()
                })
                AddEditEntryScreen(
                    addEntry = vm::addEntry,
                    goBack = navController::popBackStack
                )
            }

            composable(
                route = "edit/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id: Int? = backStackEntry.arguments?.getInt("id")
                if (id == null) {
                    navController.popBackStack()
                    return@composable
                }

                val vm = viewModel<EditIntakeEntryViewModel>(
                    initializer = {
                        EditIntakeEntryViewModel()
                    })

                var entry by remember {
                    mutableStateOf<IntakeEntry?>(null)
                }

                LaunchedEffect(id) {
                    entry = vm.getEntry(id)
                }

                AddEditEntryScreen(
                    isEditMode = true,
                    entry = entry,
                    addEntry = vm::editEntry,
                    goBack = navController::popBackStack,
                    deleteEntry = vm::deleteEntry
                )
            }
        }

    }
}