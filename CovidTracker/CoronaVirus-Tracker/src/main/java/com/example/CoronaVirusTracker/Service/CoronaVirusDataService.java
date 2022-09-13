package com.example.CoronaVirusTracker.Service;

import com.example.CoronaVirusTracker.models.LocationStats;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


@Service
public class CoronaVirusDataService
{

    private String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron="* * 1 * *")
    public void fetchVirusData() throws IOException, InterruptedException
    {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse= client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader= new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvBodyReader);
        int i=0;

        for (CSVRecord record : records)
        {
            if(i!=0) {
                LocationStats locationStats = new LocationStats();
                locationStats.setCountry(record.get(1));
                if(record.get(0).equals(""))
                {locationStats.setState(record.get(1));}
                else
                {locationStats.setState(record.get(0));}

                locationStats.setLatestTotal((record.get(record.size() - 1)));

                int current_date = Integer.parseInt(record.get(record.size() - 1));
                int previous_date = Integer.parseInt(record.get(record.size() - 2));
                locationStats.setDiffFromPrevDay(current_date - previous_date);
                newStats.add(locationStats);
            }
            i++;
        }
    this.allStats= newStats;
    }
}
