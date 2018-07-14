package com.spaikergrn.vkclient.vkapi.vkapimodels

import io.reactivex.annotations.Nullable

interface VkImageAttachment {

    fun getImageWidth() : Int

    fun getImageHeight() : Int

    fun getMainPhotoForNews(@Nullable size : String) : String?

    fun getSmallPhotoForNews () : String?

}
