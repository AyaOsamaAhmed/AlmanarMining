package net.egyconnect.almanarmining;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView myWebView;
    Boolean check=false;
    Toolbar toolbar ;
    DrawerLayout drawer ;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkNetwork()){
           toolweb();
        }
        else {
            setContentView(R.layout.drawer_no_connect);
            alart();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.RED);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getItemIconTintList();
            navigationView.setItemIconTintList(null);

        }
    }

public void toolweb(){
    if (checkNetwork()){
        setContentView(R.layout.drawer_connect);
        toolbar = (Toolbar) findViewById(R.id.toolbar_one);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.RED);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        myWebView = (WebView)findViewById(R.id.webView);
        loadWebViewLoad(myWebView);


    }


}



    @Override
    public void onBackPressed() {
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            alartExit();
        }
        alartExit();
    }

    private void loadWebViewLoad(WebView x) {
        x.getSettings().setJavaScriptEnabled(true);
        x.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        x.getSettings().setSupportMultipleWindows(true);
        x.setWebViewClient(new WebViewClient());
        x.setWebChromeClient(new WebChromeClient());
        x.loadUrl("http://www.almanar-mining.com/");
    }

    public void alart() {
        AlertDialog al = new AlertDialog.Builder(this).create();
        al.setMessage("No Internet connection. Would you like to enable Internet connection ?");
        al.setButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        al.setButton2("MOBILEDATA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.startActivity(new Intent("android.settings.DATA_ROAMING_SETTINGS"));
            }
        });
        al.setButton3("WIFI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
            }
        });
        al.show();
    }

    private Boolean checkNetwork(){

        ConnectivityManager manager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            check=true;
        }else check=false;
        return check;
    }
    public void alartExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.getItemId() == R.id.exit) {
            alartExit();
        } else {
            checkNetwork();
            toolweb();
            if (check) {
                switch (item.getItemId()) {
                    case R.id.home :
                        this.myWebView.loadUrl("http://www.almanar-mining.com/");
                        break;
                    case R.id.about :
                        this.myWebView.loadUrl("http://www.almanar-mining.com/about-us");
                        break;
                    case R.id.service :
                        this.myWebView.loadUrl("http://www.almanar-mining.com/service");
                        break;
                    case R.id.client :
                        this.myWebView.loadUrl("http://www.almanar-mining.com/clients");
                        break;
                    case R.id.contact :
                        this.myWebView.loadUrl("http://www.almanar-mining.com/contact-us");
                        break;
                    case R.id.exit :
                        alartExit();
                        break;
                    case R.id.share :
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("text/plain");
                        intent.putExtra("android.intent.extra.SUBJECT", "share app");
                        intent.putExtra("android.intent.extra.TEXT", "Download our application from: http://www.egyconnect.net/");
                        startActivity(Intent.createChooser(intent, "Sharing Last Time Tours Application using: "));
                        break;
                    case R.id.develop :
                        this.myWebView.loadUrl("http://www.egyconnect.net/");
                        break;
                    default:
                        break;
                }
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
