package dk.nenolink.birthdayblockquest

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.random.Random

data class MathQuestion(
    val text: String,
    val correctAnswer: Int,
    val choices: List<Int>
)

class MainActivity : Activity() {
    private val totalQuestions = 10
    private var score = 0
    private var questionNumber = 0
    private var currentQuestion: MathQuestion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showStartScreen()
    }

    private fun showStartScreen() {
        val screen = verticalScreen("#8BD3FF")

        screen.addView(titleText("Birthday Block Quest"))
        screen.addView(bigBlockText("Tillykke med de 10 år!"))
        screen.addView(messageText("Byg din score med sjove matematik-klodser."))

        val startButton = blockButton("Start spillet", "#00A676")
        startButton.setOnClickListener {
            score = 0
            questionNumber = 0
            showQuizScreen()
        }
        screen.addView(startButton)

        setContentView(screen)
    }

    private fun showQuizScreen() {
        if (questionNumber == totalQuestions) {
            showFinalScreen()
            return
        }

        questionNumber++
        currentQuestion = makeQuestion()

        val question = currentQuestion ?: return
        val screen = verticalScreen("#FFF3B0")

        screen.addView(scoreText())
        screen.addView(messageText("Spørgsmål $questionNumber af $totalQuestions"))
        screen.addView(bigBlockText(question.text))

        question.choices.forEachIndexed { index, answer ->
            val color = listOf("#FF6B6B", "#4D96FF", "#00A676")[index]
            val button = blockButton(answer.toString(), color)
            button.setOnClickListener { checkAnswer(answer) }
            screen.addView(button)
        }

        setContentView(screen)
    }

    private fun checkAnswer(answer: Int) {
        val question = currentQuestion ?: return
        if (answer == question.correctAnswer) {
            score += 10
        }
        showQuizScreen()
    }

    private fun showFinalScreen() {
        val screen = verticalScreen("#C7F9CC")

        screen.addView(titleText("Du klarede questen!"))
        screen.addView(bigBlockText("Tillykke med fødselsdagen!"))
        screen.addView(messageText("Du fik $score point ud af ${totalQuestions * 10}."))
        screen.addView(messageText("Må din dag være fuld af kage, klodser og gode eventyr."))

        val playAgainButton = blockButton("Spil igen", "#4D96FF")
        playAgainButton.setOnClickListener { showStartScreen() }
        screen.addView(playAgainButton)

        setContentView(screen)
    }

    private fun makeQuestion(): MathQuestion {
        return when (Random.nextInt(4)) {
            0 -> additionQuestion()
            1 -> subtractionQuestion()
            2 -> multiplicationQuestion()
            else -> divisionQuestion()
        }
    }

    private fun additionQuestion(): MathQuestion {
        val a = Random.nextInt(8, 51)
        val b = Random.nextInt(5, 49)
        val answer = a + b
        return question("Hvad er $a + $b?", answer)
    }

    private fun subtractionQuestion(): MathQuestion {
        val answer = Random.nextInt(5, 55)
        val b = Random.nextInt(4, 46)
        val a = answer + b
        return question("Hvad er $a - $b?", answer)
    }

    private fun multiplicationQuestion(): MathQuestion {
        val a = Random.nextInt(2, 11)
        val b = Random.nextInt(2, 11)
        val answer = a * b
        return question("Hvad er $a x $b?", answer)
    }

    private fun divisionQuestion(): MathQuestion {
        val answer = Random.nextInt(2, 11)
        val divisor = Random.nextInt(2, 10)
        val total = answer * divisor
        return question("Hvad er $total / $divisor?", answer)
    }

    private fun question(text: String, correctAnswer: Int): MathQuestion {
        val choices = mutableSetOf(correctAnswer)

        while (choices.size < 3) {
            val wrongAnswer = (correctAnswer + Random.nextInt(-10, 11)).coerceAtLeast(1)
            choices.add(wrongAnswer)
        }

        return MathQuestion(
            text = text,
            correctAnswer = correctAnswer,
            choices = choices.shuffled()
        )
    }

    private fun verticalScreen(backgroundColor: String): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.parseColor(backgroundColor))
        }
    }

    private fun titleText(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 34f
            setTextColor(Color.parseColor("#222222"))
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            layoutParams = blockLayoutParams()
        }
    }

    private fun bigBlockText(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 28f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            background = blockBackground("#7B2CBF")
            setPadding(24, 24, 24, 24)
            layoutParams = blockLayoutParams()
        }
    }

    private fun scoreText(): TextView {
        return TextView(this).apply {
            text = "Score: $score"
            textSize = 24f
            setTextColor(Color.parseColor("#222222"))
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            layoutParams = blockLayoutParams()
        }
    }

    private fun messageText(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 20f
            setTextColor(Color.parseColor("#222222"))
            gravity = Gravity.CENTER
            layoutParams = blockLayoutParams()
        }
    }

    private fun blockButton(text: String, color: String): Button {
        return Button(this).apply {
            this.text = text
            textSize = 22f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            background = blockBackground(color)
            setPadding(16, 16, 16, 16)
            layoutParams = blockLayoutParams()
        }
    }

    private fun blockLayoutParams(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 12, 0, 12)
        }
    }

    private fun blockBackground(color: String): GradientDrawable {
        return GradientDrawable().apply {
            setColor(Color.parseColor(color))
            cornerRadius = 14f
            setStroke(6, Color.parseColor("#222222"))
        }
    }
}
