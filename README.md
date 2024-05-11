# urban-enigma
do an android nang cao sgu app cua hang giao do an

## thông tin setup để chạy local

### Backend
1. backend nằm trong `/backend/backend-nodejs`
2. hình lưu ở `/backend/public/images`, route up hình `POST /images`, route tải hình `GET /images.[uuidv4.png]`, hình cho push lên github luôn

### Frontend app
1. app nằm trong `frontend/customer-app` & `frontend/shipper-app`

### Firebase
1. lên firebase console tạo project
2. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/f6663965-b783-4a06-9ec9-059f2d1e3baa)
3. vào project, vào authentication
4. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/b436f4a5-fc9b-4b42-a111-53d7016594a5)
5. sign-in method, add new provider, bật email/password, phone lên
6. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/dafa217b-4284-4572-8d32-11853d9262b5)
7. vào users, add user, email firebase-test-user@example.com, pass firebasetestuser
8. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/20c7d96b-f836-4f64-aedb-e82f3c692292)
9. vào project overview, add app, android
10. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/ae58ece7-b66e-40cf-9391-f1468c1efe68)
11. mở project frontend customer-app, execute gradle task
12. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/2cbbc1e1-1f73-4ad9-9aeb-8578d3896c4f)
13. gõ `gradle signingReport`
14. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/eec96f8f-8624-4156-adc1-273d0605a577)
15. lấy sha1 của variant debug và config debug
16. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/db356b41-dc2b-4def-a440-a6753f1c1df5)
17. quay lại firebase console, nhập app package name `com.doansgu.customer` và sha1
18. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/3d61b7f3-711e-469a-a5cf-d676de6599e8)
19. tải file `google-services.json` rồi để như hướng dẫn
20. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/53baff49-e3b5-4b23-90d8-6a800ab4684d)
21. bấm tiếp tục bước 3 và 4 (**không bấm tắt** cũng **không làm theo**)
22. quay lại firebase console, add app, web
23. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/eb6c2af3-4bc9-4d84-b00b-13ea8402e458)
24. chọn `use a <script> tag`, copy `firebaseConfig`
25. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/52a31fc7-fae6-4fd8-b706-47c9b1409183)
26. vô `backend/app/public/firebase-auth-test.html`, thay đổi `firebaseConfig` thành cái vừa copy
27. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/00bd7f0c-21a6-403d-814b-71463827cddf)
28. quay lại firebase console, vô project settings, service account, generate new private key
29. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/b8659ad9-34f3-4013-ac21-6161348856d0)
30. tải file key về bỏ vào `/backend/app/config`
31. thay đổi `const serviceAccount = require` thành tên file đúng file mới tải
32. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/92fa8e1c-3824-43b6-81b7-6c17461a2e95)
33. lệnh `npm start` lên thấy `Connect Firebase without errors` và `Firebase connection test success` là xong
34. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/6c91624c-2407-4d7f-9dfc-1a47e5668fcc)

### Mongodb
1. mongodb atlas và lấy driver uri connect chắc biết rồi nhỉ
2. vào `backend/app/config/_APP.js`
3. tự thay dổi `DATABASE_CONNECTION_URL` và `DATABASE_NAME` theo cài đặt của mình
4. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/209acc05-9f3e-45a4-b3bc-13cc56af8b0a)
5. lệnh `npm start` thấy `Connected to MongoDB` là xong
6. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/6c91624c-2407-4d7f-9dfc-1a47e5668fcc)
