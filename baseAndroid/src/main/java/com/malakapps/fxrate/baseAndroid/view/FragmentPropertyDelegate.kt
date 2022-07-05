package com.malakapps.fxrate.baseAndroid.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentPropertyDelegate<T>(
    fragment: Fragment,
    propertyFactory: () -> T
) : ReadOnlyProperty<Fragment, T> {
    private var viewAwareProperty: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                viewAwareProperty = it?.let { propertyFactory() }
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T = this.viewAwareProperty!!
}

fun <T> Fragment.viewAwareProperty(propertyFactory: () -> T) = FragmentPropertyDelegate(this, propertyFactory)
