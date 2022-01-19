package com.aklymov.mynetdaity.feature_client_edit.view.step.image

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.aklymov.mynetdaity.feature_client_edit.R
import com.aklymov.mynetdaity.feature_client_edit.databinding.FragmentEditClientImageBinding
import com.aklymov.mynetdaity.feature_client_edit.view.step.BaseEditStepFragment
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.image.EditClientImageViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.x.viewmodel.viewModel
import org.kodein.di.bindProvider
import org.kodein.di.diContext
import java.io.File

internal class EditClientImageFragment : BaseEditStepFragment<FragmentEditClientImageBinding>() {

    override val diContext: DIContext<*> = diContext(this)
    override val di: DI by DI.lazy {
        extend((parentFragment as DIAware).di, copy = Copy.None)
        bindProvider {
            EditClientImageViewModel(parentViewModel = parentViewModel)
        }
    }

    override val layoutId: Int = R.layout.fragment_edit_client_image

    private val viewModel: EditClientImageViewModel by viewModel()

    private lateinit var pickPhotoActivityResult: ActivityResultLauncher<Intent>
    private lateinit var takePhotoActivityResult: ActivityResultLauncher<Intent>
    private var photoUri: Uri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickPhotoActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    viewModel.setUserImage(it.toString())
                }
            }
        }
        takePhotoActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.setUserImage(photoUri.toString())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchAndCollectOnStart {
            viewModel
                .currentImage
                .collectLatest {
                    Picasso
                        .get()
                        .load(it)
                        .placeholder(R.drawable.ic_client_photo_placeholder)
                        .into(binding.ivEditClientImage)
                }
        }

        binding.bEditClientImageSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhotoActivityResult.launch(intent)
        }
        binding.bEditClientImageTakePhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                val resultFile = File(
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "temp_%d.jpg".format(System.currentTimeMillis())
                )
                photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.aklymov.mynetdaity.fileprovider",
                    resultFile
                )
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            }
            takePhotoActivityResult.launch(intent)
        }
    }
}
