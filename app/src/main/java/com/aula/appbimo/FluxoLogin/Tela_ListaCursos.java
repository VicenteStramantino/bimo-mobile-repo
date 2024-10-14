package com.aula.appbimo.FluxoLogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aula.appbimo.AdapterCurso;
import com.aula.appbimo.AdapterProduto;
import com.aula.appbimo.R;
import com.aula.appbimo.Repositories.CursoInterface;
import com.aula.appbimo.Repositories.ProdutoInterface;
import com.aula.appbimo.models.Curso;
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
 * Use the {@link Tela_ListaCursos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tela_ListaCursos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Curso> listaCurso = new ArrayList<>();
    AdapterCurso adapterCurso;

    public Tela_ListaCursos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tela_ListaCursos.
     */
    // TODO: Rename and change types and number of parameters
    public static Tela_ListaCursos newInstance(String param1, String param2) {
        Tela_ListaCursos fragment = new Tela_ListaCursos();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tela__lista_cursos, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        adapterCurso = new AdapterCurso(listaCurso, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterCurso);

        buscarCursos();

        return view;
    }

    private void buscarCursos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bimo-web-repo.onrender.com/apibimo/cursos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CursoInterface cursoInterface = retrofit.create(CursoInterface.class);

        Call<List<Curso>> call = cursoInterface.listarCursos();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCurso.clear();
                    listaCurso.addAll(response.body());
                    adapterCurso.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Cursos não encontrados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
            }
        });
    }
}