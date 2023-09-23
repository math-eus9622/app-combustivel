package com.example.appdecombustivel2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editLitros;
    private EditText editPrecoLitro;
    private Spinner spinnerCombustivel;
    private Spinner spinnerPagamento;
    private TextView textResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editLitros = findViewById(R.id.editLitros);
        editPrecoLitro = findViewById(R.id.editPrecoLitro);
        spinnerCombustivel = findViewById(R.id.spinnerCombustivel);
        spinnerPagamento = findViewById(R.id.spinnerPagamento);
        textResultado = findViewById(R.id.textResultado);

        // Configurar os adaptadores para os spinners
        ArrayAdapter<CharSequence> combustivelAdapter = ArrayAdapter.createFromResource(
                this, R.array.tipos_combustivel, android.R.layout.simple_spinner_item);
        combustivelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCombustivel.setAdapter(combustivelAdapter);

        ArrayAdapter<CharSequence> pagamentoAdapter = ArrayAdapter.createFromResource(
                this, R.array.formas_pagamento, android.R.layout.simple_spinner_item);
        pagamentoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPagamento.setAdapter(pagamentoAdapter);
    }

    public void calcularPrecoTotal(View view) {
        double litros = Double.parseDouble(editLitros.getText().toString());
        double precoLitro = Double.parseDouble(editPrecoLitro.getText().toString());
        String tipoCombustivel = spinnerCombustivel.getSelectedItem().toString();
        String formaPagamento = spinnerPagamento.getSelectedItem().toString();

        double precoTotal = calcularPrecoTotal(litros, precoLitro, tipoCombustivel, formaPagamento);

        textResultado.setText("Preço total a ser pago pelo cliente: R$" + String.format("%.2f", precoTotal));
    }

    private double calcularPrecoTotal(double litros, double precoLitro, String tipoCombustivel, String formaPagamento) {
        double acrescimoCredito = 0.10;
        double acrescimoDebitoPix = 0.05;
        double descontoEspecie = 0.05;

        double precoTotal = litros * precoLitro;

        if (formaPagamento.equals("Crédito")) {
            precoTotal += precoTotal * acrescimoCredito;
        } else if (formaPagamento.equals("Débito") || formaPagamento.equals("Pix")) {
            precoTotal += precoTotal * acrescimoDebitoPix;
        } else if (formaPagamento.equals("Espécie")) {
            precoTotal -= precoTotal * descontoEspecie;
        }

        return precoTotal;
    }
}

