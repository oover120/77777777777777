package ru.rustore.sdk.appupdateexample

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*

class Page1 : AppCompatActivity() {

    private val examples = arrayOf(
        "г. Краснодар ул.Коммунаров,290/1",
        "г. Анапа ул.Шевченко,1",
        "г. Армавир ул.Дзержинского,62",
        "г. Геленджик ул.Луначарского,176",
        "г. Горячий Ключ ул.Ленина,195Г",
        "г. Ейск ул.Красная улица 59/5",
        "г. Лабинск ул.Красная,67/1",
        "г. Новокубанск ул.Советская,90",
        "г. Сочи ул.Навагинская,16",
        "мкр.Хоста, Сочи ул.Лесная22,кв11",
        "г. Славянск-на-Кубани ул.Красная,7А",
        "г. Тимашевск ул.Ленина,169",
        "п. Мостовской ул.Горького,142А",
        "ст-ца Брюховецкая ул. Октябрьская,10",
        "ст-ца Динская ул. Гоголя, д. 108 к.А",
        "ст-ца Каневская ул. Нестеренко,59",
        "ст-ца Кущевская ул. Ленина,14",
        "ст-ца Новопокровская ул.Ленина,100,Пом 1",
        "ст-ца Павловская ул. Горького,293",
        "ст-ца Полтавская ул. Коммунистическая,187",
        "ст-ца. Староминская ул.Красная,26",
        "ст-ца Старощербиновская ул.Красная,56",
        "ст-ца Тбилисская ул. Новая, 7Б",
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kontact)


        // Находим ListView в разметке по его ID
        val listView = findViewById<ListView>(R.id.list_view)

        val adapter = ArrayAdapter(this, R.layout.list_item, examples)
        listView.adapter = adapter

        //
        val databaseReference = FirebaseDatabase.getInstance().getReference("message")

        val textView = findViewById<TextView>(R.id.message)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val message = dataSnapshot.getValue(String::class.java)
                textView.text = message
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок чтения базы данных
            }
        })
        //

        val call_button = findViewById<Button>(R.id.call_button)
        call_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:111111111") // замените YOUR_PHONE_NUMBER на номер телефона, на который нужно позвонить
            startActivity(intent)
        }

        // Получение ссылки на TextView и установка обработчика клика по нему
        val textКонтакты = findViewById<TextView>(R.id.onas)
        textКонтакты.setOnClickListener {
            // Создание Intent для перехода на новую активность
            val intent = Intent(this@Page1, onas::class.java)
            startActivity(intent)
        }

        val textГлавная = findViewById<TextView>(R.id.main)
        textГлавная.setOnClickListener {
            val intent = Intent(this@Page1, MainActivity::class.java)
            startActivity(intent)
        }

        val onasTextView = findViewById<TextView>(R.id.onas)
        onasTextView.setOnClickListener {
            val intent = Intent(this, Onas::class.java)
            startActivity(intent)
        }


        val qrCodeImageView: ImageView = findViewById(R.id.qr_code_image_view)

        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        var uuidString = sharedPreferences.getString("UUID", "")

        if (uuidString.isNullOrEmpty()) {
            // Если UUID не сохранен в SharedPreferences, создаем новый и сохраняем его
            val uuid: UUID = UUID.randomUUID()
            uuidString = uuid.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("UUID", uuidString)
            editor.apply()
        }

        // Используем сохраненный UUID вместо генерации нового
        val text = "$uuidString Накопительная скидка"

        // Задаем размеры QR-кода
        val width = 650
        val height = 650

        // Задаем параметры для генерации QR-кода
        val hints: MutableMap<EncodeHintType, Any> = HashMap()
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"

        // Генерируем QR-код из текста
        try {
            val writer = QRCodeWriter()
            val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints)

            // Преобразуем QR-код в Bitmap и выводим его в ImageView
            val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
                }
            }
            qrCodeImageView.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            e.printStackTrace()
        }

    }

}

class EncodeHintType {

}

class onas {

}
