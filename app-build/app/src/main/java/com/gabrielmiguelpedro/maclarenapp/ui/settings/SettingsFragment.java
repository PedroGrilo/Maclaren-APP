package com.gabrielmiguelpedro.maclarenapp.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gabrielmiguelpedro.maclarenapp.Exceptions.EmailErrorException;
import com.gabrielmiguelpedro.maclarenapp.Exceptions.EmptyFieldException;
import com.gabrielmiguelpedro.maclarenapp.Exceptions.InvalidFieldException;
import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;

public class SettingsFragment extends Fragment {
    private MainActivity callback;
    private SettingsViewModel settingsViewModel;
    private Button button_alterar;
    private EditText editText;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        button_alterar = root.findViewById(R.id.btn_si_alterar);
        editText = root.findViewById(R.id.si_email_alterar);


        button_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (checkFields() == true) {
                        Toast.makeText(getContext(),"Alteru",Toast.LENGTH_SHORT).show();
                        callback.getDb().setEmail(String.valueOf(callback.getUser().getUserID()),editText.getText().toString());
                    }
                } catch (Exception e) {

                }
            }
        });

        return root;
    }

    private boolean checkFields() throws InvalidFieldException, EmptyFieldException, EmailErrorException {
        String ptr = "^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?$";
        String email = editText.getText().toString();
        if (email.isEmpty())
            throw new EmptyFieldException(getString(R.string.empty_field));
        if (!email.matches(ptr))
            throw new InvalidFieldException(getString(R.string.invalid_field));
        if (!callback.getDb().checkEmail(email))
            throw new EmailErrorException(getString(R.string.email_doesnt_exist));
        return true;
    }
}