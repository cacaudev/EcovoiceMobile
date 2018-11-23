package Base.Data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class DataImageTree implements Serializable {

    String uri;

    public DataImageTree(String s) {

      uri = s;

    }
    public DataImageTree(){}
    public Uri getUriFromString(){
        return  Uri.parse(String.valueOf(uri));

    }
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}

