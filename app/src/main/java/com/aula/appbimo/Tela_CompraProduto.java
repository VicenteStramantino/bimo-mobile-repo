package com.aula.appbimo;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.aula.appbimo.callbacks.UsuarioCallback;
import com.aula.appbimo.models.Usuario;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Tela_CompraProduto extends Activity {

    private static final int REQUEST_CODE_NOTIFICATIONS = 1; // Código para solicitação de permissão

    private TextView txtdescricao;
    private TextView txtpreco;
    private ImageView imgproduto;
    private MainActivity mainActivity =  new MainActivity();
    private TextView txtnome;
    private TextView txtnmusuario;
    private ImageView imgusuario;
    private ImageView voltar;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_compra_produto);

        txtdescricao = findViewById(R.id.descricaoProduto);
        txtpreco = findViewById(R.id.tituloProduto);
        imgproduto = findViewById(R.id.imgProd);
        txtnome = findViewById(R.id.precoProduto);
        imgusuario = findViewById(R.id.imgfotousuarioprod);
        txtnmusuario = findViewById(R.id.txtnmusuarioprod);
        voltar = findViewById(R.id.voltar);
        btnComprar = findViewById(R.id.btn_comprar);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_CODE_NOTIFICATIONS);
        }

        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String imagem = bundle.getString("img");
        String preco = bundle.getString("preco");
        String descricao = bundle.getString("descricao");
        int idusuario = bundle.getInt("idUsuario");
        String id = bundle.getString("id");

        // Atualiza a UI com as informações recebidas
        txtdescricao.setText(descricao);
        txtpreco.setText("R$ " + preco);
        Glide.with(this).load(imagem).into(imgproduto);
        txtnome.setText(nome);

        Bundle bundle2 = new Bundle();

        mainActivity.pegarUsuarioPorID(new UsuarioCallback() {
            @Override
            public void onUsuarioEncontrado(Usuario usuario) {
                txtnmusuario.setText(usuario.getCnome());
                Glide.with(Tela_CompraProduto.this)
                        .load(usuario.getCimgfirebase())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgusuario);

                bundle2.putString("nome", usuario.getCnome());
                bundle2.putString("img", usuario.getCimgfirebase());
            }

            @Override
            public void onErro(String mensagemErro) {
                // Lida com o erro ao buscar o usuário
                Log.e("Erro", mensagemErro);
            }
        }, idusuario);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificar();
            }
        });

        voltar.setOnClickListener(v -> finish());
    }

    // Este método será chamado após o usuário conceder ou negar a permissão
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void notificar() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permissão para notificações não foi concedida.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar notificação
        Context context = getApplicationContext();
        Intent intentAndroid = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentAndroid, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.union)
                .setContentTitle("Compra realizada!")
                .setContentText("Sua compra de: " + txtnome.getText().toString() + " foi finalizada com sucesso!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationChannel notificationChannel = new NotificationChannel("channel_id", "No", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(notificationChannel);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0, builder.build());
    }
}
