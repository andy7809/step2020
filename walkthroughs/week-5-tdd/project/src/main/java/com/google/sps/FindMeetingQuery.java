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

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    // If there are no attendees, there are no possible time ranges
    if (request.getAttendees().size() == 0) {
      return Collections.emptySet();
    }

    // Add all times to list.
    List<TimeRange> allBookedTimes = getAttendeeMeetings(request.getAttendees(), events);
    Collections.sort(allBookedTimes, TimeRange.ORDER_BY_START);

    return availableTimes;

  }

  /**
  * Returns a Collection of all the times that the requested attendees are in meetings.
  */
  public List<TimeRange> getAttendeeMeetings(Collection<String> requestAttendees, Collection<Event> allEvents) {
    List<TimeRange> allTimesInMeetings = new ArrayList<TimeRange>();
    for (Event event : allEvents) {
      for (String eventAttendee : event.getAttendees()) {
        // If this attendee of the event is a requested attendee, add event time to list
        if (requestAttendees.contains(eventAttendee)) {
          allTimesInMeetings.add(event.getWhen);
          // Break out of attendee loop so duplicates aren't added to list
          break;
        }
      }
    }
    return allTimesInMeetings;
  }
}
