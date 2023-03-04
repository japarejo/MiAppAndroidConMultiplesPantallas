package com.example.miaplicacionmultiplespantallas.ui.main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static 	android.content.pm.PackageManager.PERMISSION_DENIED;

import com.example.miaplicacionmultiplespantallas.R;

public class MainFragment extends Fragment {

    private static String[] PERMISSIONS_ACCOUNT = {"android.permission.GET_ACCOUNTS"};

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_main, container, false);
        actualizarUsuario(result);
        return result;
    }

    private void actualizarUsuario(View result) {
        // Comprobamos si no tenemos el permiso (esto vale para versión antiguas de Android):
        if(ContextCompat.checkSelfPermission(getContext(),"android.permission.GET_ACCOUNTS")==PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(),PERMISSIONS_ACCOUNT,23);
        }
        // Intentamos obtener la cuenta y si no la tenemos, solicitamos el permiso y la cuenta a usar:
        TextView view=result.findViewById(R.id.BienvenidaTextView);
        Context context=this.getContext();
        AccountManager am = AccountManager.get(context);
        Account[] accounts = am.getAccountsByType("com.google");
        if(accounts.length!=0){
            view.setText("Bienvenido, "+accounts[0].name);
        }else{
            // Creamos el Intent, es decir la ventana, que nos permitirá elegir la cuenta del sistema a usar:
             Intent intent = AccountManager.newChooseAccountIntent(null, null,
                    new String[] {"com.google", "com.google.android.legacyimap"},
                    null, null, null, null);
            // Definimos qué debe hacer la aplicación una vez se haya escogido la cuenta a usar:
            ActivityResultLauncher<Intent> mGetAccounts = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            // Básicamente sacamos la cuenta con el account manager y mostramos el nombre:
                            AccountManager am = AccountManager.get(context);
                            Account[] accounts = am.getAccountsByType("com.google");
                            if(accounts.length!=0)
                                view.setText("Bienvenido, "+accounts[0].name);
                            else
                                view.setText("Bienvenido, no encuentro tu cuenta asocicada");
                        }
                    });
            // Lanzamos la ejecución de la nueva ventana (de elección de cuenta), para que se muestre:
            mGetAccounts.launch(intent);
        }

    }


}