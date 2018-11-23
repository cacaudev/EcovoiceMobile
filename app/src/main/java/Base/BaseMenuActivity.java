package Base;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cacau2.ecovoicetest.Activity_feedback;
import com.example.cacau2.ecovoicetest.Activity_list_species;
import com.example.cacau2.ecovoicetest.Activity_tab_add_tree;
import com.example.cacau2.ecovoicetest.Activity_tab_edit_profile;
import com.example.cacau2.ecovoicetest.Activity_tab_news_feed;
import com.example.cacau2.ecovoicetest.Activity_tab_profile;
import com.example.cacau2.ecovoicetest.MenuMap;
import com.example.cacau2.ecovoicetest.R;
import com.example.cacau2.ecovoicetest.SessionManagement;
import com.example.cacau2.ecovoicetest.ShowUsersScreen;

public class BaseMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/



        session = new SessionManagement(getApplicationContext());
    }
    public void setUpLayout(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent it;
        //noinspection SimplifiableIfStatement
        if (id == R.id.new_tree_menu) {
            it = new Intent(this,Activity_tab_add_tree.class);
            startActivity(it);
            finish();
            return true;
        } else if (id == R.id.logout_menu) {
            session.logoutUser(session.getToken());
            finish();
            return true;
        } else if (id == R.id.new_tree_menu) {
            it = new Intent(this, Activity_tab_add_tree.class);
            startActivity(it);
            return true;
        } else if (id == R.id.news_feed_menu) {
            it = new Intent(this, Activity_tab_news_feed.class);
            startActivity(it);
            return true;
        } else if (id == R.id.tree_menu) {
            it = new Intent(this, MenuMap.class);
            startActivity(it);
            return true;
        }
        else if (id == R.id.users_menu) {
            it = new Intent(this, ShowUsersScreen.class);
            startActivity(it);
            return true;

        }else if (id == R.id.edit_perfil_menu) {
            it = new Intent(this, Activity_tab_edit_profile.class);
            startActivity(it);
            return true;
        }

        else if (id == R.id.especies_menu) {
            it = new Intent(this, Activity_list_species.class);
            startActivity(it);
            return true;
        }
        else if (id == R.id.add_tree_bar) {
            it = new Intent(this, Activity_tab_add_tree.class);
            startActivity(it);
            return true;
        }
        else if (id == R.id.feedback_bar) {
            it = new Intent(this, Activity_feedback.class);
            startActivity(it);
            return true;
        }
        else if (id == R.id.news_feed_bar) {
            it = new Intent(this, Activity_tab_news_feed.class);
            startActivity(it);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            //TODO current user
            Intent it = new Intent(getBaseContext(), Activity_tab_profile.class);
            startActivity(it);
        } else if (id == R.id.nav_profile_edit) {
            Intent it = new Intent(getBaseContext(), Activity_tab_edit_profile.class);
            startActivity(it);


        } else if (id == R.id.nav_feedback) {
            Intent it = new Intent(getBaseContext(), Activity_feedback.class);
            startActivity(it);

        } else if (id == R.id.nav_feed) {
            Intent it = new Intent(getBaseContext(), Activity_tab_news_feed.class);
            startActivity(it);

        } else if (id == R.id.nav_trees) {
            //TODO all trees
            Intent intent = new Intent(getBaseContext(), MenuMap.class);
            startActivity(intent);
        } else if (id == R.id.nav_species) {
            Intent intent = new Intent(getBaseContext(), Activity_list_species.class);
            startActivity(intent);
            //TODO all species
        } else if (id == R.id.nav_companies) {

        } else if (id == R.id.nav_users) {
            //TODO all users
            Intent intent = new Intent(getBaseContext(), ShowUsersScreen.class);
            startActivity(intent);
            //finish();
        } else if (id == R.id.nav_logout) {
            session.logoutUser(session.getToken());
            finish();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
