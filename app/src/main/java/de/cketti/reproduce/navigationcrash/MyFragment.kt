package de.cketti.reproduce.navigationcrash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

class MyFragment : Fragment() {
    private val activityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        setFragmentResult("fragmentResult", Bundle())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.my_fragment, container, false)

        view.findViewById<Button>(R.id.button).setOnClickListener {
            val secondActivityIntent = Intent(requireContext(), SecondActivity::class.java)
            activityLauncher.launch(secondActivityIntent)
        }

        return view
    }
}
