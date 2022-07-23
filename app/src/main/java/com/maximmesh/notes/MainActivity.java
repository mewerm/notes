package com.maximmesh.notes;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      initCustomToolBar(isLandScape());

      NotesFragment notesFragment = new NotesFragment();//сначала создаем фрагметн

      if (savedInstanceState == null) {
         getSupportFragmentManager()//обращаемся к фрагмент менеджеру (p.s. в рамках одной транзакции можно работать с несколькими фрагментами
         .beginTransaction()
         .add(R.id.notes_container, notesFragment) //удаляем предыдущий фрагмент, добавляем новый. если add, то поверх старого добавляем новый
         .commit();
      }
   }

   private boolean isLandScape() {
      return getResources().getConfiguration().orientation
      == Configuration.ORIENTATION_LANDSCAPE;
   }

   private void initCustomToolBar(boolean isLandScape) {
      Toolbar toolbar = findViewById(R.id.tool_bar);
      setSupportActionBar(toolbar);
      if (!isLandScape) {
         initDrawer(toolbar);
      }
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.main_menu, menu);
      return super.onCreateOptionsMenu(menu);
   }

   private void initDrawer(Toolbar toolbar) {
      DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
      R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawerLayout.addDrawerListener(actionBarDrawerToggle);
      actionBarDrawerToggle.syncState();


      NavigationView navigationView = findViewById(R.id.navigation_view);
      navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
         @SuppressLint("NonConstantResourceId")
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //подписываемся на события кнопок меню
            int id = item.getItemId();

            switch (id) {

               case R.id.action_drawer_author:
                  openAboutAuthorFragment();
                  return true;

               case R.id.action_drawer_notification:
                  openNotificationFragment();
                  return true;

               case R.id.action_drawe_exit:
                  whenCloseApp();
                  return true;

               case R.id.action_exit:
                  whenCloseApp();

               case R.id.action_about:
                  openAboutFragment();
                  return true;
            }
            return false;
         }
      });
   }

   @SuppressLint("NonConstantResourceId")
   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      //подписываемся на события кнопок меню
      int id = item.getItemId();
      switch (id) {
         case R.id.action_about:
            openAboutFragment();
            break;
         case R.id.action_exit:
            whenCloseApp();
      }
      return super.onOptionsItemSelected(item);
   }

   private void openNotificationFragment() {
      getSupportFragmentManager()
      .beginTransaction()
      .addToBackStack("")
      .add(R.id.notes_container, new NotificationFragment()).commit();
   }

   private void openAboutAuthorFragment() {
      getSupportFragmentManager()
      .beginTransaction()
      .addToBackStack("")
      .add(R.id.notes_container, new AboutAuthorFragment()).commit();
   }

   private void openAboutFragment() {
      getSupportFragmentManager()
      .beginTransaction()
      .addToBackStack("")
      .add(R.id.notes_container, new AboutFragment()).commit();
   }

   private void whenCloseApp() {
      new AlertDialog.Builder(MainActivity.this)
      .setTitle("Внимание")
      .setMessage("Вы действительно желаете выйти из приложения?")
      .setNegativeButton("Нет", null)
      .setPositiveButton("Да", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(MainActivity.this, "Осуществлен выход из приложения", Toast.LENGTH_SHORT).show();
            try {
               Thread.sleep(2000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            finish();
         }
      })
      .show();
   }
}