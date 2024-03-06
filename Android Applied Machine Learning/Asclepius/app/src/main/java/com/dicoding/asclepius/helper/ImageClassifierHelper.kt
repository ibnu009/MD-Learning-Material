package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import timber.log.Timber
import java.text.NumberFormat


class ImageClassifierHelper(
    private var threshold: Float = 0.2f,
    private var maxResults: Int = 3,
    private val modelName: String = "cancer_classification.tflite",
    private val context: Context,
    private val onSuccess: (String, String) -> Unit,
    private val onFailed: (String) -> Unit,
) {

    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            onFailed("Terjadi Kesalahan")
            Timber.tag("ImageClassifierHelper").e(e.message.toString())
        }
    }

    fun classifyStaticImage(context: Context, imageUri: Uri) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.UINT8))
            .build()

        val tensorImage =
            imageProcessor.process(TensorImage.fromBitmap(uriToBitmap(context, imageUri)))

        val imageProcessingOptions = ImageProcessingOptions.builder()
            .build()

        val results = imageClassifier?.classify(tensorImage, imageProcessingOptions)
        generateResult(results)
    }

    private fun generateResult(data: List<Classifications>?) {
        data?.let { it ->
            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                println(it)
                val highestResult =
                    it[0].categories.maxBy {
                        it?.score ?: 0.0f
                    }

                val label = highestResult.label
                val confidenceScore = NumberFormat.getPercentInstance()
                    .format(highestResult.score)

                onSuccess(label, confidenceScore)
            }
        }
    }

    private fun uriToBitmap(context: Context, imageUri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(imageUri)?.use { input ->
                BitmapFactory.decodeStream(input)
            }
        } catch (e: Exception) {
            null
        }
    }

}