package br.teste.produtos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.teste.produtos.CadastroProdutoActivity;
import br.teste.produtos.database.entity.ProdutoEntity;
import br.teste.produtos.modelo.Produto;

public class ProdutoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + ProdutoEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public ProdutoDAO(Context context){

        dbGateway = DBGateway.getInstance(context);
    }

    public Boolean excluir(long id) {
        ContentValues contentValues = new ContentValues();
        return dbGateway.getDatabase().delete(
                    ProdutoEntity.TABLE_NAME,
                    ProdutoEntity._ID + "=?",
                    new String[]{String.valueOf(id)}) > 0;
        }



    public boolean salvar(Produto produto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProdutoEntity.COLUMN_NAME_NOME, produto.getNome());
        contentValues.put(ProdutoEntity.COLUMN_NAME_VALOR, produto.getValor());


        if (produto.getId() > 0) {
            return dbGateway.getDatabase().update(ProdutoEntity.TABLE_NAME,
                    contentValues,
                    ProdutoEntity._ID + "=?",
                    new String[]{String.valueOf(produto.getId())}) > 0;
        } else {
            return dbGateway.getDatabase().insert(ProdutoEntity.TABLE_NAME,
                    null, contentValues) > 0;
        }
    }

    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(ProdutoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(ProdutoEntity.COLUMN_NAME_NOME));
            Float valor = cursor.getFloat(cursor.getColumnIndex(ProdutoEntity.COLUMN_NAME_VALOR));
            produtos.add(new Produto(id, nome, valor));
        }
        cursor.close();
        return produtos;
    }
}
