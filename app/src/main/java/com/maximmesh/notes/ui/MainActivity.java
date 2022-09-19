package com.maximmesh.notes.ui;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.maximmesh.notes.R;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      getSupportFragmentManager().setFragmentResultListener(AuthFragment.KEY_RESULT_AUTHORIZED, this, new FragmentResultListener() {
         @Override
         public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            showNotes();
         }
      });

      if (savedInstanceState == null) {
         if( isAuthorized()){
            showNotes();
         }else{
            showAuth();
         }
      }

      BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

      bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
               case R.id.action_notes:

                  if( isAuthorized()){
                     showNotes();
                  }else{
                     showAuth();
                  }
                  return true;

               case R.id.action_info:
                  getSupportFragmentManager()
                  .beginTransaction()
                  .replace(R.id.container, new InfoFragment())
                  .commit();
                  return true;
            }
            return false;
         }
      });

   }

   private void showNotes() {
      getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.container, new NotesListFragment())
      .commit();
   }

   private void showAuth() {
      getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.container, new AuthFragment())
      .commit();
   }

   private boolean isAuthorized(){
      return GoogleSignIn.getLastSignedInAccount(this) != null ;
   }
}