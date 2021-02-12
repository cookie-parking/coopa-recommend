# Coopa-Recommend
쿠키파킹 Article Recommend를 위한 Batch System

## Simple Architecture
![Page2](https://user-images.githubusercontent.com/37579681/107773975-c21fba00-6d81-11eb-94f5-c23a9a1e69c4.jpg)

## 핵심 기능
1. Request를 해당 URL에 보내서 body 데이터를 긁어옴
2. cheerio를 이용해서 컨텐츠 파싱
3. AWS Comprehend를 통해 Keyword 뽑음
4. AWS Lambda에 keyword 보내서 크롤링 데이터 받아옴
5. 해당 크롤링한 데이터를 DB에 저장하기
