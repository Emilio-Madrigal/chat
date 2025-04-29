package com.example.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class One_Time_Password extends AppCompatActivity {
    Long duracion=60L;
    EditText OTP;
    Button SiguienteOTP;
    ProgressBar OTPbar;
    FirebaseAuth auth = FirebaseAuth.getInstance ();
    String TELEFONO;
    Context context;
    String codigoVerificacion;
    PhoneAuthProvider.ForceResendingToken ResendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        EdgeToEdge.enable (this);
        setContentView (R.layout.activity_one_time_password);

        OTP=findViewById (R.id.OTP);
        SiguienteOTP=findViewById (R.id.siguienteOTP);
        OTPbar=findViewById (R.id.progressBarOTP);

        TELEFONO=getIntent ().getExtras ().getString ("telefono");

        Map<String,String> data = new HashMap<> ();
        FirebaseFirestore.getInstance ().collection ("test").add (data);
        enviarOTP (TELEFONO,false);
    }

    void enviarOTP(String telefono, boolean enviado){
        progreso (true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions
                        .newBuilder (auth)
                        .setPhoneNumber (TELEFONO).setTimeout (duracion, TimeUnit.SECONDS)
                        .setActivity (this)
                        .setCallbacks (new PhoneAuthProvider.OnVerificationStateChangedCallbacks () {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                singIn(phoneAuthCredential);
                                progreso (false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(One_Time_Password.this, "error en la verificacion", Toast.LENGTH_SHORT).show();
                                progreso (false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent (s, forceResendingToken);

                                codigoVerificacion=s;
                                ResendingToken=forceResendingToken;
                                Toast.makeText(One_Time_Password.this, "codigo enviado correctamente", Toast.LENGTH_SHORT).show();

                                progreso (false);
                            }
                        });
        if(enviado){
            PhoneAuthProvider.verifyPhoneNumber (builder.setForceResendingToken (ResendingToken).build ());
        }
        else {
            PhoneAuthProvider.verifyPhoneNumber (builder.build ());
        }

    }

    void progreso(boolean enproceso){
        if(enproceso){
            OTPbar.setVisibility (View.VISIBLE);
            SiguienteOTP.setVisibility (View.GONE);
        }else{
            OTPbar.setVisibility (View.GONE);
            SiguienteOTP.setVisibility (View.VISIBLE);
        }
    }

    void singIn(PhoneAuthCredential phoneAuthCredential){

    }
}