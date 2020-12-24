package id.radenyaqien.githubuserdicoding.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import id.radenyaqien.githubuserdicoding.R
import id.radenyaqien.githubuserdicoding.util.MyReceiver

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.commit { replace(R.id.settings, SettingsFragment()) }
        }
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
        private lateinit var reminderPreferences: SwitchPreferenceCompat
        private lateinit var reminder: String
        private lateinit var alarmReceiver: MyReceiver
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            alarmReceiver = MyReceiver()
            reminder = getString(R.string.reminder)
            reminderPreferences =
                findPreference<SwitchPreferenceCompat>(reminder) as SwitchPreferenceCompat
            initSharedPreferences()


        }

        private fun initSharedPreferences() {
            val sh = preferenceManager.sharedPreferences
            reminderPreferences.isChecked = sh.getBoolean(reminder, false)
        }

        override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
            if (p1 == reminder) {
                p0?.let {
                    reminderPreferences.isChecked = it.getBoolean(reminder, false)
                }
            }
            val state: Boolean =
                PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminder, false)
            setReminder(state)
        }

        private fun setReminder(state: Boolean) {
            if (state) {
                context?.let {
                    alarmReceiver.setRepeatingAlarm(it)
                }
            } else {
                context?.let {
                    alarmReceiver.cancelAlarm(it)
                }
            }
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }


    }
}