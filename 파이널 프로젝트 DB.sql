#ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ기본테이블ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

#알림게시판을 향후 추가할 수 있으니 pboard로 지어놨음. 상품 정보가 들어가 있는 게시판에 관한 table 
CREATE TABLE tbl_pboard (
    pcode char(36) primary key not null,           #상품코드                                                         상품코드가 중복되는 것을 방지하기 위해 PK로 UUID 사용
    ptitle VARCHAR(50) NOT NULL,                 #상품제목.                                                         50자 까지만 저장 가능.
    pcontent TEXT,                                       #text로 쓰면 검색 속도가 느려짐.                            pcontent도 검색대상에 포함되면 검색결과 산출이 느려짐.
    pwriter VARCHAR(30) NOT NULL,              #작성자.                                                             30자까지만 저장 가능.
    viewcnt int default 0,                              #조회수.transcation이 필요함.
    replycnt int default 0,
    plike int default 0,                                   #상품 좋아요
    pname varchar(30) not null,
    pcondition int default 0,                          #팔리면 1 안 팔리면 0
    regDate DATETIME DEFAULT NOW(),         #등록일자
    updateDate DATETIME DEFAULT NOW()    #수정일자
);

#이용자정보가 들어있는 table. 이제 사이트들은 주민번호를 받지 않는 방식을 선택하고 있음. 법변화로 인한 주민번호 정보 삭제. https://m.blog.naver.com/cordially43/221499990100
CREATE TABLE tbl_user(
    uid varchar(15) primary key,           #로그인에 사용되는 아이디.                                        15자까지
    upass varchar(100) not null,              #암호화되어 저장되는 비밀번호                                   암호화 하면 길어지기 때문에 100자는 줘야함.
    uname varchar(30) not null,              #법정 이름                                                               한국인 대상인데 혹시 몰라서 20자까지 최대제한 걸었음
    unickname varchar(30) not null,         #페이지 상에서 출력될 별명                                       너무 길지 않게 30자 제한을 걸었음.
    uemail varchar(50) not null,              # 회원가입 시 이용될 이메일                                       그외로 가면 직접 입력하고 그게 아니면 골라서 넣을 수 있게 
    utel varchar(50) not null,                  # 회원가입 시 받게 될 전화번호
    uaddress varchar(100) not null,          # 회원가입 시 받게 될 주소                                         API 받아서 아파트나 빌라이름 치면 자동으로 가져오게
    ugender char(2) not null,                  # 회원가입 시 받게 되는 성별                  
    uprofile varchar(200),                       # 회원가입 시 받게 될 프로필
    ubirth varchar(100),                      # 회원가입 시 받게 될 생년월일                                       
    ucondition char(1) not null default '1', #0은 탈퇴 상태. 1은 활동중인 상태                          #default는 1이지만, axios로 줄 때도 그냥 1로 줬음.    
    upoint double            
    );


#관리자정보가 들어있는 table. JSP로 만들려고 함.   
CREATE TABLE tbl_admin(
    aid char(4) primary key,                      #관리자아이디 
    apass varchar(100) not null,                 #암호화된 비밀번호 
    aname varchar(10) not null                 #관리자 법정이름. 관리자는 닉네임이 필요없으니까 법정이름으로 박아 넣음.
    );




#ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ참조 테이블ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

#결제 ㅡ> buy / sell
create table tbl_pay(
regDate datetime default now(),
paycode char(36) primary key,
payprice int not null,                          #pboard의 가격을 refer하지 않은 이유는 가격 흥정이 가능하기 때문. 
seller varchar(30) not null,
buyer varchar(30) not null,
paytype varchar(10) not null,
payemail varchar(30) not null,
pcode varchar(36) not null,
sellercondition int default 0,                    #0이면 후기 등록 x, 1이면  등록
buyercondition int default 0,                  #0이면 후기 등록 x, 1이면 등록
foreign key(seller) references tbl_user(unickname) on delete cascade on update cascade,        ㅡㅡㅡㅡ>delete cascade는 삭제
foreign key(buyer) references tbl_user(unickname) on delete cascade on update cascade,     ㅡㅡㅡㅡㅡㅡㅡㅡ>delete cascade는 삭제
foreign key(pcode) references tbl_pboard(pcode)
);

#공지사항
create table tbl_notice(
ncode int auto_increment primary key,
regDate datetime default now(),
ntitle varchar(50) not null,
ncontent varchar(200) not null,
nwriter char(4) not null,
foreign key(nwriter) references tbl_admin(aid)
);

#이벤트
create table tbl_event(
ecode int auto_increment primary key,
regDate datetime default now(),
etitle varchar(50) not null,
econtent varchar(200) not null,
ewriter char(4) not null,
foreign key(ewriter) references tbl_admin(aid) 
);

#채팅 ???
 create table tbl_chat(
 id int auto_increment primary key, 
 sender varchar(30),
 receiver varchar(30),
 message text, 
 regDate datetime default now(),
 readDate datetime,                                   #읽었다면 읽었다고 표시 띄우기 위한 column. 쪽지 참고 ㄱㄱ
 foreign key(sender) references tbl_user(unickname),
 foreign key(receiver) references tbl_user(unickname)
);

#좋아요 여부
CREATE table tbl_like_pboard(
unickname varchar(30) not null,
pcode char(36) not null,
lcode char(36) primary key,
lovecondition boolean default true,
foreign key(pcode) references tbl_pboard(pcode) on delete cascade on update cascade,
foreign key(unickname) references tbl_user(unickname) on delete cascade on update cascade,
unique key(pcode,unickname)
);

#거래 후기
create table tbl_review(
regDate datetime default now(),
rvcode char(36) primary key, 
rvcontent varchar(100) not null,
sender varchar(15) not null,
receiver varchar(15) not null,
point double not null,
paycode char(36) not null,
foreign key(paycode) references tbl_pay(paycode),  #상품번호가 아니라 결제번호에 refer를 달아 언제든 후기를 남길 수 있게 함. 상품번호는 결제완료시 삭제되기 때문
foreign key(sender) references tbl_user(unickname) on delete cascade on update cascade,
foreign key(receiver) references tbl_user(unickname) on delete cascade on update cascade
);

#이벤트에 달리는 댓글
create table tbl_ereply(
regDate datetime default now(),
ecode int not null,
ercode int auto_increment primary key,
ercontent varchar(100) not null,
erwriter varchar(15) not null,
admincondition int default 0,                          #0이면 삭제 x 1이면 삭제
usercondition int default 0,                              #0이면 삭제 x 1이면 삭제
foreign key(erwriter) references tbl_user(unickname) on delete cascade on update cascade,
foreign key(ecode) references tbl_event(ecode)
);

#ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ더미 테이블ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

CREATE table temp_month(
tmonth datetime default now()
);