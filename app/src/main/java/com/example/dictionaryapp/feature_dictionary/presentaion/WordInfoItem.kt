package com.example.dictionaryapp.feature_dictionary.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .background(Color(0xFFF0F0F0)) // Light gray background for the whole item
            .padding(16.dp)
    ) {
        // Word text with larger font size and bold weight
        Text(
            text = wordInfo.word,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Origin text with italic style
        Text(
            text = wordInfo.origin,
            fontStyle = FontStyle.Italic,
            color = Color(0xFF777777)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Loop through each meaning and definition
        wordInfo.meanings.forEach { meaning ->
            // Part of speech text with bold weight and color
            Text(
                text = meaning.partOfSpeech,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0066CC)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Loop through each definition and example
            meaning.definitions.forEachIndexed { i, definition ->
                Text(
                    text = "${i + 1}. ${definition.definition}",
                    color = Color(0xFF444444)
                )

                Spacer(modifier = Modifier.height(4.dp))

                definition.example?.let { example ->
                    // Example text with a different color and slight italic style
                    Text(
                        text = "Example: $example",
                        color = Color(0xFF555555),
                        fontStyle = FontStyle.Italic
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
