
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.quizzer.databinding.ActivityQuizDisplayBinding

lateinit var binding: ActivityQuizDisplayBinding

class QuizDisplay : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.v("oncreate","hello")
       /*
       functions available: 1.to generate a random date
                            2.to find the corresponding day of given date
                            3.to select the options to be displayed
                            4.to display the options
                            5.to display the question
       class available:    1.for encapsulating date variables
       flow:
       create a objest of the class->pass it to function1->pass the randomly generated date to functions2->then keep all the four options ready->display everything->if correct repeat the quiz else display the score
        */
        var score=0
        val dummy = Date()
        val randomDate = dateGeneration(dummy)
        val answerDay = findingDay(dummy)
        val selectedOnes = generatingOptions(answerDay)
        toDisplayQuestion(randomDate)
        toDisplayOptions(selectedOnes)
        binding.optionDisplay.setOnClickListener() {
            val optionSelected = binding.optionDisplay.checkedRadioButtonId.toString()
            if (optionSelected == answerDay) {
                    binding.answerDisplay.text = "Correct!"
                    score++
                    toDisplayQuestion(randomDate)
                    toDisplayOptions(selectedOnes)
                }
            else {
                    binding.answerDisplay.text = "Oops, Wrong!"
                    intent= Intent(this,ScoreDisplay::class.java)
                    intent.putExtra("score",score)
                    startActivity(intent)
                }
        }

    }
}
//class to hold the date variables
class Date(){
    var day:Int=0
    var month:Int=0
    var year:Int=0

}
//will take in a "date" object and feed in a random date
fun dateGeneration(randomDate: Date): Date{
    randomDate.year =(1..2021).random()
    randomDate.month=(1..12).random()
    if((randomDate.year%4==0&&randomDate.year%100!=0)||randomDate.year%400==0){
        if(randomDate.month==2){
            randomDate.day= (1..29).random()
        }
    }
    else{
        randomDate.day=when(randomDate.month){
                1->(1..31).random()
                3->(1..31).random()
                4->(1..30).random()
                5->(1..31).random()
                6->(1..30).random()
                7->(1..31).random()
                8->(1..31).random()
                9->(1..30).random()
                10->(1..31).random()
                11->(1..30).random()
                else->(1..31).random()
        }

    }
    Log.v("dateGeneration",randomDate.year.toString())
    return randomDate
}
//will take in a randomly generated date and return its corresponding day
fun findingDay(randomDate: Date): String{
    var x: Int = (randomDate.year%100)
    x/=4
    x += randomDate.day
    x+=when(randomDate.month){
        1->1
        2->4
        3->4
        4->0
        5->2
        6->5
        7->0
        8->3
        9->6
        10->1
        11->4
        else->6
    }
    if(randomDate.month==1||randomDate.month==2){
        x-=1
    }

    val yearFirstTwoDigits:Int=(randomDate.year/100)
    if(((yearFirstTwoDigits-1)/4)==0){
        x+=4
    }
    else if(((yearFirstTwoDigits-2)/4)==0){
        x+=2
    }
    else if(((yearFirstTwoDigits-3)/4)==0){
        x+=0
    }
    else{
        x+=6
    }

    x+=randomDate.year%100
    x/=7
    val dayOfWeek=when(x){
        1->"Sunday"
        2->"Monday"
        3->"Tuesday"
        4->"Wednesday"
        5->"Thursday"
        6->"Friday"
        else->"Saturday"
    }
    return dayOfWeek
}
//it will generate other three random options and put that along with the correct ans into a list and return it
fun generatingOptions(dayOfWeek: String): MutableList<String>{
    val allDays= mutableListOf("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
    allDays.remove(dayOfWeek)
    val option1=allDays[(0..5).random()]
    allDays.remove(option1)
    val option2=allDays[(0..4).random()]
    allDays.remove(option2)
    val option3=allDays[(0..3).random()]
    allDays.remove(option3)
    return mutableListOf(dayOfWeek,option1,option2,option3)
}
//it will display all the options
fun toDisplayOptions(selectedOnes: MutableList<String>){
    val a = selectedOnes[(0..3).random()]
    binding.optionA.text = a
    selectedOnes.remove(a)
    val b = selectedOnes[(0..2).random()]
    binding.optionB.text = b
    selectedOnes.remove(b)
    val c = selectedOnes[(0..1).random()]
    binding.optionC.text = c
    selectedOnes.remove(c)
    val d = selectedOnes[0]
    binding.optionD.text = d
    selectedOnes.remove(d)
}
// to display the question
fun toDisplayQuestion(randomDate: Date){
    binding.questionDisplay1.text = randomDate.day.toString()
    binding.questionDisplay2.text = randomDate.month.toString()
    binding.questionDisplay3.text = randomDate.year.toString()
    Log.v("quiz_display",randomDate.year.toString())
}