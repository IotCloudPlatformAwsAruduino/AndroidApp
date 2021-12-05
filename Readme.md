
#💡스마트 에어컨시스템 

**[💦]**  
1인가구 주거공간의 여름철 온습도를 IoT 기기와 AWS 서비스를 이용한 IoT 클라우드 플랫폼을 구축하여 자동으로 제어하는 시스템 구축

___
## 적용된 AWS 서비스 
  <li>AWS IoT Core</li>
  <li>AWS Lambda</li>
  <li>Amazon DyanmoDB</li>
  <li>Amazon API Gateway</li>
  
## HW 구성
  <li>MKRWiFi1010 보드</li>
  <li>SG90 서보모터 : D3 </li>
  <li>DHT11 온도 습도 센서 : D2</li>
  <li>TEMT6000 조도센서 : A1</li>
  
  ![image](https://user-images.githubusercontent.com/72599051/144745942-1e4392ea-9d46-4c16-b18a-196ae228a6a1.png)

  
___ 

## 서비스 구성도
![image](https://user-images.githubusercontent.com/72599051/143241035-e962d196-8519-418c-a038-5cf6dc6108d7.png)
___ 


1. REST API 설계
다음 프로그램 코드를 실행시키기 위해서는 다음 형식의 REST API가 준비되어 있어야 합니다.
- 디바이스 로그 조회 
```
 GET /devices/{deviceId}/log?from=yyyy-mm-dd hh:mm:ss&to=yyyy-mm-dd hh:mm:ss
```
- 디바이스 상태 조회 : 온습도 , 에어컨 상태 확인(ON/OFF)
```
GET /devices/{deviceId}
```
- 디바이스 상태 변경 : 에어컨 ON/OFF
```
 PUT /devices/{deviceId}
```
  <li>message body (payload)</li>
  ```
  { 
 	"tags" : [
 		{
 			"attrName": "temperature",
 			"attrValue": "27.0"
 		},
 		{
 			"attrName": "LED",
 			"attrValue": "OFF"
 		}
 	]
 }
```

### Prerequisites / 선행 조건

아래 사항들이 설치가 되어있어야합니다.

```
예시
```

### Installing / 설치

아래 사항들로 현 프로젝트에 관한 모듈들을 설치할 수 있습니다.

```
예시
```

## Running the tests / 테스트의 실행

어떻게 테스트가 이 시스템에서 돌아가는지에 대한 설명을 합니다

### 테스트는 이런 식으로 동작합니다

왜 이렇게 동작하는지, 설명합니다

```
예시
```

### 테스트는 이런 식으로 작성하시면 됩니다

```
예시
```

## Deployment / 배포

Add additional notes about how to deploy this on a live system / 라이브 시스템을 배포하는 방법

## Built With / 누구랑 만들었나요?

* [1791320_최종현](https://github.com/TonyJHC) - 작업 내용
* [1771391 유지만](https://github.com/jiman-you) - 작업 내용 

## Contributiong / 기여

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us. / [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) 를 읽고 이에 맞추어 pull request 를 해주세요.

## License / 라이센스

This project is licensed under the MIT License - see the [LICENSE.md](https://gist.github.com/PurpleBooth/LICENSE.md) file for details / 이 프로젝트는 MIT 라이센스로 라이센스가 부여되어 있습니다. 자세한 내용은 LICENSE.md 파일을 참고하세요.

## Acknowledgments / 감사의 말

* Hat tip to anyone whose code was used / 코드를 사용한 모든 사용자들에게 팁
* Inspiration / 영감
* etc / 기타
