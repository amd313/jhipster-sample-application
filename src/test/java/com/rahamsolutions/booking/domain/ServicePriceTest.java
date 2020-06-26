package com.rahamsolutions.booking.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.rahamsolutions.booking.web.rest.TestUtil;

public class ServicePriceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicePrice.class);
        ServicePrice servicePrice1 = new ServicePrice();
        servicePrice1.setId(1L);
        ServicePrice servicePrice2 = new ServicePrice();
        servicePrice2.setId(servicePrice1.getId());
        assertThat(servicePrice1).isEqualTo(servicePrice2);
        servicePrice2.setId(2L);
        assertThat(servicePrice1).isNotEqualTo(servicePrice2);
        servicePrice1.setId(null);
        assertThat(servicePrice1).isNotEqualTo(servicePrice2);
    }
}
