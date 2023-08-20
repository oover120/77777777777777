package ru.rustore.sdk.appupdateexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import ru.rustore.sdk.appupdateexample.Onas


class Onas : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onas)



        // Получение ссылки на TextView и установка обработчика клика по нему
        val textКонтакты = findViewById<TextView>(R.id.contact)
        textКонтакты.setOnClickListener {
            // Создание Intent для перехода на новую активность
            val intent = Intent(this@Onas, Page1::class.java)
            startActivity(intent)
        }

        val textView = findViewById<TextView>(R.id.main)
        textView.setOnClickListener {
            val intent = Intent(this@Onas, MainActivity::class.java)
            startActivity(intent)
        }

        val call_button = findViewById<Button>(R.id.call_button)
        call_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:111111111") // замените YOUR_PHONE_NUMBER на номер телефона, на который нужно позвонить
            startActivity(intent)
        }
    }
}