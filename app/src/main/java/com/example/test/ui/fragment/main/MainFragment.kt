package com.example.test.ui.fragment.main

import android.os.Bundle
import android.widget.RadioGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.example.base_mvvm.FragmentBaseMVVM
import com.example.test.R
import com.example.test.databinding.FragmentMainBinding
import com.example.test.ui.adapter.MainAdapterV2
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : FragmentBaseMVVM<FragmentMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModel()
    private val adapter by lazy {
        MainAdapterV2()
    }

    override fun initViewBinding(): FragmentMainBinding =
        FragmentMainBinding.inflate(layoutInflater)

    override fun onView(savedInstanceState: Bundle?) {
        viewModel.getUser()
        viewModel.getPetrols()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context).apply { orientation = HORIZONTAL }
        adapter.onItemChanged = { old, new ->
            viewModel.getChangedList(new, binding.totalLitersEt.text.toString())?.let {
                it[new].price?.let { it1 ->
                    validate(it1)
                }
                adapter.setData(
                    it
                )
            }
            adapter.notifyItemChanged(old)
            adapter.notifyItemChanged(new)
        }
    }

    private fun validate(price: Int) {
        if (!viewModel.validate(price)) {
            binding.totalLitersEt.error = "Not enough funds"
            binding.confirmButton.isEnabled = false
        } else {
            binding.totalLitersEt.error = null
            binding.confirmButton.isEnabled = true
        }
    }

    override fun onInitListeners() {
        binding.accumulativeBtn.performClick()
        binding.accumulativeBtn.setOnCheckedChangeListener { _, b ->
            if (b) {
                viewModel.paymentMethod = MainViewModel.Payments.ACCUMULATIVE
                adapter.selectedItem?.let { adapter.items[it].price?.let { price -> validate(price) } }
            }
        }
        binding.paymentBtn.setOnCheckedChangeListener { _, b ->
            if (b) {
                viewModel.paymentMethod = MainViewModel.Payments.PAYMENT
                adapter.selectedItem?.let { adapter.items[it].price?.let { price -> validate(price) } }
            }
        }
        binding.confirmButton.setOnClickListener {
            adapter.selectedItem?.let {
                adapter.items[it].price?.let { it1 ->
                    viewModel.onConfirmClicked(
                        it1
                    )
                }
            }
        }
        binding.totalLitersEt.doAfterTextChanged { text ->
            adapter.selectedItem?.let {
                viewModel.getChangedList(it, text.toString())?.let { it1 ->
                    adapter.setData(it1)
                    it1[it].price?.let { validate(it) }
                }
                adapter.notifyItemChanged(it)
            }
        }
    }

    override fun observes() {
        with(viewModel) {
            petrols.observeNonNull {
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
            user.observeNonNull { it ->
                adapter.selectedItem?.let {
                    adapter.items[it].price?.let { it1 ->
                        this@MainFragment.validate(it1)
                    }
                }
                binding.name.text = it.name
                binding.email.text = it.email
                binding.accumulativeValue.text =
                    context?.getString(R.string.x_dollar, it.accumulativeBalance.toString())
                binding.paymentValue.text =
                    context?.getString(R.string.x_dollar, it.paymentBalance.toString())
            }
        }
    }
}