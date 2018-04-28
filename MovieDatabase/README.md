# MovieDatabase
Simple program for Movie Database.

우선 각 장르마다 영화 제목에 따라 정렬된 리스트를 만듭니다.

    (ACTION, BATMAN BEGINS, ONG-BAK, THE MATRIX)
    (DRAMA, MILLION DOLLAR BABY, THE AVIATOR)
    (HORROR, HELLRAISER)

그리고 위의 리스트들을 원소로 가지며, 장르에 따라 정렬된 리스트를 만듭니다.

    ( (ACTION, BATMAN BEGINS, ONG-BAK, THE MATRIX), (DRAMA, MILLION DOLLAR BABY, THE AVIATOR), (HORROR, HELLRAISER) )

## 지원하는 명령어
**삽입**: 

    INSERT %장르% %제목%
해당 장르와 제목을 가진 영화를 삽입합니다. 이미 데이터베이스에 있는 영화와 장르, 제목이 모두 같으면 삽입하지 않습니다.

**삭제**: 
    
    DELETE %장르% %제목%
해당 장르와 제목을 가진 영화를 삭제합니다. 각 장르의 마지막 영화가 삭제되면 장르도 삭제됩니다.

**검색**: 

    SEARCH %검색어%
제목에 검색어가 들어 있는 모든 영화들의 장르와 제목을 정렬된 순서로 출력합니다.

**출력**: 
    
    PRINT
데이터베이스의 전체 내용을 정렬된 순서로 출력합니다.

**종료**: 

    QUIT
프로그램을 종료합니다.

## 입출력 형식
프로그램을 실행하면 한줄로 된 명령어를 입력받습니다.

명령어를 입력받으면 적절한 작업을 수행하고 SEARCH와 PRINT는 그 결과를 출력합니다.

명령어의 앞뒤 및 명령어와 인자들 사이에는 0개 이상의 공백이 들어갈 수 있습니다.

모든 영화들의 장르와 제목에는 '%'와 ',' 문자가 들어가지 않습니다.

영화 목록을 출력할 때에는 한 줄에 (장르, 제목)과 같이 하나의 영화만 출력하고, 결과가 더 있으면 다음 줄에 계속 해서 같은 형식으로 출력합니다. 데이터베이스가 비어있으면 EMPTY로 출력합니다.

SEARCH시에 검색어가 들어 있는 영화가 없다면, EMPTY로 출력합니다.

모든 입력은 대문자로 들어온다고 가정하셔도 좋습니다.

영화 목록을 정렬 할 때에는 장르로 먼저 정렬하고, 장르가 같을 때에는 제목으로 정렬합니다. 참고로 데이터베이스를 제대로 구현한다면 목록을 정렬하기 위해서 별도의 작업은 필요치 않습니다.

출력할 때에는 한줄 한줄이 괄호로 묶여있어야 하며 ,(콤마) 뒤의 공백 한칸을 반드시 띄워야 합니다.

아래 예시의 형식과 정확히 일치하게 만드세요.

한 명령어의 실행이 끝나면 QUIT를 입력받을때까지 다시 입력을 받습니다.

    
    java MovieDatabaseConsole                 <- 프로그램 실행
    INSERT %ACTION% %BATMAN BEGINS%             <- 이렇게 입력
    INSERT %ACTION% %THE MATRIX%                <- 이렇게 입력
    INSERT %DRAMA% %MILLION DOLLAR BABY%        <- 이렇게 입력
    SEARCH %BA%                                 <- 이렇게 입력하면
    (ACTION, BATMAN BEGINS)                     <- 이렇게 출력
    (DRAMA, MILLION DOLLAR BABY)                <- 이렇게 출력한다.
    DELETE %DRAMA% %MILLION DOLLAR BABY%        <- 이렇게 입력
    PRINT                                       <- 이렇게 입력하면
    (ACTION, BATMAN BEGINS)                     <- 이렇게 출력
    (ACTION, THE MATRIX)                        <- 이렇게 출력한다.
    QUIT                                        <- 이렇게 입력하면
    $                                           <- 종료한다.
