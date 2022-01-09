package com.thornbirds.repodemo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thornbirds.framework.fragment.ComponentFragment

/**
 * Created by yangli on 2017/9/7.
 */
class TestFragment : ComponentFragment() {

    override val TAG: String = "TestFragment"

    private var mId = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments != null) {
            mId = requireArguments().getInt(EXTRA_FRAGMENT_ID, hashCode())
        } else {
            Log.w(TAG, "onAttach arguments not found, fragment " + mId + ", hashCode=" + hashCode())
        }
        Log.d(TAG, "onAttach fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mId = requireArguments().getInt(EXTRA_FRAGMENT_ID, hashCode())
        } else {
            Log.w(TAG, "onCreate arguments not found, fragment " + mId + ", hashCode=" + hashCode())
        }
        Log.d(TAG, "onCreate fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView fragment " + mId + ", hashCode=" + hashCode())
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy fragment " + mId + ", hashCode=" + hashCode())
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach fragment " + mId + ", hashCode=" + hashCode())
    }

    companion object {
        const val EXTRA_FRAGMENT_ID = "extra_fragment_id"
    }
}