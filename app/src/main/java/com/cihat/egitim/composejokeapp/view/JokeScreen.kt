package com.cihat.egitim.composejokeapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cihat.egitim.composejokeapp.model.Joke
import com.cihat.egitim.composejokeapp.viewmodel.JokeViewModel

@Composable
fun JokeScreen(modifier: Modifier = Modifier, viewModel: JokeViewModel = viewModel()) {
    val jokes by viewModel.jokes.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchJokes()
    }

    LazyColumn {
        items(jokes) { joke ->
            JokeItem(joke)
        }
    }
}

@Composable
fun JokeItem(joke: Joke) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer).padding(5.dp).fillMaxWidth(),
            text = joke.joke ?: "${joke.setup}\n${joke.delivery}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}