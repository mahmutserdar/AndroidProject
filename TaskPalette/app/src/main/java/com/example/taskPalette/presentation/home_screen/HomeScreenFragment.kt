package com.example.taskPalette.presentation.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.taskPalette.presentation.dialogs.HowToUseFragment
import com.example.taskPalette.R
import com.example.taskPalette.presentation.task_screen.ScreenPagerAdapter
import com.example.taskPalette.databinding.FragmentHomeScreenBinding
import com.example.taskPalette.presentation.dialogs.ContactInfoFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator


class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    lateinit var pagerAdapter: ScreenPagerAdapter

    private val categories = mutableListOf(
        "Must do", "Should do", "Could do"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabAndAdapter()
        setupNavigationDrawer()
    }

    private fun setupTabAndAdapter() {
        pagerAdapter = ScreenPagerAdapter(categories.size, requireActivity())

        binding.viewPager.apply {
            offscreenPageLimit = 2
            setPageTransformer(CardPageTransformer())
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.TabLayout, binding.viewPager) { tab, position ->
            tab.text = categories[position]
        }.attach()
    }

    private fun setupNavigationDrawer() {
        // Set a click listener for the navigation drawer icon in the toolbar/Action Bar
        binding.navigationButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        // Set a click listener for the items in the navigation drawer menu
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                R.id.mItem1 -> {
                    // Navigate to page 1
                    val howToUseFragment = HowToUseFragment()
                    howToUseFragment.show(childFragmentManager, "HowToUseFragment")
                    binding.drawerLayout.closeDrawer((GravityCompat.START))
                    true
                }
                R.id.mItem2 -> {
                    // Navigate to page 1
                    val contactInfoFragment = ContactInfoFragment()
                    contactInfoFragment.show(childFragmentManager, "ContactInfoFragment")
                    binding.drawerLayout.closeDrawer((GravityCompat.START))
                    true
                }
                else -> false
            }
        }
    }


}