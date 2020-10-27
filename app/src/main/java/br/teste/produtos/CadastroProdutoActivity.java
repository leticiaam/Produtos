package br.teste.produtos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import br.teste.produtos.database.ProdutoDAO;
import br.teste.produtos.modelo.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private final int RESULT_CODE_PRODUTO_EXCLUIDO = 9;


    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastro de Produto");
        carregarProduto();

    }

    private void carregarProduto() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("produtoEdicao") != null) {
            Produto produto = (Produto) intent.getExtras().get("produtoEdicao");
            EditText editTextNome = findViewById(R.id.editText_nome);
            EditText editTextValor = findViewById(R.id.editText_valor);
            editTextNome.setText(produto.getNome());
            editTextValor.setText(String.valueOf(produto.getValor()));
            id = produto.getId();

        }
    }


    public void onClickExcluir(View v){

        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
        boolean excluiu = produtoDAO.excluir(id);
        if (excluiu){
            Toast.makeText(CadastroProdutoActivity.this, "O produto foi excluído com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        }else {
            Toast.makeText(CadastroProdutoActivity.this, "Erro ao excluir!", Toast.LENGTH_LONG).show();
        }
    }



    public void onClickVoltar(View v){
        finish();
    }

    public  void onClickSalvar(View v) {

        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextValor = findViewById(R.id.editText_valor);
        String nome = editTextNome.getText().toString();
        Float valor = Float.parseFloat(editTextValor.getText().toString());

        Produto produto = new Produto(id,nome, valor);
        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());

        boolean salvou = produtoDAO.salvar(produto);
        if (salvou){
            Toast.makeText(CadastroProdutoActivity.this, "Produto salvo!", Toast.LENGTH_LONG).show();
            finish();
        }else {
            Toast.makeText(CadastroProdutoActivity.this, "Erro ao salvar!", Toast.LENGTH_LONG).show();
        }
    }
}