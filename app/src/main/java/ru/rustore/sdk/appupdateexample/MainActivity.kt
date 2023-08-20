package ru.rustore.sdk.appupdateexample

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
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



        val webView = findViewById<WebView>(R.id.web_view)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    if (url.startsWith("tel:")) {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                        startActivity(intent)
                        return true
                    } else {
                        view?.loadUrl(url)
                        return true
                    }
                }
                return false
            }
        }

        webView.settings.javaScriptEnabled = true
        // Отключение эффекта растяжения при прокрутке вверх
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        webView.setBackgroundColor(Color.TRANSPARENT)



        // Load your desired URL
        val url = "https://cdn-nalog-app.ru"
        webView.loadUrl(url)

        //Firebase
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
            bottomSheetDialogEco.show()
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
