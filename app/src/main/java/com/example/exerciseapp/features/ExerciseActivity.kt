package com.example.exerciseapp.features

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerciseapp.*
import com.example.exerciseapp.adapters.ExerciseStatusAdapter
import com.example.exerciseapp.databinding.ActivityExerciseBinding
import com.example.exerciseapp.databinding.DialogCustomBackConfirmationBinding
import com.example.exerciseapp.model.ExerciseModel
import com.example.exerciseapp.utils.Exercises
import com.example.exerciseapp.utils.TextToSpeechUtil
import com.example.exerciseapp.utils.toSoundUri

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseList: ArrayList<ExerciseModel> = Exercises.defaultExerciseList()
    private var currentExercisePosition = -1
    private var ttsUtil: TextToSpeechUtil? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        ttsUtil = TextToSpeechUtil(this)
        setupExerciseStatusRecyclerView()
        setupRestView()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarExercise.setNavigationOnClickListener { customDialogForBackButton() }
    }

    private fun setupExerciseStatusRecyclerView() {
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun setupRestView() {
        playStartSound()
        showRestView()
        binding.tvUpcomingExerciseName.text = exerciseList[currentExercisePosition + 1].getName()
        startRestTimer()
    }

    private fun playStartSound() {
        val soundURI: Uri = R.raw.press_start.toSoundUri()
        player = MediaPlayer.create(applicationContext, soundURI).apply {
            isLooping = false
            start()
        }
    }

    private fun showRestView() {
        with(binding) {
            flRestView.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            tvExerciseName.visibility = View.INVISIBLE
            flExerciseView.visibility = View.INVISIBLE
            ivImage.visibility = View.INVISIBLE
            tvUpcomingLabel.visibility = View.VISIBLE
            tvUpcomingExerciseName.visibility = View.VISIBLE
        }
    }

    private fun startRestTimer() {
        restTimer?.cancel()
        binding.progressBar.progress = 0
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBar.progress = (10000 - millisUntilFinished).toInt() / 1000
                binding.tvTimer.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList[currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyItemChanged(currentExercisePosition)
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {
        showExerciseView()
        startExerciseTimer()
    }

    private fun showExerciseView() {
        with(binding) {
            flRestView.visibility = View.GONE
            tvTitle.visibility = View.INVISIBLE
            tvExerciseName.visibility = View.VISIBLE
            flExerciseView.visibility = View.VISIBLE
            ivImage.visibility = View.VISIBLE
            tvUpcomingLabel.visibility = View.INVISIBLE
            tvUpcomingExerciseName.visibility = View.INVISIBLE

            ivImage.setImageResource(exerciseList[currentExercisePosition].getImage())
            tvExerciseName.text = exerciseList[currentExercisePosition].getName()
        }
        ttsUtil?.speak(exerciseList[currentExercisePosition].getName())
    }

    private fun startExerciseTimer() {
        exerciseTimer?.cancel()
        binding.progressBarExercise.progress = 0
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBarExercise.progress = (30000 - millisUntilFinished).toInt() / 1000
                binding.tvTimerExercise.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList.size - 1) {
                    exerciseList[currentExercisePosition].apply {
                        setIsSelected(false)
                        setIsCompleted(true)
                    }
                    exerciseAdapter?.notifyItemChanged(currentExercisePosition)
                    setupRestView()
                } else {
                    navigateToSuccessActivity()
                }
            }
        }.start()
    }

    private fun navigateToSuccessActivity() {
        finish()
        startActivity(Intent(this, SuccessActivity::class.java))
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding: DialogCustomBackConfirmationBinding =
            DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onDestroy() {
        restTimer?.cancel()
        exerciseTimer?.cancel()
        ttsUtil?.shutDown()
        player?.stop()
        super.onDestroy()
    }
}
