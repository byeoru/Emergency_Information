package com.byeoru.myinfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.byeoru.myinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editFloatingButton.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }

        binding.deleteFloatingButton.setOnClickListener {
            deleteData()
        }

        binding.phoneLayer.setOnClickListener {
            with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.phoneValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        uiUpdate()
    }

    private fun getData(): MutableMap<String, String?> {
        val data = mutableMapOf<String, String?>()
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            data[NAME] = getString(NAME, "미정")
            data[BIRTH_DATE] = getString(BIRTH_DATE, "미정")
            data[PHONE_NUMBER] = getString(PHONE_NUMBER, "미정")
            data[BLOOD_TYPE] = getString(BLOOD_TYPE, "미정")
            data[WARNING] = getString(WARNING, "미정")
        }
        return data
    }

    private fun uiUpdate() {
        with(binding) {
            nameValueTextView.text = getData()[NAME]
            birthdateValueTextView.text = getData()[BIRTH_DATE]
            phoneValueTextView.text = getData()[PHONE_NUMBER]
            bloodTypeValueTextView.text = getData()[BLOOD_TYPE]
            precautionValueTextView.text = getData()[WARNING]
        }
    }

    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            clear()
            apply()
            uiUpdate()
        }
        Toast.makeText(this, "초기화 완료", Toast.LENGTH_SHORT).show()
    }
}