package com.lucky.miniclip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucky.miniclip.databinding.ActivityMainBinding
import com.lucky.miniclip.util.UiUtil

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.bottom_menu_home->{
                    UiUtil.showToast(this,"Home")
                }
                R.id.bottom_menu_add_video->{
                    UiUtil.showToast(this,"Add video")
                    //Goto VideoUploadActivity
                }
                R.id.bottom_menu_profile->{
                    UiUtil.showToast(this,"Profile")
                    //Goto ProfileActivity
                }
            }
            false
        }
    }
}