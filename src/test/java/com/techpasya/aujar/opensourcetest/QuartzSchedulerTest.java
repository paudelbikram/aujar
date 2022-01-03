package com.techpasya.aujar.opensourcetest;

import static org.junit.jupiter.api.Assertions.fail;
import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuartzSchedulerTest {

  @Test
  @DisplayName("Testing JobBuilder from Quartz-Scheduler open source project")
  public void testJobBuilder() {
    Aujar aujar = null;
    try {
      aujar = Aujar.build("org.quartz.JobBuilder", "org.quartz");
      aujar.save("test-data/jobbuilder.svg");
      assert (new File("test-data/jobbuilder.svg").exists());
    } catch (AujarException | IOException e) {
      e.printStackTrace();
      fail();
    }
  }

}
