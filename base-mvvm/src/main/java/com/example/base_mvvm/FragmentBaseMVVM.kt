package com.example.base_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class FragmentBaseMVVM<Viewbinding:ViewBinding, Viewmodel:BaseViewModel>: Fragment(){
    protected open lateinit var binding: Viewbinding
    abstract val viewModel: Viewmodel

    protected lateinit var navController: NavController

    abstract fun initViewBinding(): Viewbinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        observes()
        onView(savedInstanceState)
        onInitListeners()
    }

    protected open fun onInitListeners() {}

    protected open fun observes() {}

    protected fun <T> LiveData<T>.observeNonNull(action: (T) -> Unit){
        if(!this@FragmentBaseMVVM.isAdded) return
        this.observe(viewLifecycleOwner){
            it?.let { action.invoke(it) }
        }
    }

    abstract fun onView(savedInstanceState: Bundle?)

    open fun navigate(
        direction:Int?,
        bundle: Bundle? = null,
        animWay: NavigationWay = NavigationWay.NONE
    ){
        direction?.let { navController.navigate(it, bundle, setAnimations(animWay)) }
    }

    private fun setAnimations(navigationWay: NavigationWay):NavOptions{
        return NavOptions.Builder()
            .setLaunchSingleTop(true)
            .apply {
                when(navigationWay){
                    NavigationWay.NEXT -> {
                        setEnterAnim(R.anim.anim_slide_from_right)
                        setExitAnim(R.anim.anim_slide_to_left)
                        setPopEnterAnim(R.anim.anim_slide_from_left)
                        setPopExitAnim(R.anim.anim_slide_to_right)
                    }
                    NavigationWay.BACK -> {
                        setEnterAnim(R.anim.anim_slide_from_left)
                        setExitAnim(R.anim.anim_slide_to_right)
                        setPopEnterAnim(R.anim.anim_slide_from_right)
                        setPopExitAnim(R.anim.anim_slide_to_left)
                    }
                    NavigationWay.NONE -> {}
                }
            }.build()
    }
}