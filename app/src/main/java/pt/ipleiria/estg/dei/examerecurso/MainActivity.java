package pt.ipleiria.estg.dei.examerecurso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.LinkedList;

import pt.ipleiria.estg.dei.examerecurso.colecoes.Iterador;
import pt.ipleiria.estg.dei.examerecurso.modelo.ModeloDados;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btnComprimir;
    private Button btnAnagrama;
    private EditText editText1;
    private EditText editText2;
    private TextView resAnagramas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
    }

    private void inicializar(){
        spinner = (Spinner) findViewById(R.id.spinner);
        btnComprimir = (Button) findViewById(R.id.btnComprimir);
        btnAnagrama = (Button) findViewById(R.id.btnAnagramas);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        resAnagramas = (TextView) findViewById(R.id.textViewResultadoAnagrama);

        LinkedList<String> listaTestes = new LinkedList<>();
        Iterador<String> it = ModeloDados.INSTANCIA.getListaExemplos();

        while(it.podeAvancar()){
            listaTestes.add(it.avancar());
        }

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTestes));
    }

}
