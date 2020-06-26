package com.rahamsolutions.booking.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.rahamsolutions.booking.web.rest.TestUtil;

public class BookedSlotsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookedSlots.class);
        BookedSlots bookedSlots1 = new BookedSlots();
        bookedSlots1.setId(1L);
        BookedSlots bookedSlots2 = new BookedSlots();
        bookedSlots2.setId(bookedSlots1.getId());
        assertThat(bookedSlots1).isEqualTo(bookedSlots2);
        bookedSlots2.setId(2L);
        assertThat(bookedSlots1).isNotEqualTo(bookedSlots2);
        bookedSlots1.setId(null);
        assertThat(bookedSlots1).isNotEqualTo(bookedSlots2);
    }
}
