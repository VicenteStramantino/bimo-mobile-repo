package com.aula.appbimo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DatabaseFotoGeral {

    // Interface para o callback que será usado para obter o URI após o upload
    public interface OnUriReceivedListener {
        void onUriReceived(String uri);
    }

    // Método de upload que recebe um listener para o callback
    public void uploadFoto(Context c, ImageView foto, Map<String, String> docData, OnUriReceivedListener listener) {
        // Conversão da imagem para bytes
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();

        // Inicializa o Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference("produtos").child("produtos_" + System.currentTimeMillis() + ".jpg")
                .putBytes(databyte)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Obtém a URL da imagem após o upload
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String uriLink = uri.toString(); // Atribui o URI diretamente
                                        docData.put("url", uriLink); // Atualiza o docData com a URL

                                        // Notifica o listener com o URI obtido
                                        if (listener != null) {
                                            listener.onUriReceived(uriLink);
                                        }
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Intent intent = new Intent(c, Tela_ErroInterno.class);
                                    c.startActivity(intent);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Intent intent = new Intent(c, Tela_ErroInterno.class);
                    c.startActivity(intent);;
                });
    }
}
