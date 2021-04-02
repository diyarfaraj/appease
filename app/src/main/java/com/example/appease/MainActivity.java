package com.example.appease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.transition.Visibility;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.appease.adapter.MyAdapter;
import com.example.appease.model.MyItem;
import com.example.appease.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout layout_permisson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter adapter = new MyAdapter(this, getApplications());
        recyclerView.setAdapter(adapter);
        layout_permisson = findViewById(R.id.layout_permission);
    }

    private List<MyItem> getApplications() {
        List<MyItem> items = new ArrayList<>();
       // PackageManager manager = getPackageManager();
        //Intent intent = new Intent(Intent.ACTION_MAIN, null);
       // intent.addCategory(Intent.CATEGORY_LAUNCHER);
       // List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
        //for(ResolveInfo resolveInfo:infos){
      //      ActivityInfo activityInfo = resolveInfo.activityInfo;
       //     items.add(new MyItem(activityInfo.loadIcon(manager), activityInfo.loadLabel(manager).toString(),activityInfo.packageName));
       // }
        //return items;
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            //system apps! get out
            if (!isSTOPPED(packageInfo) && !isSYSTEM(packageInfo)) {
                items.add(new MyItem(packageInfo.loadIcon(getPackageManager()),packageInfo.packageName,getApplicationLabel(packageInfo.packageName) ));
            }
        }
        return items;
    }

    private static boolean isSTOPPED(ApplicationInfo pkgInfo) {
        return ((pkgInfo.flags & ApplicationInfo.FLAG_STOPPED) != 0);
    }

    private static boolean isSYSTEM(ApplicationInfo pkgInfo) {
        return ((pkgInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public String getApplicationLabel(String packageName) {
        PackageManager        packageManager = getPackageManager();
        List<ApplicationInfo> packages       = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        String                label          = null;
        for (int i = 0; i < packages.size(); i++) {
            ApplicationInfo temp = packages.get(i);
            if (temp.packageName.equals(packageName))
                label = packageManager.getApplicationLabel(temp).toString();
        }
        return label;
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        toolbar.setTitle("Applications");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }

    public void set_permission(View view) {
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    @Override
    protected void onResume() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(Utils.PermissionCheck(this)){
                layout_permisson.setVisibility(View.GONE);
            } else {
                layout_permisson.setVisibility(View.VISIBLE);
            }
        }
        super.onResume();
    }
}