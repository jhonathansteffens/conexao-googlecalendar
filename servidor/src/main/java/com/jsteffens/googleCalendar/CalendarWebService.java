package com.jsteffens.googleCalendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
@RequestMapping(value = "calendario")
public class CalendarWebService implements Serializable {

    @ResponseBody
    @GetMapping("getEvents")
    String getAllEvents() {
        List<Event> events = new ArrayList<>();
        StringBuilder eventList = new StringBuilder();
        try {
            events = GoogleCalendar.getEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Event event : events) {
            DateTime start = event.getStart().getDateTime();
            String dataFormatada;
            if (start == null) {
                start = event.getStart().getDate();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy ");
                dataFormatada = formatador.format(start);
            } else {
                String data = start.toStringRfc3339();
                dataFormatada = data.substring(8, 10) + "/" + data.substring(5, 7) + "/" + data.substring(0, 4) + " " + data.substring(11, 19);
            }
            eventList.append(event.getSummary()).append("\n");
            eventList.append(dataFormatada).append("\n");
        }
        return eventList.toString();
    }
}