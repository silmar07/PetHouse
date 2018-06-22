package com.fdi.pad.pethouse;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class InforApp extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_app);

           TextView t = findViewById(R.id.textViewApp);
            t.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        String infor ="La aplicación Pethouse te permitirá contactar con gente" +
                "que esta dispuesta a cuidar tu mascota, de esta manera tu podras disfrutar " +
                "de unos dias de vacaiones y el cuidador obtener un benefico.\n\n" +
                "Obviamente tu también puedes cuidar mascotas, unicamente debes anuciarte" +
                "y los usuarios de la apliccion podrán verte y ponerse en contacto contigo.\n\n\n" +
                "Gracias por usar nuestra app.";

        t.setText(infor);

    }
}
