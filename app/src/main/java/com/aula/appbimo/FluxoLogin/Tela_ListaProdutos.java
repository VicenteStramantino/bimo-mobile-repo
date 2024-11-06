package com.aula.appbimo.FluxoLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.appbimo.AdapterProduto;
import com.aula.appbimo.R;
import com.aula.appbimo.Repositories.ProdutoInterface;
import com.aula.appbimo.models.Produto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tela_ListaProdutos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tela_ListaProdutos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Produto> listaProduto = new ArrayList<>();
    AdapterProduto adapterProduto;
    private int userID = 0;

    public void setUserId(int userId) {
        this.userID = userId;
    }

    public int getUserID() {
        return userID;
    }

    public Tela_ListaProdutos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tela_ListaProdutos.
     */
    // TODO: Rename and change types and number of parameters
    public static Tela_ListaProdutos newInstance(String param1, String param2) {
        Tela_ListaProdutos fragment = new Tela_ListaProdutos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        buscarProdutos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tela__lista_produtos, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        adapterProduto = new AdapterProduto(listaProduto, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterProduto);

        return view;
    }

    private void buscarProdutos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bimo-web-repo.onrender.com/apibimo/produtos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProdutoInterface produtoInterface = retrofit.create(ProdutoInterface.class);

        Call<List<Produto>> call = produtoInterface.listarProdutos();
        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaProduto.clear();

                    for (Produto p : response.body()) {
                        if (userID == 0 || p.getIdUsuario() == userID) {
                            listaProduto.add(p);
                        }
                    }

                    adapterProduto.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
            }
        });
    }
}