
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.quizzer.databinding.ActivityScoreDisplayBinding

class ScoreDisplay : AppCompatActivity() {

    private lateinit var binding: ActivityScoreDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score= intent.getIntExtra("score",0).toString()
        binding.textView3.text=score

        binding.button2.setOnClickListener() {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
