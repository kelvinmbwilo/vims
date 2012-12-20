package org.openlmis.web.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.authentication.web.UserAuthenticationSuccessHandler;
import org.openlmis.core.domain.Product;
import org.openlmis.upload.RecordHandler;
import org.openlmis.upload.model.ModelClass;
import org.openlmis.upload.parser.CSVParser;
import org.openlmis.web.handler.UploadHandlerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UploadControllerTest {

    public static final String USER = "user";
    @Mock
    CSVParser csvParser;

    UploadController controller;

    @Mock
    RecordHandler handler;
    @Mock
    private UploadHandlerFactory uploadHandlerFactory;

    private MockHttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        Map<String, Class> modelMap = new HashMap<String, Class>(){{put("product", Product.class);}};
        when(uploadHandlerFactory.getHandler(any(String.class))).thenReturn(handler);
        request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(UserAuthenticationSuccessHandler.USER, USER);
        request.setSession(session);
        controller = new UploadController(csvParser , uploadHandlerFactory, modelMap);//, new HashMap<String, RecordHandler>());
    }

    @Test
    public void shouldThrowErrorIfUnsupportedModelIsSupplied() throws Exception {
        MultipartFile multipartFile = mock(MultipartFile.class);
        ResponseEntity<ModelMap> responseEntity = controller.upload(multipartFile, "Random", request);

        assertEquals("Incorrect file", responseEntity.getBody().get("error"));
    }

    @Test
    public void shouldThrowErrorIfFileIsEmpty() throws Exception {
        byte[] content = new byte[0];
        MockMultipartFile multiPartMock = new MockMultipartFile("csvFile", "mock.csv", null, content);
        ResponseEntity<ModelMap> responseEntity = controller.upload(multiPartMock, "product", request);

        assertEquals("File is empty", responseEntity.getBody().get("error"));
    }

    @Test
    public void shouldParseIfFileIsCsv() throws Exception {
        byte[] content = new byte[1];
        MockMultipartFile multiPartMock = new MockMultipartFile("csvFile", "mock.csv", null, content);

        ResponseEntity<ModelMap> responseEntity = controller.upload(multiPartMock, "product", request);

        assertEquals("File uploaded successfully. Total records uploaded: 0", responseEntity.getBody().get("message"));
    }

    @Test
    public void shouldUseCsvParserService() throws Exception {
        byte[] content = new byte[1];
        MultipartFile mockMultiPart = spy(new MockMultipartFile("csvFile", "mock.csv", null, content));

        InputStream mockInputStream = mock(InputStream.class);
        when(mockMultiPart.getInputStream()).thenReturn(mockInputStream);

        ResponseEntity<ModelMap> responseEntity = controller.upload(mockMultiPart, "product", request);

        assertEquals("File uploaded successfully. Total records uploaded: 0", responseEntity.getBody().get("message"));

        verify(csvParser).process(eq(mockMultiPart.getInputStream()), argThat(modelMatcher(Product.class)), eq(handler), eq(USER));
    }

    private ArgumentMatcher<ModelClass> modelMatcher(final Class clazz) {
        return new ArgumentMatcher<ModelClass>(){
            @Override
            public boolean matches(Object item) {
                ModelClass modelClass = (ModelClass)item;
                return  modelClass.getClazz().equals(clazz);
            }
        };
    }

    @Test
    public void shouldGiveErrorIfFileNotOfTypeCsv() throws Exception {
        byte[] content = new byte[1];
        MockMultipartFile multiPartMock = new MockMultipartFile("mock.doc", content);

        ResponseEntity<ModelMap> responseEntity = controller.upload(multiPartMock, "product", request);
        assertEquals("Incorrect file format , Please upload product data as a \".csv\" file", responseEntity.getBody().get("error"));
//        verify(csvParser).process(mockedStream, Product.class, handler);
    }

}
