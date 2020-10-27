package br.teste.produtos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.teste.produtos.database.ProdutoDAO;
import br.teste.produtos.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");
        listViewProdutos = findViewById(R.id.lisView_produtos);
        definirOnClickListenerListView();
    }

    protected void onResume(){
        super.onResume();
        ProdutoDAO produtoDAO = new ProdutoDAO((getBaseContext()));
        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                produtoDAO.listar());
        listViewProdutos.setAdapter(adapterProdutos);

    }

    private void definirOnClickListenerListView() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Produto produtoClicado = adapterProdutos.getItem(position);
               Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
               intent.putExtra("produtoEdicao", produtoClicado);
               startActivity(intent);
            }
        });
    }

   public void onClickNovoProduto(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivity(intent);
    }
}