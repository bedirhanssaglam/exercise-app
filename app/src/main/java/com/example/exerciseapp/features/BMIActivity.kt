package com.example.exerciseapp.features

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exerciseapp.R
import com.example.exerciseapp.utils.checkBMI
import com.example.exerciseapp.databinding.ActivityBmiBinding
import com.example.exerciseapp.utils.editableToFloat
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }

    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()
        initializeViews()
        setListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.calculate_bmi)
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initializeViews() {
        makeVisibleMetricUnitsView()
    }

    private fun setListeners() {
        binding?.radioUnits?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.apply {
            inputMetricUnitWeight.visibility = View.VISIBLE
            inputMetricUnitHeight.visibility = View.VISIBLE
            inputUsUnitWeight.visibility = View.GONE
            tilMetricUsUnitHeightFeet.visibility = View.GONE
            tilMetricUsUnitHeightInch.visibility = View.GONE
            clearMetricInputs()
            llDisplayBMIResult.visibility = View.INVISIBLE
        }
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.apply {
            inputMetricUnitWeight.visibility = View.INVISIBLE
            inputMetricUnitHeight.visibility = View.INVISIBLE
            inputUsUnitWeight.visibility = View.VISIBLE
            tilMetricUsUnitHeightFeet.visibility = View.VISIBLE
            tilMetricUsUnitHeightInch.visibility = View.VISIBLE
            clearUsInputs()
            llDisplayBMIResult.visibility = View.INVISIBLE
        }
    }

    private fun clearMetricInputs() {
        binding?.editTextMetricUnitHeight?.text?.clear()
        binding?.editTextMetricUnitWeight?.text?.clear()
    }

    private fun clearUsInputs() {
        binding?.editTextUsUnitWeight?.text?.clear()
        binding?.etUsMetricUnitHeightFeet?.text?.clear()
        binding?.etUsMetricUnitHeightInch?.text?.clear()
    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val bmi: Float = calculateMetricBMI()
                displayBMIResult(bmi)
            } else {
                showToast(getString(R.string.please_enter_valid_values))
            }
        } else {
            if (validateUsUnits()) {
                val bmi: Float = calculateUsBMI()
                displayBMIResult(bmi)
            } else {
                showToast(getString(R.string.please_enter_valid_values))
            }
        }
    }

    private fun calculateMetricBMI(): Float {
        val weightValue: Float = binding?.editTextMetricUnitWeight?.text.editableToFloat()
        val heightValue: Float = binding?.editTextMetricUnitHeight?.text.editableToFloat() / 100
        return weightValue / (heightValue * heightValue)
    }

    private fun calculateUsBMI(): Float {
        val weightValue: Float = binding?.editTextUsUnitWeight?.text.editableToFloat()
        val heightValueInch: Float = binding?.etUsMetricUnitHeightInch?.text.editableToFloat()
        val heightValueFeet: Float = binding?.etUsMetricUnitHeightFeet?.text.editableToFloat()
        val heightValue: Float = heightValueFeet * 12 + heightValueInch
        return 703 * (weightValue / (heightValue * heightValue))
    }

    private fun validateMetricUnits(): Boolean {
        return binding?.editTextMetricUnitWeight?.text?.isNotEmpty() == true &&
                binding?.editTextMetricUnitHeight?.text?.isNotEmpty() == true
    }

    private fun validateUsUnits(): Boolean {
        return binding?.editTextUsUnitWeight?.text?.isNotEmpty() == true &&
                binding?.etUsMetricUnitHeightFeet?.text?.isNotEmpty() == true &&
                binding?.etUsMetricUnitHeightInch?.text?.isNotEmpty() == true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiPair: Pair<String, String> = bmi.checkBMI(this)

        val bmiValue: String = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.apply {
            llDisplayBMIResult.visibility = View.VISIBLE
            tvBMIValue.text = bmiValue
            tvBMIType.text = bmiPair.first
            tvBMIDescription.text = bmiPair.second
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
