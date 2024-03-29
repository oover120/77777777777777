package ru.rustore.sdk.appupdateexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call_button = findViewById<Button>(R.id.call_button)
        call_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:111111111") // замените YOUR_PHONE_NUMBER на номер телефона, на который нужно позвонить
            startActivity(intent)
        }

        FirebaseMessaging.getInstance().subscribeToTopic("news")
            .addOnCompleteListener { task ->
                val msg = if (task.isSuccessful) "Done" else "Failed"
                // do something with the result
            }

        val textViewOnas = findViewById<TextView>(R.id.onas)
        textViewOnas.setOnClickListener {
            val intent = Intent(this, Onas::class.java)
            startActivity(intent)
        }

        val textViewContact = findViewById<TextView>(R.id.contact)
        textViewContact.setOnClickListener {
            val intent = Intent(this, Page1::class.java)
            startActivity(intent)
        }
        //3ndfl
        val bottomSheetDialog = BottomSheetDialog(this)
        val view3ndfl = layoutInflater.inflate(R.layout.bottom_sheet_layout_3ndfl, null)
        bottomSheetDialog.setContentView(view3ndfl)

        val text_view = findViewById<TextView>(R.id.text_view2ndfl)
        text_view.setOnClickListener {
            bottomSheetDialog.show()
        }
        //pfr
        val bottomSheetDialogPF = BottomSheetDialog(this)
        val viewPF = layoutInflater.inflate(R.layout.bottom_sheet_layout_pf, null)
        bottomSheetDialogPF.setContentView(viewPF)

        val text_view_pf = findViewById<TextView>(R.id.text_view2pf)
        text_view_pf.setOnClickListener {
            bottomSheetDialogPF.show()
        }
        //declaracii
        val bottomSheetDialogDec = BottomSheetDialog(this)
        val viewDec = layoutInflater.inflate(R.layout.bottom_sheet_layout_dec, null)
        bottomSheetDialogDec.setContentView(viewDec)
        val textViewDec = findViewById<TextView>(R.id.text_view2dec)
        textViewDec.setOnClickListener {
            bottomSheetDialogDec.show()
        }
         //ktc
        val bottomSheetDialogKtc = BottomSheetDialog(this)
        val viewKts = layoutInflater.inflate(R.layout.bottom_sheet_layout_ktc, null)
        bottomSheetDialogKtc.setContentView(viewKts)
        val textViewKtc = findViewById<TextView>(R.id.text_view2ktc)
        textViewKtc.setOnClickListener {
            bottomSheetDialogKtc.show()
        }
        //eco
        val bottomSheetDialogEco = BottomSheetDialog(this)
        val viewEco = layoutInflater.inflate(R.layout.bottom_sheet_layout_eco, null)
        bottomSheetDialogEco.setContentView(viewEco)
        val textViewEco = findViewById<TextView>(R.id.text_view2eco)
        textViewEco.setOnClickListener {
            bottomSheetDialogKtc.show()
        }



        viewModel.init(this)

        lifecycleScope.launch {
            viewModel.events
                .flowWithLifecycle(lifecycle)
                .collect { event ->
                    when (event) {
                        Event.UpdateCompleted -> popupSnackBarForCompleteUpdate()
                    }
                }
        }
    }

    private fun popupSnackBarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.root_layout),
            getString(R.string.downloading_completed),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(getString(R.string.button_install)) { viewModel.completeUpdateRequested() }
            show()
        }
    }
}
