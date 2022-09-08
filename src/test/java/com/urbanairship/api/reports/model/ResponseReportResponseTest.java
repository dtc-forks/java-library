package com.urbanairship.api.reports.model;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseReportResponseTest {

    @Test
    public void testResponseObject() {
        DateTime date = new DateTime(2015, 10, 15, 12, 0, 0, 0);

        DeviceStats deviceStats = DeviceStats.newBuilder()
                .setInfluenced(1111)
                .setDirect(2222)
                .build();

        ResponseReportResponse responseReportResponse = ResponseReportResponse.newBuilder()
                .setDate(date)
                .addDeviceStatsMapping("ios", deviceStats)
                .addDeviceStatsMapping("android", deviceStats)
                .build();

        assertNotNull(responseReportResponse);
        assertEquals(1111, Objects.requireNonNull(responseReportResponse.getDeviceStatsMap().get().get("ios")).getInfluenced().get().intValue());
        assertEquals(2222, Objects.requireNonNull(responseReportResponse.getDeviceStatsMap().get().get("ios")).getDirect().get().intValue());
        assertEquals(1111, Objects.requireNonNull(responseReportResponse.getDeviceStatsMap().get().get("android")).getInfluenced().get().intValue());
        assertEquals(2222, Objects.requireNonNull(responseReportResponse.getDeviceStatsMap().get().get("android")).getDirect().get().intValue());
    }
}
