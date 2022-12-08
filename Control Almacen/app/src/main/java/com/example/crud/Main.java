package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FloatingActionButton floatingActionButton;
    private BottomNavigationView bottomnavigationview;
    private NavigationView navigationView;
    private View anchorMenu;
    private int privileges = 0;
    private int numActivity = 1;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        anchorMenu = findViewById(R.id.anchorMenu);
        //Bundle
        Bundle bundle = getIntent().getExtras();
        privileges = Integer.parseInt(bundle.getString("privilege"));
        // Bottom Navigation View
        bottomnavigationview = findViewById(R.id.bottomNavigationView);
        bottomnavigationview.setBackground(null);
        bottomnavigationview.getMenu().getItem(1).setEnabled(false);
        bottomnavigationview.getMenu().getItem(2).setEnabled(false);
        bottomnavigationview.setOnItemSelectedListener(mOnItemSelectedListener);
        // Navigation Drawer
        mDrawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(privileges == 2){
            navigationView.getMenu().getItem(2).setVisible(false);
        }
        //Floating Action Button
        floatingActionButton = findViewById(R.id.fab);
        // Fragments
        Bundle bundleforFragment = new Bundle();
        bundleforFragment.putString("data", "1");
        Fragment listprod = new ListProducts();
        listprod.setArguments(bundleforFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.load_fragment, listprod, "ListProducts").commit();
        // Floating Action Button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFAB();
                switch (numActivity) {
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.load_fragment, new InsertProduct()).addToBackStack(null).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.load_fragment, new InsertCategories()).addToBackStack(null).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.load_fragment, new InsertUsers()).addToBackStack(null).commit();
                        break;
                }
            }
        });
    }

    public void showUpFAB(){
        floatingActionButton.show();
    }

    public void hideFAB(){
        floatingActionButton.hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Main activity = Main.this;
        if(activity != null){
            activity.showUpFAB();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager mgr = getSupportFragmentManager();
        if (mgr.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            floatingActionButton.show();
            mgr.popBackStackImmediate();
        }
    }

    private final BottomNavigationView.OnItemSelectedListener mOnItemSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.miMenu:
                    mDrawerLayout.open();
                    return true;
                case R.id.miSearch:
                    switch (numActivity){
                        case 1:
                            ListProducts fragment = (ListProducts) getSupportFragmentManager().findFragmentByTag("ListProducts");
                            fragment.ShowSearchView();
                            break;
                        default:
                            break;
                    }
                    return true;
                case R.id.miFilter:
                    showPopup(anchorMenu);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemNav(item);
        return true;
    }

    private void selectItemNav(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()){
            case R.id.nav_productos:
                numActivity = 1;
                Bundle bundleforFragment = new Bundle();
                bundleforFragment.putString("data", "1");
                Fragment listprod = new ListProducts();
                listprod.setArguments(bundleforFragment);
                fragmentTransaction.replace(R.id.load_fragment, listprod, "ListProducts").commit();
                mDrawerLayout.close();
                floatingActionButton.show();
                break;
            case R.id.nav_registros:
                numActivity = 2;
                fragmentTransaction.replace(R.id.load_fragment, new ListCategories(), "ListCategories").commit();
                mDrawerLayout.close();
                floatingActionButton.show();
                break;
            case R.id.nav_usuarios:
                numActivity = 3;
                fragmentTransaction.replace(R.id.load_fragment, new ListUsers(), "ListUsers").commit();
                mDrawerLayout.close();
                floatingActionButton.show();
                break;
        }
    }

    public void showPopup(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.NO_GRAVITY, com.google.android.material.R.attr.actionOverflowMenuStyle, 0);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.filter_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String aux;
                switch (item.getItemId()) {
                    case R.id.opt1:
                        aux = "2";
                        SetDir(aux);
                        return true;
                    case R.id.opt2:
                        aux = "3";
                        SetDir(aux);
                        return true;
                    case R.id.opt3:
                        aux = "4";
                        SetDir(aux);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public void SetDir(String aux){
        Bundle bundleforFragment = new Bundle();
        bundleforFragment.putString("data", aux);
        Fragment listprod = new ListProducts();
        listprod.setArguments(bundleforFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.load_fragment, listprod, "ListProducts").commit();
        mDrawerLayout.close();
    }
}