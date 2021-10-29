

package com.example.mailopuw

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.mailopuw.databinding.ActivityMainBinding
import java.text.NumberFormat

/**
 * Activity that displays a tip calculator.
 */
class ActivityMain : AppCompatActivity() {

    // Binding object instance with access to the views in the activity_main.xml layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout XML file and return a binding object instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the content view of the Activity to be the root view of the layout
        setContentView(binding.root)

        // Setup a click listener on the calculate button to calculate the tip
        binding.calculateButton.setOnClickListener { calculateTip() }

        // Set up a key listener on the EditText field to listen for "enter" button presses
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    /**
     * Calculates the tip based on the user input.
     */
    private fun calculateTip() {
        // Get the decimal value from the cost of service EditText field
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val pinjaman = stringInTextField.toDoubleOrNull()

        // If the cost is null or 0, then display 0 tip and exit this function early.

        if (pinjaman == null || pinjaman == 0.0){
            displayTotal(0.0)
            return
        }
        if (pinjaman == null || pinjaman == 0.0){
            displayBiaya(0.0)
            return
        }

        // Get the tip percentage based on which radio button is selected
        val cicilan = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 6
            R.id.option_eighteen_percent -> 12
            else -> 18
        }

        // Calculate the tip
        val layanan = pinjaman * 0.05
        val bunga = pinjaman * 0.0375
        val total = pinjaman + (bunga * cicilan)

        // Display the formatted tip value onscreen

        displayTotal(total)
        displayBiaya(layanan)

    }

    private fun displayTotal(total: Double){
        val formattedTotal = NumberFormat.getCurrencyInstance().format(total)
        binding.totalResult.text = getString(R.string.total_amount, formattedTotal)
    }
    private fun displayBiaya(layanan: Double){
        val formattedBiaya = NumberFormat.getCurrencyInstance().format(layanan)
        binding.layananResult.text = getString(R.string.biaya_amount, formattedBiaya)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}