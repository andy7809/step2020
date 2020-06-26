// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // If there are no attendees, the possible meeting time is all day
    if (request.getAttendees().size() == 0) {
      return Arrays.asList(TimeRange.WHOLE_DAY);
    }

    // Add all booked times to a list.
    List<TimeRange> requiredBookedTimes = getAttendeeMeetings(request.getAttendees(), events);
    Collections.sort(requiredBookedTimes, TimeRange.ORDER_BY_START);
    Collection<TimeRange> requiredAvailableTimes = getAvailableTimes(requiredBookedTimes, request.getDuration());
    if (request.getOptionalAttendees().size() == 0) {
      return requiredAvailableTimes;
    }

    List<TimeRange> optionalBookedTimes = getAttendeeMeetings(request.getOptionalAttendees(), events);
    return null;
  }

  private Collection<TimeRange> getAvailableTimes(List<TimeRange> bookedTimes, long duration) {
    /* Algorithm: have a start time and end time. The start time begins as the beginning of the day,
       while the end time begins as 0 or null. Then for every time range in the unavailable meetings,
       set end time to the start of the unavailable time. If the time between start and end can fit
       the requested duration, add it to the return collection. The start time becomes the end time
       of this TimeRange.

       To handle overlapping unavailable times, only run the loop body if the start time is less than
       the end time of this TimeRange.
    */
    int availableStartTime = TimeRange.START_OF_DAY;
    int availableEndTime = 0;
    Collection<TimeRange> availableTimes = new ArrayList<>();

    for (TimeRange unavailableTime : bookedTimes) {
      if (availableStartTime < unavailableTime.end()) {
        availableEndTime = unavailableTime.start();
        if (availableEndTime - availableStartTime >= duration) {
          availableTimes.add(TimeRange.fromStartEnd(availableStartTime, availableEndTime, false));
        }
        availableStartTime = unavailableTime.end();
      }
    }
    // Check the time from the current end time to the end of the day, as it isn't checked in the loop
    if (TimeRange.END_OF_DAY - availableStartTime >= duration) {
      availableTimes.add(TimeRange.fromStartEnd(availableStartTime, TimeRange.END_OF_DAY, true));
    }
    return availableTimes;
  }

  /**
  * Returns a List of all the times that the requested attendees are in meetings.
  */
  private List<TimeRange> getAttendeeMeetings(Collection<String> requestAttendees, Collection<Event> allEvents) {
    List<TimeRange> allTimesInMeetings = new ArrayList<TimeRange>();
    for (Event event : allEvents) {
      for (String eventAttendee : event.getAttendees()) {
        // If this attendee of the event is a requested attendee, add event time to list
        if (requestAttendees.contains(eventAttendee)) {
          allTimesInMeetings.add(event.getWhen());
          // Break out of attendee loop so duplicates aren't added to list
          break;
        }
      }
    }
    return allTimesInMeetings;
  }
}
