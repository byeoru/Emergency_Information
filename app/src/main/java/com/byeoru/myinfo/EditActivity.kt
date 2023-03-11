package com.byeoru.myinfo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.byeoru.myinfo.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.blood_types, android.R.layout.simple_list_item_1
        )

        binding.birthdateLayer.setOnClickListener {
            val listener = OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthdateValueTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(this, listener, 2000, 1, 1)
                .show()
        }

        binding.warningCheckBox.isChecked = true
        binding.warningCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.precautionValueTextView.isVisible = isChecked
        }

        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.nameEditText.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(PHONE_NUMBER, binding.phoneValueEditText.text.toString())
            putString(BIRTH_DATE, binding.birthdateValueTextView.text.toString())
            putString(WARNING, getWarning())
            apply()
        }
        Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = when(binding.bloodTypeRadioGroup.checkedRadioButtonId) {
            R.id.bloodTypePlus -> "+"
            R.id.bloodTypeMinus -> "-"
            else -> ""
        }
        return "$bloodSign$bloodAlphabet"
    }

    private fun getWarning(): String {
        return if(binding.warningCheckBox.isChecked)
            binding.precautionValueTextView.text.toString() else ""
    }
}