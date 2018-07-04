package com.spaikergrn.vkclient.callable;

import com.spaikergrn.vkclient.Mocks;
import com.spaikergrn.vkclient.callablemodels.CallVkModelPhoto;
import com.spaikergrn.vkclient.clients.HttpUrlClient;
import com.spaikergrn.vkclient.clients.IHttpUrlClient;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.VkModelPhotoK;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallVkModelPhotoTest {

    private static final int CORRECT_ID = 456276227;
    private CallVkModelPhoto mCallVkModelPhoto;

    @Before
    public void setUp() throws IOException {
        initMocks(this);
        
        final IHttpUrlClient httpUrlClient = spy(new HttpUrlClient());
        Mockito.doReturn(Mocks.mockedStream(this,"photo_by_id.json")).when(httpUrlClient).getInputStream(anyString());

        mCallVkModelPhoto = spy(new CallVkModelPhoto(""));
        doReturn(httpUrlClient).when(mCallVkModelPhoto).getHttpClient();
        doReturn("").when(mCallVkModelPhoto).buildRequest();
    }

    @Test
    public void test() {
        final VkModelPhotoK modelPhotoK = mCallVkModelPhoto.call();

        assertTrue((modelPhotoK.getId()) == CORRECT_ID);
    }
}
