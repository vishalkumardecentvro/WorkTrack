package com.myapp.worktrack.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {
  private final List<Fragment> fragmentList = new ArrayList<>();
  private final List<String> fragmentTitleList = new ArrayList<>();

  public SectionPageAdapter(@NonNull @NotNull FragmentManager fm) {
    super(fm);
  }

  @NonNull
  @NotNull
  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }

  @Nullable
  @org.jetbrains.annotations.Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return fragmentTitleList.get(position);
  }

  public void addFragment (Fragment fragment, String title){
    fragmentList.add(fragment);
    fragmentTitleList.add(title);
  }
}
