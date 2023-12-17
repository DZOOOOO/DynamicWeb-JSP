// 와이파이 위치 정보
let wifiInfoList = [];
let wifiStr = "";
let dataLength = 0;

let LAT = document.getElementById("LAT").value;
let LNT = document.getElementById("LNT").value;

// 내 위치 가져오기
function findMyLocation() {
    navigator.geolocation.getCurrentPosition((position) => {
        console.log(position)
        let lat = position.coords.latitude;
        let lnt = position.coords.longitude;
        LAT = lat;
        LNT = lnt;
    })
}

// 근처 Wifi 정보 호출
function findWifi(LAT, LNT) {
    axios.get(`find-wifi?LAT=${LAT}&LNT=${LNT}`)
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
}


// Open API 와이파이 정보 가져오기
const getWifi = async () => {
    for (let i = 0; i < 24; i++) {
        let start = 1 + (1000 * i);
        let end = 1000 * (i + 1);

        // API 호출(비동기 처리)
        await axios.get(`http://openapi.seoul.go.kr:8088/5474586571647a703131377862576e55/json/TbPublicWifiInfo/${start}/${end}/`)
            .then(function (response) {
                dataLength = response.data.TbPublicWifiInfo.list_total_count;
                let data = response.data.TbPublicWifiInfo.row;
                let len = data.length;
                console.log("데이터 넣는중...");
                for (let i = 0; i < len; i++) {
                    wifiInfoList.push((data[i]));
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
}

// 와이파이 정보 저장 API 호출
const saveData = async () => {
    await getWifi();
    await axios.post('/hello-servlet', {
        wifiStr: wifiStr,
        dataLength: dataLength
    })
        .then(function (response) {
            console.log(response.state);
        })
        .catch(function (error) {
            console.log(error);
        });
}