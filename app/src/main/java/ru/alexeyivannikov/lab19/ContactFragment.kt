package ru.alexeyivannikov.lab19

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.alexeyivannikov.lab19.databinding.FragmentContactBinding


class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding: FragmentContactBinding
        get() = _binding ?: throw RuntimeException()

    private val adapter = ContactAdapter()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("LOGIC_TEST", "is granted")
            loadContacts()
        } else {
            navigateToExplanation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvContacts.adapter = adapter
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btGetContacts.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        android.Manifest.permission.READ_CONTACTS
                    )
                ) {
                    navigateToExplanation()
                } else {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
                }
            } else {
                loadContacts()
            }
        }
    }


    @SuppressLint("Range")
    fun loadContacts(){
        val result = mutableListOf<String>()
        val cur = requireActivity().contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if (cur != null) {
            val colName = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            while (cur.moveToNext()) {
                val name = cur.getString(colName)
                result.add(name)
            }
            cur.close()
        }
        Log.d("LOGIC_TEST", result.toString())
        adapter.submitList(result)
    }

    private fun navigateToExplanation() {
        findNavController().navigate(R.id.reasonFragment)
    }

}