package br.com.gutierres.massacorporal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText edtPeso;
    private EditText edtAltura;
    private Button btnCalcular;
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPeso = (EditText) findViewById(R.id.edt_peso);
        edtAltura = (EditText) findViewById(R.id.edt_altura);

        btnCalcular = (Button) findViewById(R.id.btn_calcular);
        btnCalcular.setOnClickListener(this);

        txtResultado = (TextView) findViewById(R.id.txt_resultado);

        edtPeso.addTextChangedListener(this);
        edtAltura.addTextChangedListener(this);

        edtAltura.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                    calcularIMC();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCalcular.getId()) {
            calcularIMC();
        }
    }

    private void calcularIMC() {
        double peso, altura;

        try {
            peso = Double.parseDouble(edtPeso.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(this, "Favor informar um peso válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            altura = Double.parseDouble(edtAltura.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(this, "Favor informar uma altura válida.", Toast.LENGTH_SHORT).show();
            return;
        }

        double IMC = (peso / Math.pow(altura, 2));
        StringBuilder resultado = new StringBuilder(String.valueOf(IMC));
        resultado.append("\n");

        if (IMC < 17)
            resultado.append("Muito abaixo do peso");
        else if (IMC >= 17 && IMC <= 18.49)
            resultado.append("Abaixo do peso");
        else if (IMC >= 18.5 && IMC <= 24.99)
            resultado.append("Peso normal");
        else if (IMC >= 25 && IMC <= 29.99)
            resultado.append("Acima do peso");
        else if (IMC >= 30 && IMC <= 34.99)
            resultado.append("Obesidade I");
        else if (IMC >= 35 && IMC <= 39.99)
            resultado.append("Obesidade II (severa)");
        else
            resultado.append("Obesidade III (mórbida)");

        txtResultado.setText(resultado.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        txtResultado.setText("");
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
