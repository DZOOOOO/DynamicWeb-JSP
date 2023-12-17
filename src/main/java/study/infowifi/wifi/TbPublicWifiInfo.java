package study.infowifi.wifi;

import lombok.*;

@Getter
@Setter
public class TbPublicWifiInfo {
    private int list_total_count;
    private Result RESULT;
    private SeoulWifi[] row;
}


