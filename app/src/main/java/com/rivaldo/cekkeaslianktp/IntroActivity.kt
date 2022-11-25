package com.rivaldo.cekkeaslianktp

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.rivaldo.cekkeaslianktp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvVersion.text = Utils.getVersion()
        window.sharedElementEnterTransition.duration = 500

        binding.filledTextField.setEndIconOnClickListener { search() }
        binding.etKtp.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search()
                    return true
                }
                return false
            }
        })
    }

    private fun search() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.EXTRAS_TEXT, binding.etKtp.text)
        val optionsCompat: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(binding.filledTextField, "tnEdittext")
            )
        startActivity(intent, optionsCompat.toBundle())
        finish()
    }
}