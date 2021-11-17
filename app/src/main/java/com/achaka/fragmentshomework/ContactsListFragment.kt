package com.achaka.fragmentshomework

import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.achaka.fragmentshomework.databinding.FragmentContactsListBinding

const val CONTACT_NAME_KEY = "com.achaka.fragmentshomework.contactName"
const val CONTACT_PHONE_NUMBER_KEY = "com.achaka.fragmentshomework.contactPhoneNumber"
private const val ARG_PARAM2 = "param2"

class ContactsListFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {


    private val contactsMap = hashMapOf<Long, Contact>()
    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Contacts.NAME_RAW_CONTACT_ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,

        )

    private lateinit var binding: FragmentContactsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().requestPermissions(arrayOf("android.permission.READ_CONTACTS"), 0)
        }
        LoaderManager.getInstance(this).initLoader(0, null, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun addNewContacts(contacts: HashMap<Long, Contact>) {
        val linearLayout = binding.linearLayoutContactsList
        val scale = resources.displayMetrics.density
        val paddingDp = resources.getDimension(R.dimen.padding).toInt()
//        val textSizeSp = (resources.getDimension(R.dimen.padding)-0.5f)/scale

        for ((id, contact) in contacts) {
            val textView = TextView(requireContext())
            textView.text = contact.name + " " + id
            textView.textSize = 18f
            textView.setPadding(0, paddingDp, 0, 0)
            textView.setOnClickListener {
                val args = Bundle()
                args.putString(CONTACT_NAME_KEY, contact.name)
                args.putString(CONTACT_PHONE_NUMBER_KEY, contact.phoneNumber)
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.contacts_fragment_container_view,
                        ContactDetailsFragment::class.java,
                        args
                    )
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
            linearLayout.addView(textView)
        }

    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val cursorLoader = CursorLoader(
            requireContext(),
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            PROJECTION,
            null,
            null,
            null
        )

        return cursorLoader
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        while (data != null && data.moveToNext()) {
            val id = data.getLong(0)
            val name = data.getString(1)
            val phoneNumber = data.getString(2)
            Log.d("ID", id.toString())
            Log.d("Phone", phoneNumber)
            Log.d("Name", name)
            contactsMap[id] = Contact(name, phoneNumber)
        }
        addNewContacts(contactsMap)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactsListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsListFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    inner class ClickListener(val contact: Contact) :
        View.OnClickListener {
        override fun onClick(p0: View?) {
            val args = Bundle()
            args.putString(CONTACT_NAME_KEY, contact.name)
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.contacts_fragment_container_view,
                    ContactDetailsFragment::class.java,
                    args
                )
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }

    }
}