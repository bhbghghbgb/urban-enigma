# urban-enigma
do an android nang cao sgu app cua hang giao do an

## Chu y
1. ⚠ xem update mới ngày `Tue May 14 2024 22:27:42 GMT+0700 (Indochina Time)`: [nhập thêm app shipper vào firebase](#add-app-shipper-to-firebase)
2. ⚠ xem update mới ngày `Wed May 15 2024 00:42:23 GMT+0700 (Indochina Time)`: [kết nối qua internet](#connecting-over-the-internet)
3. ⚠ xem update mới ngày `Mon May 20 2024 01:14:49 GMT+0700 (Indochina Time)`: [đổi format log ip access control](#whitelist-ip-on-backend)

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
20. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/e0a766d5-1f99-4138-b853-5de2c5ad10a9)
21. quay lại firebase console, nhập app package name `com.doansgu.cafectm` và sha1
22. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/ecbda996-dc45-4359-8630-0ba48802677f)
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
###### Add app shipper to firebase
39. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/39cef46c-d5fe-4efb-abe1-4deb5a5ba34c)
40. add thêm 1 app với package name `com.example.delivery_app` vào, sha1 nhập như của app customer đã nhập
41. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/10a0709a-dfde-4e1d-a6d4-595215a47e5f)

### Mongodb
1. mongodb atlas và tạo database + collection, lấy driver uri connect chắc biết rồi nhỉ
2. vào https://account.mongodb.com/account/login đăng nhập google
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
18. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/ae94c6e1-595a-491a-8e57-144b77506f7e)
19. lệnh `npm start` thấy `Connected to MongoDB` là xong
20. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/6c91624c-2407-4d7f-9dfc-1a47e5668fcc)

## connecting over the internet

### CHÚ Ý
1. chỉ dùng cho **thiết bị thật** kết nối khi **lên báo cáo**
2. nên thực hiện xong [thông tin setup để chạy local](#backend) trước khi tiếp tục
3. bước [whitelist ip cho backend](#whitelist-ip-on-backend) phải **thực hiện lại** khi **đổi ip**.
4. Tránh commit file config tránh để bị lộ IP nếu không thích.
5. ℹ️ các ip có ghi sẵn như `["127.0.0.1", "localhost", "::1", "10.0.2.2", "10.0.3.2"]` là các ip loopback và loopback cho host của android studio emulator
###### whitelist ip on backend
6. nếu kết nối local (test bằng GET `/helloworld`) cũng bị `403 Forbidden, this client IP ${chuoi gi do} is not whitelisted.`, hoặc muốn add ip qua mạng này vào danh sách
7. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/13836d0b-5a36-4242-a277-3918f9dfebf0)
8. đọc log của server, **ví dụ** thấy `::ffff:127.0.0.1 (forwarded for 2402:0000:0000:0000:0000:0000:0000:9cba)` bị chặn mà đó là của mình, thì thêm vào `WHITELIST_IPS` trong `/backend/app/config/_APP.js`. thì thêm cả 2 cái IP `::ffff:127.0.0.1` và `2402:0000:0000:0000:0000:0000:0000:9cba`.
9. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/8b51033f-d414-4fff-b066-74bf90585643)
10. **restart server**, bảo đảm đã GET `/helloworld` được, hoặc vào được trang chủ, log show `_ accessed`
11. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/4deb9c4b-02da-4db2-b32e-f804a230ae7c)

### Ngrok
1. vào https://dashboard.ngrok.com/login đăng nhập google
2. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/34ad64b9-0b7e-4ba2-b1f1-ef7bb45b7d4e)
3. đăng ký xong, vào Your authtoken copy dòng lệnh `ngrok config add-authtoken` được chỉ dẫn
4. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/67c1f446-0c98-4bb1-9865-760d4a554f34)
5. chạy `npx <lệnh đã copy>` trong terminal của backend (đã được install bằng npm)
6. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/7d9432c7-a28c-4f43-b810-327e14c49dd8)
7. mở thêm 1 terminal, cái để chạy server, cái để chạy `npx ngrok http 3001`. **CHÚ Ý** port server mặc định `3001`, nếu muốn đổi server mở port khác thì ghi port đó vào
8. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/b7ecd510-6fb6-4814-a0c8-a8132e8be6ac)
9. lấy link `Forwarding` đã được ngrok mở cho
10. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/c0b012c8-cb76-4c21-bc08-b847e410485a)
11. visit site
12. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/69951c8a-fa68-4035-95d8-55a951f4cc20)
13. hãy xem kết nối được
14. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/373afa28-498c-468a-ab7d-8d3451bc3b2c)
15. **như vầy là server chưa chạy hoặc sai port hoặc abcxyz**
16. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/9c23ba69-da7b-4131-ba8d-dee1e8d463f4)
17. **như vầy là sai link ngrok**
18. ![image](https://github.com/bhbghghbgb/urban-enigma/assets/113711814/584bf072-2dcf-41d2-a4d3-288a742ec191)
