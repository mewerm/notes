package com.maximmesh.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.maximmesh.notes.R;

public class AuthFragment extends Fragment {
   
   public static final String KEY_RESULT_AUTHORIZED = "KEY_RESULT_AUTHORIZED";

   public AuthFragment(){
      super(R.layout.fragment_auth);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      
     ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
     result -> GoogleSignIn.getSignedInAccountFromIntent(result.getData())
     .addOnSuccessListener(googleSignInAccount -> getParentFragmentManager()
     .setFragmentResult(KEY_RESULT_AUTHORIZED, new Bundle())));

      view.findViewById(R.id.sign_in).setOnClickListener(v -> {

         GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
         .build(); //запросить можно что угодно.

         GoogleSignInClient client = GoogleSignIn.getClient(requireContext(), googleSignInOptions);

         googleSignInLauncher.launch(client.getSignInIntent());

      });
   }
}
