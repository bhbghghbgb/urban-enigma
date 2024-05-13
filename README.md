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
7. bấm edit configuration của phone
8. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/90c8108a-03b5-45fc-94fe-ef16cc38dfbe)
9. add 1 vài số dt và mã code để test số ảo không tốn tiền
10. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/73482110-4fe8-415a-894a-c79cd8c7cc56)
11. vào users, add user, email firebase-test-user@example.com, pass firebasetestuser
12. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/20c7d96b-f836-4f64-aedb-e82f3c692292)
13. vào project overview, add app, android
14. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/ae58ece7-b66e-40cf-9391-f1468c1efe68)
15. mở project frontend customer-app, execute gradle task
16. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/2cbbc1e1-1f73-4ad9-9aeb-8578d3896c4f)
17. gõ `gradle signingReport`
18. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/eec96f8f-8624-4156-adc1-273d0605a577)
19. lấy sha1 của variant debug và config debug
20. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/db356b41-dc2b-4def-a440-a6753f1c1df5)
21. quay lại firebase console, nhập app package name `com.doansgu.cafectm` và sha1
22. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/3d61b7f3-711e-469a-a5cf-d676de6599e8)
23. tải file `google-services.json` rồi để như hướng dẫn
24. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/53baff49-e3b5-4b23-90d8-6a800ab4684d)
25. bấm tiếp tục bước 3 và 4 (**không bấm tắt** cũng **không làm theo**)
26. quay lại firebase console, add app, web
27. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/eb6c2af3-4bc9-4d84-b00b-13ea8402e458)
28. chọn `use a <script> tag`, copy `firebaseConfig`
29. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/52a31fc7-fae6-4fd8-b706-47c9b1409183)
30. vô `backend/app/public/firebase-auth-test.html`, thay đổi `firebaseConfig` thành cái vừa copy
31. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/00bd7f0c-21a6-403d-814b-71463827cddf)
32. quay lại firebase console, vô project settings, service account, generate new private key
33. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/b8659ad9-34f3-4013-ac21-6161348856d0)
34. tải file key về bỏ vào `/backend/app/config`
35. thay đổi `const serviceAccount = require` thành tên file đúng file mới tải
36. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/92fa8e1c-3824-43b6-81b7-6c17461a2e95)
37. lệnh `npm start` lên thấy `Connect Firebase without errors` và `Firebase connection test success` là xong
38. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/6c91624c-2407-4d7f-9dfc-1a47e5668fcc)

### Mongodb
1. mongodb atlas và tạo database + collection, lấy driver uri connect chắc biết rồi nhỉ
2. vào https://mongodb.com đăng nhập google
3. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/35bc1cb3-c718-4eee-9721-1a1173729103)
4. bấm bỏ qua mấy trang khảo sát
5. ở trang Overview bấm connect, Drivers
6. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/eb70f95a-c44b-4900-9db2-86412ea04f6f)
7. copy connection string này, nhưng cần phải thay `<username>:<password>` mới xong
8. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/db6aa923-21a3-4ccc-9ea6-c5cc0f46e74c)
9. xem mục security, database access
10. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/66c63c02-edb6-4513-a800-493424c2bf45)
11. bấm add new database user, nhập username password
12. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/7538c9cc-9668-4284-ad5a-04af761d0bb5)
13. sau khi bấm add user nó quay về thấy hiện user mới tạo ở bảng là được
14. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/61b7cb27-5f9e-4ee6-a08a-13bd9218af11)
15. thay đổi phần `<username>:<password>` trong connection string đã copy thành cái mới tạo
16. vào `backend/app/config/_APP.js`
17. tự thay dổi `DATABASE_CONNECTION_URL` và `DATABASE_NAME` theo cài đặt của mình
18. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/209acc05-9f3e-45a4-b3bc-13cc56af8b0a)
19. lệnh `npm start` thấy `Connected to MongoDB` là xong
20. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/6c91624c-2407-4d7f-9dfc-1a47e5668fcc)
