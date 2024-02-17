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
import dev.pinaki.cannaguide.data.store.MoodEntry
import dev.pinaki.cannaguide.feature.intakeentry.EditIntakeEntryViewModel
import dev.pinaki.cannaguide.feature.home.HomeScreen
import dev.pinaki.cannaguide.feature.home.HomeViewModel
import dev.pinaki.cannaguide.feature.intakeentry.IntakeEntryViewModel
import dev.pinaki.cannaguide.feature.intakeentry.AddEditEntryScreen
import dev.pinaki.cannaguide.feature.moodtracker.AddEditMoodScreen
import dev.pinaki.cannaguide.feature.moodtracker.AddEditMoodViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        "home"
    ) {
        composable(route = "home") {
            val vm = viewModel<HomeViewModel>(initializer = {
                HomeViewModel()
            })
            val state = vm.state.collectAsState()
            HomeScreen(
                state = state.value,
                addEntry = {
                    navController.navigate("intake/add")
                },
                addMood = {
                    navController.navigate("mood/add")
                },
                editEntry = {
                    when (it) {
                        is IntakeEntry -> {
                            navController.navigate("intake/edit/${it.id}")
                        }

                        is MoodEntry -> {
                            navController.navigate("mood/edit/${it.id}")
                        }
                    }

                },
                loadMore = vm::loadMore
            )
        }

        composable(route = "intake/add") {
            val vm = viewModel<IntakeEntryViewModel>(initializer = {
                IntakeEntryViewModel()
            })
            AddEditEntryScreen(
                addEntry = vm::addEntry,
                goBack = navController::popBackStack
            )
        }

        composable(
            route = "intake/edit/{id}",
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

        composable(route = "mood/add") {
            val vm = viewModel<AddEditMoodViewModel>(initializer = {
                AddEditMoodViewModel()
            })
            AddEditMoodScreen(
                addEntry = vm::addMoodEntry,
                goBack = navController::popBackStack,
                emotions = { vm.emotions }
            )
        }

        composable(
            route = "mood/edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id: Int? = backStackEntry.arguments?.getInt("id")
            if (id == null) {
                navController.popBackStack()
                return@composable
            }

            val vm = viewModel<AddEditMoodViewModel>(
                initializer = {
                    AddEditMoodViewModel()
                })

            var entry by remember {
                mutableStateOf<MoodEntry?>(null)
            }

            LaunchedEffect(id) {
                entry = vm.getEntry(id)
            }

            AddEditMoodScreen(
                isEditMode = true,
                entry = entry,
                addEntry = vm::editEntry,
                goBack = navController::popBackStack,
                deleteEntry = vm::deleteEntry,
                emotions = { vm.emotions }
            )
        }
    }
}