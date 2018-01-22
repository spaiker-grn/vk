package com.spaikergrn.vkclient.vkapi.vkapimodels;

import java.io.Serializable;
import java.util.List;

public interface VkAttachmentsI extends Serializable {

    List<VkModelAttachmentsI> getAttachmentsList();

}
