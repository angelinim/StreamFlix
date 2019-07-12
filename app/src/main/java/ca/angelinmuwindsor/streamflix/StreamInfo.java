package ca.angelinmuwindsor.streamflix;

import com.google.firebase.firestore.GeoPoint;

public class StreamInfo {
    private String streamTitle = "";
    private String streamDescription = "";
    private String streamId = "";
    private GeoPoint streamLocation = new GeoPoint(0,0);



    public StreamInfo() {
        //Empty constructor is needed for firebase
    }

    public StreamInfo(String streamTitle, String streamDescription) {
        this.streamTitle = streamTitle;
        this.streamDescription = streamDescription;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public String getStreamDescription() {
        return streamDescription;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public GeoPoint getStreamLocation() {
        return streamLocation;
    }

    public void setStreamLocation(GeoPoint streamLocation) {
        this.streamLocation = streamLocation;
    }
}
