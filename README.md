# Coopa-Recommend
쿠키파킹 Article Recommend를 위한 Batch System

## Simple Architecture
![Page2](https://user-images.githubusercontent.com/37579681/107773975-c21fba00-6d81-11eb-94f5-c23a9a1e69c4.jpg)

## 핵심 기능
1?. Request를 해당 URL에 보내서 body 데이터를 긁어옴
2?. cheerio를 이용해서 컨텐츠 파싱된 컨텐츠를 DB에 저장
pre3. userId로 spring boot에 요청 보내면 batch job 실행
3. DB에서 쿠키 데이터를 가져와서 AWS Comprehend를 통해 Keyword 추출 (2.14 성공)
3-1. 1번과 2번이 불필요할 수도 있으며, 그냥 저장된 title 정도에서만 keyword를 뽑아서 진행해봐도 좋을 것 (2.14 성공)
3-2. 추출한 keyword를 테이블에 저장함 (2.14 성공)
3-3. 단, 이미 keyword가 추출되어 있는 쿠키는 다시 추출하지 않아야 함.
4. AWS Lambda에 keyword 보내서 크롤링 데이터 받아옴
5. 해당 크롤링한 추천 쿠키들을 DB에 저장하기
