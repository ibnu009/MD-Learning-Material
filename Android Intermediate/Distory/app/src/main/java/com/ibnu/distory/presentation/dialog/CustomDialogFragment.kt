package com.ibnu.distory.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ibnu.distory.databinding.LayoutCustomDialogBinding
import com.ibnu.distory.utils.constant.AppConstants.KEY_ON_CLICK
import com.ibnu.distory.utils.helper.popClick

class CustomDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(
            onBtnClick: () -> Unit
        ): CustomDialogFragment = CustomDialogFragment().apply {
            arguments = Bundle().apply { }
            this.onBtnClick = onBtnClick
        }
    }

    private var onBtnClick: (() -> Unit)? = null

    private var _binding: LayoutCustomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutCustomDialogBinding.inflate(inflater, container, false)

        dialog?.apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAction()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initAction() {
        binding.apply {
            val onClickCallBack: () -> Unit = arguments?.getSerializable(KEY_ON_CLICK) as () -> Unit
            btnDialog.popClick {
                onClickCallBack.invoke()
                dismiss()
            }
            btnClose.popClick {
                dismiss()
            }
        }
    }
}