package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.R.string
import android.util.Log
import android.widget.Switch
import java.text.ParseException
import java.util.*


//import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentExpressionText = findViewById(R.id.currentExpressionText)
        resultExpressionText = findViewById(R.id.resultExpressionText)
    }
//    lateinit var outputTextView: TextView
    lateinit var currentExpressionText: TextView
    lateinit var resultExpressionText: TextView


    var lastDigitEntered: Boolean = false
    var errorExpressionEntered: Boolean = false
    var lastDotEntered :Boolean = false
    var isEvaluatedExpression: Boolean = false
//    var nonZeroDigit: CharArray = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    var digit: CharArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    var zero: CharArray = charArrayOf('0')
    var operation: CharArray = charArrayOf('+', '-', '×', '/')
    var equal: CharArray = charArrayOf('=')
    var separator: CharArray = charArrayOf('.')
    var operationPriorities: Map<Char, Int> = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2)

    fun getPriority (s: Char): Int
    {
        val answer: Int = 0
        operationPriorities.getOrDefault(s, answer)
        return answer
    }

    fun infixToRPN (s: String): String
    {
        var st: Stack<String> = Stack()
        var answer: String = ""
        var numberAdded: Boolean = false
        var i: Int = 0
        while (i < s.length)
        {
            var tempDigit: String = ""
            var j: Int = i
            numberAdded = false
            while (s.length > j && (digit.contains(s[j]) || separator.contains(s[j])))
            {
                numberAdded = true
                tempDigit += s[j]
                j++
            } // reading float number here
            if (numberAdded)
            {
                answer += tempDigit + " "
                i = j - 1
            }
            if (s[i] == '(')
            {
                st.push(s[i].toString())
                continue
            }
            if (s[i] == ')')
            {
                while(st.peek() != "(")
                {
                    answer += st.pop() + " "
                }
                if (st.peek() == "(")
                {
                    st.pop()
                }
                continue
            }
            if (operation.contains(s[i]))
            {
                var myLog: String = i.toString() + " " + answer + " " + st.toString()
                Log.d(s[i].toString(), myLog)
                while(st.size > 0 && (getPriority(st.peek()[0]) >= getPriority(s[i])) )
                {
                    print("pushed operator")
                    answer += st.pop() + " "
                }
                st.push(s[i].toString())
                i++
                continue
            }
            i++

        }
        while (st.size > 0)
        {
            answer += st.pop() + " "
        }
        return answer
    }

    fun RPNComputation (s: String): Double
    {
        var arguments: List<String> = s.split(" ")
        var st: Stack<Double> = Stack()
        var i: Int = 0
        while (i < arguments.size)
        {
            try
            {
                st.push(arguments[i].toDouble())
            }
            catch (e: Exception)
            {
                var secondOperand: Double
                when (arguments[i])
                {
                    "+" -> st.push(st.pop() + st.pop())
                    "×" -> st.push(st.pop() * st.pop())
                    "-" -> {
                        secondOperand = st.pop()
                        st.push(st.pop() - secondOperand)
                    }
                    "/" -> {
                        secondOperand = st.pop()
                        if (secondOperand != 0.0)
                        {
                            st.push(st.pop() / secondOperand)
                        }
                    }
                    "^" -> {
                        secondOperand = st.pop()
                        st.push(Math.pow(st.pop(), secondOperand))
                    }
                    else -> {}
                }
            }
            i++
        }
        return  st.pop()
    }

    fun onDigitEnter (view: View)
    {
        if (errorExpressionEntered)
        {
            currentExpressionText.text = (view as Button).text
            errorExpressionEntered = false
        }
        else
        {
            if(isEvaluatedExpression)
            {
                currentExpressionText.text = ""
                isEvaluatedExpression = false
            }
            currentExpressionText.append((view as Button).text)
        }
        lastDigitEntered = true
    }

    fun onDotEnter (view: View)
    {
        if (lastDigitEntered && !errorExpressionEntered && !lastDotEntered)
        {
            currentExpressionText.append(".")
            lastDigitEntered = false
            lastDotEntered = true
        }
    }

    fun onOperatorEnter (view: View)
    {
        if (lastDigitEntered && !errorExpressionEntered)
        {
            currentExpressionText.append((view as Button).text)
            lastDigitEntered = false
            lastDotEntered = false
        }
    }

    fun onEraseEnter (view: View)
    {
        if(isEvaluatedExpression)
        {
            this.currentExpressionText.text = ""
        }
        this.currentExpressionText.text = this.currentExpressionText.text.dropLast(1)
        lastDigitEntered = false
        errorExpressionEntered = false
        lastDotEntered = false
    }

    fun onEqualsEnter (view: View)
    {

        if (lastDigitEntered && !errorExpressionEntered)
        {
            val currentExpression = currentExpressionText.text.toString()
            Log.d("Before infixToRPN: ", currentExpression)
//            print("Before infixToRPN: " + currentExpression)
            try
            {
                val temp: String = infixToRPN(currentExpression)
                Log.d("After infixToRPN: ", temp)
                resultExpressionText.text = RPNComputation(temp).toString()
                Log.d("After RPNComputation: ", resultExpressionText.text.toString())
//                val result = expression.evaluate()
//                resultExpressionText.text = result.toString()
                lastDotEntered = true
            }
            catch (ex:Exception)
            {
                resultExpressionText.text="Error"
                errorExpressionEntered = true
                lastDigitEntered = false
            }
        }
        isEvaluatedExpression = true
    }
    fun onAdditionalOptionsEnter (view: View)
    {

    }

    fun onRadiansDegreeEnter (view: View)
    {

    }
}