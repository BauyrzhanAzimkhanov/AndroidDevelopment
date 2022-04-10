package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.util.Log
import com.example.calculator.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currentExpressionText: TextView
    private lateinit var resultExpressionText: TextView
    private var lastDigitEntered: Boolean = false
    private var errorExpressionEntered: Boolean = false
    private var lastDotEntered: Boolean = false
    private var isEvaluatedExpression: Boolean = false
    private var digit: CharArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    private var operation: CharArray = charArrayOf('+', '-', '×', '÷')
    private var separator: CharArray = charArrayOf('.')
    private var operationPriorities: Map<Char, Int> = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2)

    companion object {
        private const val LAST_DIGIT_ENTERED = "lastDigitEntered"
        private const val ERROR_EXPRESSION_ENTERED = "lastDigitEntered"
        private const val LAST_DOT_ENTERED = "lastDotEntered"
        private const val IS_EVALUATED_EXPRESSION = "isEvaluatedExpression"
        private const val CURRENT_EXPRESSION = "currentExpression"
        private const val RESULT_EXPRESSION = "resultExpression"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initUI()
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            lastDigitEntered = savedInstanceState.getBoolean(LAST_DIGIT_ENTERED, false)
            errorExpressionEntered = savedInstanceState.getBoolean(ERROR_EXPRESSION_ENTERED, false)
            lastDotEntered = savedInstanceState.getBoolean(LAST_DOT_ENTERED, false)
            isEvaluatedExpression = savedInstanceState.getBoolean(IS_EVALUATED_EXPRESSION, false)
            currentExpressionText.text = savedInstanceState.getString(CURRENT_EXPRESSION, "")
            resultExpressionText.text = savedInstanceState.getString(RESULT_EXPRESSION, "0")
        }
    }

    private fun initUI() {
        val view = binding.root
        setContentView(view)
        currentExpressionText = findViewById(R.id.currentExpressionText)
        resultExpressionText = findViewById(R.id.resultExpressionText)
        binding.button0.setOnClickListener(this)
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)
        binding.button7.setOnClickListener(this)
        binding.button8.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
        binding.buttonDivide.setOnClickListener(this)
        binding.buttonErase.setOnClickListener(this)
        binding.buttonMultiply.setOnClickListener(this)
        binding.buttonSubtract.setOnClickListener(this)
        binding.buttonSum.setOnClickListener(this)
        binding.buttonDot.setOnClickListener(this)
        binding.buttonEquals.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_EXPRESSION, currentExpressionText.text.toString())
        outState.putString(RESULT_EXPRESSION, resultExpressionText.text.toString())
        outState.putBoolean(LAST_DIGIT_ENTERED, lastDigitEntered)
        outState.putBoolean(ERROR_EXPRESSION_ENTERED, errorExpressionEntered)
        outState.putBoolean(LAST_DOT_ENTERED, lastDotEntered)
        outState.putBoolean(IS_EVALUATED_EXPRESSION, isEvaluatedExpression)
    }

    private fun getPriority(s: Char): Int {
        val answer = 0
        operationPriorities.getOrDefault(s, answer)
        return answer
    }

    private fun infixToRPN(s: String): String {
        val st: Stack<String> = Stack()
        var answer = ""
        var numberAdded = false
        var i = 0
        while (i < s.length) {
            var tempDigit = ""
            var j: Int = i
            numberAdded = false
            while (s.length > j && (digit.contains(s[j]) || separator.contains(s[j]))) {
                numberAdded = true
                tempDigit += s[j]
                j++
            } // reading float number here
            if (numberAdded) {
                answer += "$tempDigit "
                i = j - 1
            }
            if (s[i] == '(') {
                st.push(s[i].toString())
                continue
            }
            if (s[i] == ')') {
                while (st.peek() != "(") {
                    answer += st.pop() + " "
                }
                if (st.peek() == "(") {
                    st.pop()
                }
                continue
            }
            if (operation.contains(s[i])) {
                val myLog = "$i $answer $st"
                Log.d(s[i].toString(), myLog)
                while (st.size > 0 && (getPriority(st.peek()[0]) >= getPriority(s[i]))) {
                    print("pushed operator")
                    answer += st.pop() + " "
                }
                st.push(s[i].toString())
                i++
                continue
            }
            i++

        }
        while (st.size > 0) {
            answer += st.pop() + " "
        }
        return answer
    }

    private fun functionRPNComputation(s: String): Double {
        val arguments: List<String> = s.split(" ")
        val st: Stack<Double> = Stack()
        var i = 0
        while (i < arguments.size) {
            try {
                st.push(arguments[i].toDouble())
            } catch (e: Exception) {
                var secondOperand: Double
                when (arguments[i]) {
                    "+" -> st.push(st.pop() + st.pop())
                    "×" -> st.push(st.pop() * st.pop())
                    "-" -> {
                        secondOperand = st.pop()
                        st.push(st.pop() - secondOperand)
                    }
                    "÷" -> {
                        secondOperand = st.pop()
                        if (secondOperand != 0.0) {
                            st.push(st.pop() / secondOperand)
                        } else {
                            errorExpressionEntered = true
                            lastDigitEntered = false
                            return 0.0
                        }
                    }
                    "^" -> {
                        secondOperand = st.pop()
                        st.push(Math.pow(st.pop(), secondOperand))
                    }
                    else -> Unit
                }
            }
            i++
        }
        return st.pop()
    }

    private fun onDigitEnter(view: View) {
        clearCurrentExpression()
        if (isEvaluatedExpression) {
            isEvaluatedExpression = false
        }
        if (errorExpressionEntered) {
            currentExpressionText.text = (view as Button).text
            errorExpressionEntered = false
        } else {
            currentExpressionText.append((view as Button).text)
        }
        lastDigitEntered = true
    }

    private fun onDotEnter() {
        clearCurrentExpression()
        if (lastDigitEntered && !errorExpressionEntered && !lastDotEntered) {
            currentExpressionText.append(".")
            lastDigitEntered = false
            lastDotEntered = true
        }
    }

    private fun onOperatorEnter(view: View) {
        clearCurrentExpression()
        if (lastDigitEntered && !errorExpressionEntered) {
            currentExpressionText.append((view as Button).text)
            lastDigitEntered = false
            lastDotEntered = false
        }
    }

    private fun onEraseEnter() {
        clearCurrentExpression()
        if (currentExpressionText.text.isNotEmpty()) {
            if (separator.contains(currentExpressionText.text[currentExpressionText.text.length - 1])) {
                lastDotEntered = false
            }
        }
        this.currentExpressionText.text = this.currentExpressionText.text.dropLast(1)
        if (currentExpressionText.text.isNotEmpty()) {
            if (separator.contains(currentExpressionText.text[currentExpressionText.text.length - 1])) {
                lastDotEntered = true
            } else if (digit.contains(currentExpressionText.text[currentExpressionText.text.length - 1])) {
                lastDigitEntered = true
            }
        }
    }

    private fun onEqualsEnter() {
        if (lastDigitEntered && !errorExpressionEntered) {
            val currentExpression = currentExpressionText.text.toString()
            Log.d("Before infixToRPN: ", currentExpression)
            try {
                var temp: String = infixToRPN(currentExpression)
                Log.d("After infixToRPN: ", temp)
                temp = functionRPNComputation(temp).toString()
                if (errorExpressionEntered) {
                    resultExpressionText.setText(R.string.error_text)
                } else {
                    resultExpressionText.text = temp
                }
                Log.d("After RPNComputation: ", resultExpressionText.text.toString())
                lastDotEntered = true
            } catch (ex: Exception) {
                resultExpressionText.setText(R.string.error_text)
                errorExpressionEntered = true
                lastDigitEntered = false
            }
        }
        lastDigitEntered = false
        lastDotEntered = false
        isEvaluatedExpression = true
    }

    private fun clearCurrentExpression() {
        if (isEvaluatedExpression) {
            this.currentExpressionText.text = ""
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            in listOf(
                R.id.button_0,
                R.id.button_1,
                R.id.button_2,
                R.id.button_3,
                R.id.button_4,
                R.id.button_5,
                R.id.button_6,
                R.id.button_7,
                R.id.button_8,
                R.id.button_9
            ) -> {
                onDigitEnter(v)
            }
            in listOf(
                R.id.button_divide,
                R.id.button_multiply,
                R.id.button_subtract,
                R.id.button_sum
            ) -> {
                onOperatorEnter(v)
            }
            R.id.button_dot -> {
                onDotEnter()
            }
            R.id.button_equals -> {
                onEqualsEnter()
            }
            else -> {
                onEraseEnter()
            }
        }
    }
}