package com.maximmesh.notes;


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
         initDrawer();
      }
   }

   private void initDrawer() {
      DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
      R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawerLayout.addDrawerListener(actionBarDrawerToggle);
      actionBarDrawerToggle.syncState();

   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.main_menu, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      //подписываемся на события кнопок меню
      int id = item.getItemId();
      switch (id) {
         case R.id.action_about:
            getSupportFragmentManager()
            .beginTransaction()
            .addToBackStack("")
            .add(R.id.notes_container, new AboutFragment())
            .commit();
            break;
         case R.id.action_exit:
            Toast.makeText(this, "Осуществлен выход из приложения", Toast.LENGTH_LONG).show();
            try {
               Thread.sleep(3000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            finish();
      }
      return super.onOptionsItemSelected(item);
   }
}
