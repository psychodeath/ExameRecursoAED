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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import pt.ipleiria.estg.dei.examerecurso.colecoes.Fila;
import pt.ipleiria.estg.dei.examerecurso.colecoes.Iterador;
import pt.ipleiria.estg.dei.examerecurso.colecoes.Pilha;
import pt.ipleiria.estg.dei.examerecurso.colecoes.TabelaHashPorListas;
import pt.ipleiria.estg.dei.examerecurso.modelo.ModeloDados;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btnComprimir;
    private Button btnAnagrama;
    private EditText editText1;
    private EditText editText2;
    private TextView txtResAnagramas;
    private TextView txtResCompressao;

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
        txtResAnagramas = (TextView) findViewById(R.id.textViewResultadoAnagrama);
        txtResCompressao = (TextView) findViewById(R.id.textViewResCompressao);

        LinkedList<String> listaTestes = new LinkedList<>();
        Iterador<String> it = ModeloDados.INSTANCIA.getListaExemplos();

        while(it.podeAvancar()){
            listaTestes.add(it.avancar());
        }

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTestes));
    }

    public void onComprimirClick(View view){
        String descomprimido = (String)spinner.getSelectedItem();
        System.out.println("Descomprimido: " + descomprimido);
        StringBuilder inversor = new StringBuilder(descomprimido);
        String invertido = inversor.reverse().toString();
        System.out.println("Invertido: " + invertido);

        Fila<String> filaRes = new Fila<>();

        Character anterior = null;
        Character actual;
        StringBuilder conjuntos = null;
        for (int i = 0; i < invertido.length(); i++){
            actual = invertido.charAt(i);
            if(anterior == null || actual != anterior){
                if(conjuntos != null){
                    filaRes.inserir(conjuntos.toString());
                }
                conjuntos = new StringBuilder();
                //conjuntos.append(actual);
            }
            conjuntos.append(actual);
            anterior = actual;
        }
        filaRes.inserir(conjuntos.toString());


        StringBuilder res = new StringBuilder();
        while(!filaRes.isVazia()){
            String conjuntoChars = filaRes.remover();
            res.append(Integer.toString(conjuntoChars.length()));
            res.append(conjuntoChars.charAt(0));
        }

        txtResCompressao.setText(res.toString());

    }

}
