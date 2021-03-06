package com.myapp.worktrack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.myapp.worktrack.adapters.SectionPageAdapter;
import com.myapp.worktrack.databinding.ActivityMainBinding;
import com.myapp.worktrack.fragments.DoingFragment;
import com.myapp.worktrack.fragments.TodoFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private SectionPageAdapter sectionPageAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    ViewPager viewPager = binding.viewPager;

    sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
    setViewPager(viewPager);

    TabLayout tabs = binding.tabs;
    tabs.setupWithViewPager(viewPager);

  }

  private void setViewPager(ViewPager viewPager) {
    SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
    sectionPageAdapter.addFragment(new DoingFragment(), "Doing");
    sectionPageAdapter.addFragment(new TodoFragment(), "To do");

    viewPager.setAdapter(sectionPageAdapter);
  }
}