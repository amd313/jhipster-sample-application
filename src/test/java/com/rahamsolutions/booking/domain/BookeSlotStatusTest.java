package com.rahamsolutions.booking.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.rahamsolutions.booking.web.rest.TestUtil;

public class BookeSlotStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookeSlotStatus.class);
        BookeSlotStatus bookeSlotStatus1 = new BookeSlotStatus();
        bookeSlotStatus1.setId(1L);
        BookeSlotStatus bookeSlotStatus2 = new BookeSlotStatus();
        bookeSlotStatus2.setId(bookeSlotStatus1.getId());
        assertThat(bookeSlotStatus1).isEqualTo(bookeSlotStatus2);
        bookeSlotStatus2.setId(2L);
        assertThat(bookeSlotStatus1).isNotEqualTo(bookeSlotStatus2);
        bookeSlotStatus1.setId(null);
        assertThat(bookeSlotStatus1).isNotEqualTo(bookeSlotStatus2);
    }
}
