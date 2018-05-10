package com.fdi.pad.pethouse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.fdi.pad.pethouse.home.fragment_home_user;

public class activity_home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    /**
     * metodo que gestiona el action de lso botones de la barra de navegacion
     * @param item elitem del menu seleccionado9
     * @return segun el item devolvemos la clase .java asociado con ese boton y que dicha calse tendra asociada la vista (xml
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {

            case R.id.btnLupa:
                fragment = new BusqFragment();
            break;

            case R.id.btnHuella:
                fragment = new MascotaFragment();
            break;

            case R.id.btnHome:
                fragment = new HomeFragment();
            break;

            case R.id.btnUsuario:
                fragment = new fragment_home_user();
            break;

            case R.id.btnAjustes:
                fragment = new AjustesFragment();
            break;
        }

        return loadFragment(fragment);

    }


    /**
     * metodo para cargar el fragmento que le pasamos por parametro siempre que no sea null
     * @param fragment framento que le pasamos desde onNAviga... que es, el que esta asociado al action del boton
     * @return true si ha sido correcto false si no es correcto
     */
    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {

            getSupportFragmentManager().
                    beginTransaction().
                         replace(R.id.fragmentHome,fragment).
                                commit();

            return true;
        }


        return false;
    }
}
