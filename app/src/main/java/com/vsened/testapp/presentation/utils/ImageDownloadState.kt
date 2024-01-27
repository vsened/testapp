package com.vsened.testapp.presentation.utils

data class ImageDownloadState(
    val isDownload: Boolean = false,
    val isImageSaved: Boolean = false,
    val shouldShowDownloadToast: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null
)
