package com.aula.appbimo.FluxoLogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.aula.appbimo.Tela_ErroInterno;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DatabaseFoto {


    public interface OnUriReceivedListener {
        void onUriReceived(String uri);
    }
    public void uploadFoto(Context c, ShapeableImageView foto, Map<String, String> docData, OnUriReceivedListener listener) {
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();


        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference("galeria").child("galeria_" + System.currentTimeMillis() + ".jpg")
                .putBytes(databyte)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String uriLink = uri.toString();
                                        docData.put("url", uriLink);
                                        Toast.makeText(c, "Foto salva com sucesso!", Toast.LENGTH_SHORT).show();

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
                    c.startActivity(intent);
                });
    }
}