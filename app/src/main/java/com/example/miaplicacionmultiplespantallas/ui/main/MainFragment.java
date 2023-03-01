package com.example.miaplicacionmultiplespantallas.ui.main;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
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
        // Comprobamos si no tenemos el permiso:
        if(ContextCompat.checkSelfPermission(getContext(),"android.permission.GET_ACCOUNTS")==PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(),PERMISSIONS_ACCOUNT,23);
        }
        TextView view=result.findViewById(R.id.BienvenidaTextView);
        AccountManager am = AccountManager.get(this.getContext());
        Account[] accounts = am.getAccountsByType("com.google");
        if(accounts.length!=0){
            view.setText("Bienvenido, "+accounts[0].name);
        }else{
            Intent intent = AccountManager.newChooseAccountIntent(null, null,
                    new String[] {"com.google", "com.google.android.legacyimap"},
                    null, null, null, null);
            startActivityForResult(intent, 7);

            accounts = am.getAccountsByType("com.google");
            if(accounts.length!=0)
                view.setText("Bienvenido, "+accounts[0].name);
            else
                view.setText("Bienvenido, no encuentro tu cuenta asocicada");
        }
    }


}