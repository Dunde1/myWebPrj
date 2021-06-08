# myWebPrj
version : 2.0

외부 웹 주소 : http://mit4.iptime.org:3380/

안녕하세요. 제 프로젝트에 오신걸 환영합니다.

이 프로젝트는 학원에서 진행한 프로젝트와 별개로 개인적으로 만들어진 프로젝트입니다.

아무렇게 사용하거나 테스트 해보셔도 상관 없습니다. 마음껏 이용해 주세요.

이 프로젝트는 JAVA, Spring boot, Jpa, mariaDB, mustache를 이용해 만들어졌습니다.

## 현재 사파리 브라우저에서 코멘트가 제대로 보이지 않는 현상이 있습니다.

* 기능별 상세설명 바로가기
  * [게시판](#게시판-기능-10)
  * [로그인](#로그인-기능-10)
  * [테스트](#테스트-기능-101)
  * [추가기능](#여러-추가-기능-102)
  * [가상 비트코인 거래소](#가상-비트코인-거래소-기능-20)
---
## 상세 기능 설명

---
### 메인 0.0
#### 메인화면(index)에 대한 내용 입니다.
#### 코드
* [(컨트롤러) IndexController](src/main/java/org/me/springboot/web/IndexController.java)
  * 메인 화면에서 로그인, 게시판 보기, api 테스트페이지 보기, 로또추첨 보기, 가상비트코인 보기 등을 할 수 있게 페이지 이동을 도와주는 컨트롤러 입니다.

#### 데이터베이스 테이블 공통
BaseTimeEntity
* create_date(DATETIME [6]) : 테이블 행 생성 일시 저장.
* modified_date(DATETIME [6]) : 테이블 행 수정 일시 저장.
---
### 게시판 기능 1.0
#### 로그인이 필요한 기능입니다.
* 게시글 보기
* 게시글 검색 및 페이지 이동
* 게시글 수정, 삭제 (작성자 본인만 가능)
* 댓글 쓰기
* 댓글 수정, 삭제 (작성자 본인만 가능)

#### 코드 참고
* [(컨트롤러) PostsApiController](src/main/java/org/me/springboot/web/PostsApiController.java)
  * 게시글의 읽기, 쓰기, 수정, 삭제와 코멘트의 쓰기, 수정, 삭제를 담당하는 컨트롤러 입니다.
* [(서비스) Service](src/main/java/org/me/springboot/service/posts)
  * 게시판의 서비스를 담당하는 폴더입니다. 게시글서비스(PostsService)와 코멘트서비스(CommentsService)가 나누어져 있습니다. 
* [(도메인) Domain](src/main/java/org/me/springboot/domain/posts)
  * 게시판의 도메인을 담당하는 폴더입니다. 게시글(Posts)와 코멘트(Comments)로 나누어져 있습니다.
  
#### 사용한 데이터베이스 테이블
Posts
* id(_BIGINT [20] [AUTO_INCREMENT, 기본키]_) : 게시글 ID
* author(_VARCHAR [255]_) : 게시글 작성자
* comments(_INT [11]_) : 코멘트 갯수, 현재 미사용으로 추후 업데이트 예정입니다.
* content(_TEXT [65535]_) : 게시글 내용
* title(_VARCHAR [500]_) : 게시글 제목

Comments
* post_id(_BIGINT [20] [외래키(Posts), 복합키]_) : 게시글 ID 
* step(_INT [11] [복합키]_) : 해당 게시글의 코멘트 순번 
* author(_VARCHAR [255]_) : 코멘트 작성자
* comment(_VARCHAR [100]_) : 코멘트 내용

---
### 로그인 기능 1.0
* 구글로그인, 네이버로그인(개발 단계라 개발자만 가능)
  * 추후 다른 로그인 기능도 넣을 수 있음.
* 로그아웃
* 우측 상단에 닉네임과 프로필사진 표시

#### 코드 참고
* [(OAuth) auth](src/main/java/org/me/springboot/config/auth)
  * 로그인 기능을 담당하는 폴더입니다. 현재 구글, 네이버 로그인 기능만 구현되어 있고 사용 가능한 로그인은 구글만 되어 있습니다.
* [(도메인) Domain](src/main/java/org/me/springboot/domain/user)
  * 유저정보의 도메인을 담당합니다. 유저(User)설정과 권한(Role)설정을 할 수 있습니다.

#### 사용한 데이터베이스 테이블
User
* id(_BIGINT [20] [AUTO_INCREMENT, 기본키]_) : 유저 ID
* email(_VARCHAR [255]_) : 유저 이메일
* name(_VARCHAR [255]_) : 유저 이름
* picture(_VARCHAR [255]_) : 유저 프로필 사진
* role(_VARCHAR [255]_) : 유저 권한 (현재 USER, GUEST 만 존재하며 가입시 USER로 시작)

---
### 테스트 기능 1.0.1
* 테스트용 기능을 사용하기 위해 새로운 탭을 개설하였습니다.
  * 현재 Api요청을 테스트 할 수 있습니다.
* 요청 url : http://mit4.iptime.org:3380/test/api/main/number
* 요청 방식 : POST
* 요청 내용 : Json 방식의 String number1, String number2 (단, number1, number2는 정수형 범위)
* 응답 내용 : Json 방식의 String add, String sub, String mul, String div (각각 덧셈, 뺄셈, 곱셈, 나눗셈 결과값)

#### 코드 참고
* [(컨트롤러) TestApiController](src/main/java/org/me/springboot/web/TestApiController.java)
  * 테스트 Api를 요청하여 사용할 수 있는 컨트롤러 입니다.
* [(서비스) Service](src/main/java/org/me/springboot/service/tests)
  * 테스트 Api의 서비스를 담당하는 폴더입니다.
* [(도메인) Domain](src/main/java/org/me/springboot/domain/test/api)
  * 테스트 Api의 도메인을 담당하는 폴더입니다. 받은 Api의 값을 연산하여 돌려줍니다. DB와 연동되지 않습니다.
  
---
### 여러 추가 기능 1.0.2
* 여러가지 기능을 사용하기 위해 새로운 탭을 개설하였습니다.
  * 현재 로또 추첨기를 사용할 수 있습니다.
  * 로또 추첨기는 로그인을 해야 사용할 수 있습니다.
  * 로또 추첨기 서버는 다른 프로젝트의 서버에서 api를 사용하기에 해당 서버와 미연동시 사용이 불가합니다.
  
#### 코드 참고
* [(컨트롤러) FunctionController](src/main/java/org/me/springboot/web/FunctionController.java)
  * 각 추가 기능이 있는 페이지로 이동시켜 주는 컨트롤러 입니다. 현재 로또추첨 기능만 존재합니다.
* [(메인페이지) lottoMain](src/main/resources/templates/lotto/lottoMain.mustache), [(Api 요청) lotto.js](src/main/resources/static/js/app/lotto.js)
  * ajax를 통해 타 서버(프로젝트)의 Api를 요청하여 데이터를 가져옵니다.

---
### 가상 비트코인 거래소 기능 2.0
* 가상 비트코인 거래소
  * 가상으로 비트코인을 거래해볼 수 있는 기능입니다.
  * 가격은 실제 가격과 동일하며 1초마다 정보가 갱신됩니다.
  * 기본적으로 가상머니 1억이 주어집니다. (대출금 1억)
  * 앞으로 더 기능을 추가 할 예정입니다.

#### 코드 참고
* [(컨트롤러) BitcoinApiController](src/main/java/org/me/springboot/web/BitcoinApiController.java)
  * 비트코인의 정보요청, 거래내역, 거래요청을 담당하는 컨트롤러입니다.
* [(서비스) Service](src/main/java/org/me/springboot/service/function/bitcoin/BitcoinService.java)
  * 비트코인의 정보요청, 지갑생성, 거래내역 가져오기, 거래진행 후 내역추가 등의 기능을 가진 서비스입니다.
* [(도메인) Bitcoin](src/main/java/org/me/springboot/domain/bitcoin), [Wallet](src/main/java/org/me/springboot/domain/wallet)
  * 비트코인 로그, 지갑정보를 저장하는 도메인 입니다.
* [(메인페이지) bitcoinMain](src/main/resources/templates/bitcoin/bitcoinMain.mustache), [(Api 요청) bitcoin.js](src/main/resources/static/js/app/bitcoin.js)
  * ajax를 통해 서버의 코인정보를 매 초마다 업데이트 하며, 비트코인 거래, 거래내역 가져오기 등을 수행합니다.

#### 사용한 데이터베이스 테이블
bitcoin_log
* id(_BIGINT [20] [AUTO_INCREMENT, 기본키]_) : 비트코인로그 ID
* user_id(_BIGINT [20] [외래키]_) : 유저 ID
* coins(_VARCHAR [255]_) : 코인 이름 (btc, bch, btg, eos, etc, eth, ltc, xrp 중 1)
* amount(_BIGINT [20]_) : 코인의 변화량 (음수 : 판매, 양수 : 구입)
* value(_BIGINT [20]_) : 코인의 현재 가격 (매수 혹은 매도 시 가격)

wallet
* id(_BIGINT [20] [AUTO_INCREMENT, 기본키]_) : 지갑 ID
* user_id(_BIGINT [20] [외래키]_) : 유저 ID (지갑은 유저 하나당 한개만 소지가능, 1:1관계)
* cash(_BIGINT [20]_) : 보유 현금의 양
* btc, bch, btg, eos, etc, eth, ltc, xrp(_BIGINT [20]_) : 각 보유 코인의 갯수
* loan(_BIGINT [20]_) : 대출 받은 금액 (초기 1억)
