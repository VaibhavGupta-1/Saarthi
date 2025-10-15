package com.example.saarthi.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.saarthi.viewmodel.SharedQuizViewModel

private data class QuizQuestion(
    val questionText: String,
    val options: List<String>
)

private val questions = listOf(
    QuizQuestion(
        questionText = "What subjects are you most passionate about?",
        options = listOf("Science and Technology", "Arts and Humanities", "Business and Economics", "Health and Social Care")
    ),
    QuizQuestion(
        questionText = "When facing a tough problem, you tend to:",
        options = listOf("Analyze it logically", "Brainstorm creative solutions", "Organize a team to tackle it", "Empathize with those affected")
    ),
    QuizQuestion(
        questionText = "Which work environment sounds most appealing?",
        options = listOf("A fast-paced, innovative startup", "A large, stable, and structured company", "A creative and collaborative studio", "A quiet, independent research lab")
    ),
    QuizQuestion(
        questionText = "What kind of tasks do you enjoy the most?",
        options = listOf("Solving complex puzzles with data", "Designing something new and beautiful", "Leading a team toward a goal", "Helping and advising people directly")
    ),
    QuizQuestion(
        questionText = "You feel most energized when you are:",
        options = listOf("Building something tangible with code or tools", "Expressing a creative idea or vision", "Making strategic decisions", "Understanding and helping others")
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentQuizScreen(
    navController: NavController,
    viewModel: SharedQuizViewModel
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var answers by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }

    val currentQuestion = questions[currentQuestionIndex]
    val isLastQuestion = currentQuestionIndex == questions.size - 1

    // Wrap the entire screen content in a Scaffold
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp) // Keep our own content padding
        ) {
            Text(
                text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = (currentQuestionIndex + 1) / questions.size.toFloat(),
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(MaterialTheme.shapes.small)
            )
            Column(
                modifier = Modifier.weight(1f).padding(top = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = currentQuestion.questionText, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(32.dp))

                currentQuestion.options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                answers = answers + (currentQuestionIndex to option)
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (answers[currentQuestionIndex] == option),
                            onClick = {
                                answers = answers + (currentQuestionIndex to option)
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text(text = option, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.submitAnswers(answers)

                    if (isLastQuestion) {
                        navController.navigate("results")
                    } else {
                        currentQuestionIndex++
                    }
                },
                enabled = answers.containsKey(currentQuestionIndex), // Enabled only if an answer is selected
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = if (isLastQuestion) "See Your Results" else "Next",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}