package com.fdi.pad.pethouse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.fdi.pad.pethouse.fragments.AjustesFragment;
import com.fdi.pad.pethouse.home.FragmentHomeSearch;
import com.fdi.pad.pethouse.home.fragment_home_user;
import com.fdi.pad.pethouse.fragments.*;

public class activity_home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    private FragmentHomeSearch bsqFragment;
    private MascotaFragment masFragment;
    private HomeFragment homeFragment;
    private fragment_home_user homeUserFragment;
    private AjustesFragment ajsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Creamos los fragmentos
        bsqFragment = new FragmentHomeSearch();
        masFragment = new MascotaFragment();
        homeFragment = new HomeFragment();
        homeUserFragment = new fragment_home_user();
        ajsFragment = new AjustesFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        loadFragment(homeFragment);
        navigation.setSelectedItemId(R.id.btnHome);

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
                fragment = bsqFragment;
            break;

            case R.id.btnHuella:
                fragment = masFragment;
            break;

            case R.id.btnHome:
                fragment = homeFragment;
            break;

            case R.id.btnUsuario:
                fragment = homeUserFragment;
            break;

            case R.id.btnAjustes:
                fragment = ajsFragment;
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
