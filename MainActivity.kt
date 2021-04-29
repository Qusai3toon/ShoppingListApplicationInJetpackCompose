package com.example.shoppinglistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistcompose.dao.ShoppingListsDao
import com.example.shoppinglistcompose.db.ShoppingDatabase
import com.example.shoppinglistcompose.entities.Market
import com.example.shoppinglistcompose.repositories.Repository
import com.example.shoppinglistcompose.screenComponents.AppNavigator
import com.example.shoppinglistcompose.viewModels.ShoppingListsViewModel
import com.example.shoppinglistcompose.viewModels.TextFieldsViewModel

class MainActivity : ComponentActivity() {
    private lateinit var textFieldsViewModel: TextFieldsViewModel
    private lateinit var dao: ShoppingListsDao

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao = ShoppingDatabase.getInstance(this).dao()
        textFieldsViewModel =
            ViewModelProvider(this).get(TextFieldsViewModel::class.java)
        val shoppingListsViewModel: ShoppingListsViewModel by viewModels {
            ShoppingListsViewModel.ShoppingListsViewModelFactory(repository = Repository(dao = dao))
        }
        val markets: List<Market> = MarketsProvider().getMarketsFromApi()
        setContent {
            //Here you will only see that I only called AppNavigator
            //and that is because every Screen composable that I will use is gonna be called in the navigation graph
            AppNavigator(
                //to deal with text inputs,More explanation in it`s own class
                textFieldsViewModel = textFieldsViewModel,
                //This viewModel here here is for deals with getting the data from the database and
                //inserting data into the database
                shoppingListsViewModel = shoppingListsViewModel,
                context = this,
                markets = markets
            )
        }
    }
}