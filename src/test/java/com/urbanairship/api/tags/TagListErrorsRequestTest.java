package com.urbanairship.api.tags;


import com.google.common.net.HttpHeaders;
import com.urbanairship.api.client.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TagListErrorsRequestTest {
    private static final String TEST_LIST_NAME = "testlist";
    private static final String TAG_LIST_ERRORS_PATH = "/api/tag-lists/" + TEST_LIST_NAME + "/errors";
    private static final String OUTPUT_FILE_PATH = "src/test/data/out.csv";

    TagListErrorsRequest request;
    TagListErrorsRequest requestWithFile;


    @Before
    public void setup() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE_PATH);
        request = TagListErrorsRequest.newRequest(TEST_LIST_NAME);
        requestWithFile = TagListErrorsRequest.newRequest(TEST_LIST_NAME)
            .setOutputStream(fileOutputStream);
    }

    @Test
    public void testContentType() throws Exception {
        assertNull(request.getContentType());
        assertNull(requestWithFile.getContentType());
    }

    @Test
    public void testMethod() throws Exception {
        assertEquals(request.getHttpMethod(), Request.HttpMethod.GET);
        assertEquals(requestWithFile.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testURI() throws Exception {
        URI baseURI = URI.create("https://go.urbanairship.com");
        URI expectedUri = URI.create("https://go.urbanairship.com" + TAG_LIST_ERRORS_PATH);

        assertEquals(request.getUri(baseURI), expectedUri);
        assertEquals(requestWithFile.getHttpMethod(), Request.HttpMethod.GET);
    }

    @Test
    public void testHeaders() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, Request.UA_VERSION_CSV);
        assertEquals(request.getRequestHeaders(), headers);
        assertEquals(requestWithFile.getRequestHeaders(), headers);
    }

    @Test
    public void testBody() throws Exception {
        assertNull(request.getRequestBody());
        assertNull(requestWithFile.getRequestBody());
    }

    @Test
    public void testParser() throws Exception {
        String response = "8b4de669-16f1-4e71-9a1f-0c62a8235a65,ERROR,\"Unknown channel\"\n" +
                "abcd,ERROR,\"Invalid msisdn\"\n";


        assertEquals(request.getResponseParser().parse(response), response);
        assertEquals(requestWithFile.getResponseParser().parse(response), response);

        File outFile = new File(OUTPUT_FILE_PATH);
        assertTrue(outFile.exists());

        BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(outFile.toPath())));
        StringBuilder fileString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            fileString.append(line).append("\n");
        }
        assertEquals(response, fileString.toString());
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_FILE_PATH));
    }

}
