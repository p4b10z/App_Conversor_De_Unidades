package com.example.conversordeunidades;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Spinners para seleção de unidades
    private Spinner unidadeOrigemSpinner;
    private Spinner unidadeDestinoSpinner;

    // Valores de conversão das unidades selecionadas
    private double fatorOrigem;
    private double fatorDestino;

    // Entrada e saída de dados
    private EditText inputValor;
    private Button botaoConverter;
    private static TextView resultadoTexto;

    // Lista de opções de unidades e seus fatores de conversão
    String[] unidades = {"--Selecione--", "centimetros", "metros", "quilometros", "milhas"};
    double[] fatoresConversao = {0, 0.01, 1.0, 1000.0, 1609.34};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Vinculação de elementos da interface
        unidadeOrigemSpinner = findViewById(R.id.spinner);
        unidadeDestinoSpinner = findViewById(R.id.spinner2);
        botaoConverter = findViewById(R.id.button);
        resultadoTexto = findViewById(R.id.Resultado);
        inputValor = findViewById(R.id.editTextText);

        // Configuração do adaptador para os spinners
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, unidades);
        unidadeOrigemSpinner.setAdapter(adaptador);
        unidadeDestinoSpinner.setAdapter(adaptador);

        // Listener para o spinner de unidade de origem
        unidadeOrigemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                fatorOrigem = fatoresConversao[position]; // Define o fator da unidade selecionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Não faz nada caso nenhuma unidade seja selecionada
            }
        });

        // Listener para o spinner de unidade de destino
        unidadeDestinoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                fatorDestino = fatoresConversao[position]; // Define o fator da unidade selecionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Não faz nada caso nenhuma unidade seja selecionada
            }
        });

        // Ajusta o padding para encaixar a interface com as bordas da tela
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Método acionado pelo botão para converter o valor de uma unidade para outra.
     */
    public void converter(View view) {
        String valorEntradaStr = inputValor.getText().toString();

        // Valida se o valor de entrada foi inserido
        if (valorEntradaStr.isEmpty()) {
            resultadoTexto.setText("Insira um valor válido.");
            return;
        }

        double valorEntrada = Double.parseDouble(valorEntradaStr);

        // Valida se as unidades foram selecionadas corretamente
        if (fatorOrigem == 0 || fatorDestino == 0) {
            resultadoTexto.setText("Selecione as unidades de medida.");
            return;
        }

        // Realiza a conversão com base nos fatores das unidades
        double valorConvertido = valorEntrada * (fatorOrigem / fatorDestino);

        // Exibe o resultado formatado
        resultadoTexto.setText(String.format("%.2f %s é igual a %.2f %s",
                valorEntrada, unidades[unidadeOrigemSpinner.getSelectedItemPosition()],
                valorConvertido, unidades[unidadeDestinoSpinner.getSelectedItemPosition()]));
    }
}
