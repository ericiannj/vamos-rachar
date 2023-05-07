package com.example.vamosrachar
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.widget.EditText
import android.widget.Button
import java.util.Locale
import android.speech.tts.TextToSpeech

class MainActivity : ComponentActivity() {

    var valorIndividual: Double = 0.0
    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vTotal: EditText = findViewById(R.id.valor_total)
        val qtdPessoas: EditText = findViewById(R.id.qtd_pessoas)
        val vDivisao: TextView = findViewById(R.id.valor_dividido)

        val btnTTS: Button = findViewById(R.id.btn_speech)
        val btnCompartilhar: Button = findViewById(R.id.btn_share)


        qtdPessoas.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                val dValor = vTotal.text.toString().toDoubleOrNull()
                val dPessoas = qtdPessoas.text.toString().toIntOrNull()

                if (dValor != null && dPessoas != null && dPessoas != 0) {
                    valorIndividual = dValor / dPessoas
                    vDivisao.text = "Valor individual: R$ ${"%.2f".format(valorIndividual)}"
                } else {
                    vDivisao.text = "Informe o valor total, por favor."
                }
            }
        })

        vTotal.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                val dPessoas = qtdPessoas.text.toString().toIntOrNull()
                val dValor = vTotal.text.toString().toDoubleOrNull()

                if (dPessoas != null && dPessoas != 0 && dValor != null) {
                    valorIndividual = dValor / dPessoas
                    vDivisao.text = "O valor individual é R$ ${"%.2f".format(valorIndividual)}"

                } else {
                    vDivisao.text = "Confirme a quantidade de pessoas, por favor."
                }
            }
        })

        textToSpeech = TextToSpeech(this) {
            textToSpeech.setLanguage(Locale("pt", "BR")
            )}

        btnTTS.setOnClickListener {
            val text = vDivisao.text.toString()
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        btnCompartilhar.setOnClickListener {
            val txtCompartilhado = "O valor individual é R\$ ${"%.2f".format(valorIndividual)}"

            val intent = Intent(Intent(ACTION_SEND))
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, txtCompartilhado)

            val chooser = Intent.createChooser(intent, "Compartilhar com:")

            startActivity(chooser)

        }

    }
}
