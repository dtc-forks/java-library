package com.urbanairship.api.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.channel.model.ChannelResponse;
import com.urbanairship.api.channel.model.ChannelType;
import com.urbanairship.api.channel.model.ChannelView;
import com.urbanairship.api.nameduser.model.NamedUserListingResponse;
import com.urbanairship.api.nameduser.model.NamedUserView;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.PushResponse;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notification;
import com.urbanairship.api.reports.model.PushInfoResponse;
import com.urbanairship.api.reports.model.PushListingResponse;
import com.urbanairship.api.schedule.model.ListAllSchedulesResponse;
import com.urbanairship.api.schedule.model.Schedule;
import com.urbanairship.api.schedule.model.SchedulePayloadResponse;
import com.urbanairship.api.schedule.model.ScheduleResponse;
import com.urbanairship.api.segments.model.SegmentListingResponse;
import com.urbanairship.api.segments.model.SegmentListingView;
import com.urbanairship.api.segments.model.SegmentView;
import com.urbanairship.api.staticlists.model.StaticListListingResponse;
import com.urbanairship.api.staticlists.model.StaticListView;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ResponseTest {

    private final String CONTENT_TYPE_KEY = "content-type";
    private final String CONTENT_TYPE = "application/json";


    private final Map<String, String> headers = new HashMap<>();

    @Before
    public void setUp() {
        headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE);
    }

    @Test
    public void testPushResponse() {
        PushResponse pushResponse = PushResponse.newBuilder()
                .setOk(true)
                .addAllPushIds(Arrays.asList("ID1", "ID2"))
                .setOperationId("OpID")
                .build();

        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Response<PushResponse> response = new Response<>(pushResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertEquals("HTTP response body not set properly", response.getBody().get(), pushResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIScheduleResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ScheduleResponse scheduleResponse = ScheduleResponse.newBuilder()
                .setOk(true)
                .addAllScheduleUrls(Arrays.asList("ID1", "ID2"))
                .setOperationId("ID")
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Response<ScheduleResponse> response = new Response<>(scheduleResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertEquals("HTTP response body not set properly", response.getBody().get(), scheduleResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIListAllSchedulesResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        SchedulePayloadResponse sample = SchedulePayloadResponse.newBuilder()
                .setSchedule(Schedule.newBuilder()
                        .setScheduledTimestamp(DateTime.now())
                        .build())
                .setPushPayload(PushPayload.newBuilder()
                        .setAudience(Selectors.all())
                        .setNotification(Notification.newBuilder()
                                .setAlert("UA Push")
                                .build())
                        .setDeviceTypes(DeviceTypeData.of(DeviceType.IOS))
                        .build())
                .setUrl("http://sample.com/")
                .build();

        ListAllSchedulesResponse listScheduleResponse = ListAllSchedulesResponse.newBuilder()
                .setOk(true)
                .setCount(5)
                .setTotalCount(6)
                .addSchedule(sample)
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Response<ListAllSchedulesResponse> response = new Response<>(listScheduleResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertEquals("HTTP response body not set properly", response.getBody().get(), listScheduleResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIChannelViewResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ChannelResponse channelResponse =
                ChannelResponse.newBuilder()
                        .setOk(true)
                        .setChannelObject(ChannelView.newBuilder()
                                .setAlias("Alias")
                                .setBackground(true)
                                .setChannelId("channelID")
                                .setCreated(DateTime.now())
                                .setChannelType(ChannelType.ANDROID.getIdentifier())
                                .setInstalled(true)
                                .setLastRegistration(DateTime.now().minus(12345L))
                                .setOptIn(true)
                                .setPushAddress("PUSH")
                                .build())
                        .build();

        Response<ChannelResponse> response = new Response<>(channelResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertEquals("HTTP response body not set properly", response.getBody().get(), channelResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIListChannelsResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        ChannelResponse channelResponse = ChannelResponse.newBuilder()
                .setOk(true)
                .setNextPage("nextPage")
                .addChannel(ChannelView.newBuilder()
                        .setAlias("Alias")
                        .setBackground(true)
                        .setChannelId("channelID")
                        .setCreated(DateTime.now())
                        .setChannelType(ChannelType.ANDROID.getIdentifier())
                        .setInstalled(true)
                        .setLastRegistration(DateTime.now().minus(12345L))
                        .setOptIn(true)
                        .setPushAddress("PUSH")
                        .build())
                .build();

        Response<ChannelResponse> response = new Response<>(channelResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertEquals("HTTP response body not set properly", response.getBody().get(), channelResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testNamedUserListingResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        NamedUserListingResponse namedUserListingResponse = NamedUserListingResponse.newBuilder()
                .setOk(true)
                .setNamedUserView(NamedUserView.newBuilder()
                        .setChannelViews(ImmutableSet.of(ChannelView.newBuilder()
                                .setAlias("Alias")
                                .setBackground(true)
                                .setChannelId("channelID")
                                .setCreated(DateTime.now())
                                .setChannelType(ChannelType.ANDROID.getIdentifier())
                                .setInstalled(true)
                                .setLastRegistration(DateTime.now().minus(12345L))
                                .setOptIn(true)
                                .setPushAddress("PUSH")
                                .build()))
                        .build())
                .build();

        Response<NamedUserListingResponse> response = new Response<>(namedUserListingResponse, headers, httpResponse.getStatusLine().getStatusCode());
        assertEquals("HTTP response body not set properly", response.getBody().get(), namedUserListingResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIReportsListingResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        PushInfoResponse spir = PushInfoResponse.newBuilder()
                .setPushId(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(PushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupId(two)
                .build();

        PushListingResponse pushListingResponse = PushListingResponse.newBuilder()
                .setNextPage("123")
                .addPushInfoObject(spir)
                .addPushInfoObject(spir)
                .addPushInfoObject(spir)
                .build();

        Response<PushListingResponse> response = new Response<>(
                pushListingResponse,
                headers,
                httpResponse.getStatusLine().getStatusCode());

        assertEquals("HTTP response not set properly", response.getBody().get(), pushListingResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testListIndividualPushAPIResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();

        PushInfoResponse pushInfoResponse = PushInfoResponse.newBuilder()
                .setPushId(one)
                .setDirectResponses(4)
                .setSends(5)
                .setPushType(PushInfoResponse.PushType.UNICAST_PUSH)
                .setPushTime("2013-07-31 21:27:38")
                .setGroupId(two)
                .build();

        Response<PushInfoResponse> response = new Response<>(
                pushInfoResponse,
                headers,
                httpResponse.getStatusLine().getStatusCode());

        assertEquals("HTTP response not set properly", response.getBody().get(), pushInfoResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());

    }

    @Test
    public void testAudienceSegmentResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        Selector andSelector = Selectors.tags("java", "lib");
        Selector compound = Selectors.or(andSelector, Selectors.not(Selectors.tag("mfd")));

        SegmentView segment = SegmentView.newBuilder()
                .setDisplayName("hello")
                .setCriteria(compound)
                .build();

        Response<SegmentView> response = new Response<>(
                segment,
                headers,
                httpResponse.getStatusLine().getStatusCode());

        assertEquals("HTTP response not set properly", response.getBody().get(), segment);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testAPIListAllSegmentsResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        SegmentListingView listItem = SegmentListingView.newBuilder()
                .setCreationDate(123L)
                .setDisplayName("DisplayName")
                .setId("Id")
                .setModificationDate(321L)
                .build();

        ImmutableList<SegmentListingView> list = ImmutableList.<SegmentListingView>builder()
                .add(listItem)
                .add(listItem)
                .add(listItem)
                .build();

        SegmentListingResponse segments = SegmentListingResponse.newBuilder()
                .setNextPage("NextPage")
                .addAllSegmentObjects(list)
                .build();

        Response<SegmentListingResponse> response = new Response<>(
                segments,
                headers,
                httpResponse.getStatusLine().getStatusCode());

        assertEquals("HTTP response not set properly", response.getBody().get(), segments);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testStaticListLookupResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        StaticListView staticListView = StaticListView.newBuilder()
                .setOk(true)
                .setName("static_list_name")
                .setDescription("a great list")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setStatus("processing")
                .build();

        Response<StaticListView> response = new Response<>(
                staticListView,
                headers,
                httpResponse.getStatusLine().getStatusCode());

        assertEquals("HTTP response not set properly", response.getBody().get(), staticListView);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testStaticListListingResponse() {
        HttpResponse httpResponse = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
        httpResponse.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);

        DateTime created = new DateTime(2014, 10, 1, 12, 0, 0, 0);
        DateTime updated = created.plus(Period.hours(48));

        StaticListView res1 = StaticListView.newBuilder()
                .setName("static_list_name")
                .setCreated(created)
                .setChannelCount(1234)
                .setLastUpdated(updated)
                .setStatus("ready")
                .build();

        StaticListView res2 = StaticListView.newBuilder()
                .setName("static_list_name")
                .setDescription("a great list")
                .setCreated(created)
                .setLastUpdated(updated)
                .setChannelCount(1234)
                .setStatus("processing")
                .build();

        StaticListListingResponse staticListListingResponse = StaticListListingResponse.newBuilder()
                .setOk(true)
                .addStaticList(res1)
                .addStaticList(res2)
                .build();

        Response<StaticListListingResponse> response = new Response<>(
                staticListListingResponse,
                headers,
                httpResponse.getStatusLine().getStatusCode());

        assertEquals("HTTP response not set properly", response.getBody().get(), staticListListingResponse);
        assertEquals("HTTP response headers not set properly", response.getHeaders(), headers);
        assertEquals("HTTP response status not set properly", response.getStatus(), httpResponse.getStatusLine().getStatusCode());

    }

}
