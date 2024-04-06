import express from "express";
import cors from "cors"
import bodyParser from "body-parser";

var app = express();

import { config } from "dotenv"

config({ path: './env.shared' });

// Sử dụng body-parser middleware
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cors());
//Import thư viện kết nối MongoDB ---------------------------------------------------------------
/**
 * Backend với MongoDB 
 */
import db from './app/config/connect'
import mongoose from "mongoose";
const ObjectId = mongoose.Types.ObjectId
/**
 * Product
 */

// Tạo API GET để lấy dữ liệu từ product collection - PASS
app.get('/products', async (req, res) => {
  try {
    const products = await db.collection('Product').find().toArray();
    res.json(products);
    console.log('/products Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
});

// Tạo API Get để lấy tìm dữ liệu product bằng _id - PASS
app.get('/products/:id', async (req, res) => {
  try {
    const product = await db.collection('Product').findOne({ _id: new ObjectId(req.params.id) });
    res.json(product);
    console.log('/products/:id Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
});

// Tạo API Post để add document cho collection product - PASS
app.post('/products/add', async (req, res) => {
  try {
    const { name, image, description, price, popular, category } = req.body;
    const product = {
      name: name,
      image: image,
      description: description,
      price: price,
      popular: popular,
      category: category,
    };
    await db.collection("Product").insertOne(product);
    console.log('/products/add Success')
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
})

// Tạo API Put để sửa dữ liệu document cho collection product - PASS
app.put('/products/update/:id', async (req, res) => {
  try {
    const { name, image, description, price, popular, category } = req.body;
    var myQuery = { _id: new ObjectId(req.params.id) };
    var newValues = {
      $set: {
        name: name,
        image: image,
        description: description,
        price: price,
        popular: popular,
        category: category,
      }
    }
    await db.collection("Product").updateOne(myQuery, newValues);
    console.log('/products/update/:id Success')
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
})

// Tạo API Delete để xóa dữ liệu document cho collection product - PASS
app.delete('/products/delete/:id', async (req, res) => {
  try {
    await db.collection('Product').deleteOne({ _id: new ObjectId(req.params.id) })
    console.log('/products/delete/:id Success')
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err)
  }
})

/**
 * Category
 */
// Tạo API lấy dữ liệu category từ collection category - PASS
app.get('/category', async (req, res) => {
  try {
    const catogery = await db.collection('Category').find().toArray();
    res.json(catogery);
    console.log('/category Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
});

// Tạo API search category by _id - PASS
app.get('/category/:id', async (req, res) => {
  try {
    const category = await db.collection('Category').findOne({ _id: new ObjectId(req.params.id) });
    res.json(category);
    console.log('/category/:id Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
})

// Tạo API thêm dữ liệu category - PASS
app.post('/category/add', async (req, res) => {
  try {
    const { name } = req.body;
    const category = {
      name: name,
    };
    db.collection('Category').insertOne(category);
    console.log('/category/add Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
})

// Tạo API sửa dữ liệu category - PASS
app.put('/category/update/:id', async (req, res) => {
  try {
    const { name } = req.body;
    var myQuery = { _id: new ObjectId(req.params.id) };
    var newData = {
      $set: {
        name: name,
      }
    }
    await db.collection('Category').updateOne(myQuery, newData)
    console.log('/category/update/:id Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
})


// Tạo API xóa dữ liệu category - PASS
async function isCategoryExist(categoryId: string) {
  try {
    const productCount = await db.collection('Product').countDocuments({ category: categoryId })
    return productCount > 0;
  } catch (error) {
    console.log(error);
    return false;
  }
}

app.delete('/category/delete/:id', async (req, res) => {
  try {
    const categoryExists = await isCategoryExist(req.params.id);
    if (!categoryExists) {
      await db.collection('Category').deleteOne({ _id: new ObjectId(req.params.id) });
      console.log('/category/delete/:id Success');
    }
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
})

/**
 * Account
 */
// Tạo API Thêm dữ liệu Account - PASS
async function isUserNameExist(username: string) {
  try {
    const existingUsername = await db.collection('Account').countDocuments({ username: username });
    return existingUsername > 0;
  } catch (err) {
    console.log(err);
    return false;
  }
}

app.get('/account/:username', async (req, res) => {
  try {
    const account = await db.collection('Account').findOne({ username: req.params.username });
    res.json(account);
    console.log('/account/:username Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.error(err);
  }
})

app.post('/account/add', async (req, res) => {
  try {
    const { username, password, role } = req.body;
    const account = {
      username: username,
      password: password,
      role: role
    }
    const checkUsername = await isUserNameExist(account.username);
    if (!checkUsername) {
      await db.collection('Account').insertOne(account)
      console.log('/account/add Success');
      
      return
    }
    console.log('/account/add Exists')
  } catch (err) {
    console.log(err);
  }
})

// Tạo API Sửa password Account - PASS
app.put('/account/update/:username', async (req, res) => {
  try {
    var myQuery = { username: req.params.username };
    var newPassword = {
      $set: {
        password: req.body.password,
      }
    }
    const checkUsername = await isUserNameExist(req.params.username);
    if (checkUsername) {
      await db.collection('Account').updateOne(myQuery, newPassword)
    }
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.log(err);
  }
})

// Tạo API Sửa Role Account cho nhân viên - PASS
app.put('/account/update/staff/:username', async (req, res) => {
  try {
    var myQuery = { username: req.params.username };
    var newRole = {
      $set: {
        role: req.body.role,
      }
    }
    const checkUsername = await isUserNameExist(req.params.username);
    if (checkUsername) {
      await db.collection('Account').updateOne(myQuery, newRole)
    }
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.log(err);
  }
})

/**
 * User
 */
// Tạo API lấy dữ liệu user là nhân viên - PASS
app.get('/user/staff', async (req, res) => {
  try {
    const staff = await db.collection('User').find({ isStaff: true }).toArray();
    res.json(staff);
    console.log('Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.log(err);
  }
})

// Tạo API tìm kiếm dữ liệu user bằng _id - PASS
app.get('/user/:id', async (req, res) => {
  try {
    const staff = await db.collection('User').findOne({ _id: new ObjectId(req.params.id) });
    res.json(staff);
    console.log('Success');
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.log(err);
  }
})

// Tạo API thêm user là nhân viên - PASS
app.post('/user/add/staff', async (req, res) => {
  try {
    const { name, gender, dateOfBirth, phone, address, position, username } = req.body;
    const staff = {
      name: name,
      gender: gender,
      dateOfBirth: dateOfBirth,
      phone: phone,
      address: address,
      position: position,
      username: username,
      isStaff: true
    }
    await db.collection('User').insertOne(staff)
  } catch (err) {
    res.status(500).json({ message: (err as Error).message });
    console.log(err);
  }
})

// Tạo API sửa user là nhân viên - PASS
app.put('/user/update/staff/:id', async (req, res) => {
  try {
    const { name, gender, dateOfBirth, phone, address, position } = req.body;
    var myQuery = { _id: new ObjectId(req.params.id) };
    var newData = {
      $set: {
        name: name,
        gender: gender,
        dateOfBirth: dateOfBirth,
        phone: phone,
        address: address,
        position: position,
        isStaff: true
      }
    }
    await db.collection('User').updateOne(myQuery, newData)
  } catch (err) {
    console.log(err);
  }
})

// Tạo API thêm user là khách hàng - PASS
app.post('/user/add/customer', async (req, res) => {
  try {
    const { name, gender, dateOfBirth, phone, qrCode, membershipPoints, username } = req.body;
    const customer = {
      name: name,
      gender: gender,
      dateOfBirth: dateOfBirth,
      phone: phone,
      qrCode: qrCode,
      membershipPoints: membershipPoints,
      username: username
    }
    await db.collection('User').insertOne(customer)
  } catch (err) {
    console.log(err);
  }
})

// Tạo API sửa user là khách hàng - PASS
app.put('/user/update/customer/:id', async (req, res) => {
  try {
    const { name, gender, dateOfBirth, phone, qrCode, membershipPoints } = req.body;
    var myQuery = { _id: new ObjectId(req.params.id) };
    var newData = {
      $set: {
        name: name,
        gender: gender,
        dateOfBirth: dateOfBirth,
        phone: phone,
        qrCode: qrCode,
        membershipPoints: membershipPoints
      }
    }
    await db.collection('User').updateOne(myQuery, newData)
  } catch (err) {
    console.log(err);
  }
})

// Tạo API xóa user là nhân viên - PASS
app.delete('/user/delete/:id', async (req, res) => {
  try {
    await db.collection('User').deleteOne({ _id: new ObjectId(req.params.id) }, function (err, result) {
      if (err) throw err;
      console.log(result);
    });
  } catch (err) {
    console.log(err);
  }
})

/**
 * Order
 */
// Tạo API lấy dữ liệu order - PASS
app.get('/orders', async (req, res) => {
  try {
    const orders = await db.collection('Order').find().toArray();
    res.json(orders);
    console.log('Success');
  } catch (err) {
    res.status(500).json({ message: err.message });
    console.log("Failed");
  }
})

// Tạo API lấy dữ liệu order bằng _id - PASS
app.get('/orders/:id', async (req, res) => {
  try {
    const order = await db.collection('Order').findOne({ _id: new ObjectId(req.params.id) });
    res.json(order);
    console.log("Success");
  } catch (err) {
    res.status(500).json({ message: err.message });
    console.log('Failed');
  }
})

// Tạo API lưu dữ liệu order - PASS
app.post('/orders/add', async (req, res) => {
  try {
    const { user, orderDateTime, status, deliveryLocation, note, discount, detailOrders } = req.body;
    const order = {
      user: user,
      orderDateTime: orderDateTime,
      status: status,
      deliveryLocation: deliveryLocation,
      note: note,
      discount: discount,
      detailOrders: detailOrders,
    }
    await db.collection('Order').insertOne(order, function (err, result) {
      if (err) throw err;
      console.log(result);
    })
  } catch (err) {
    console.log(err);
  }
})

// Tạo API cập nhật trạng thái dữ liệu order - PASS
app.patch('/order/update/:id', async (req, res) => {
  try {
    const { status } = req.body;
    var myQuery = { _id: new ObjectId(req.params.id) };
    var newData = {
      $set: {
        status: status,
      }
    }
    await db.collection('Order').updateOne(myQuery, newData, function (err, result) {
      if (err) throw err;
      console.log(result);
    })
  } catch (err) {
    console.log(err);
  }
})

/**
 * Rating
 */
// Tạo API để lấy dữ liệu rating của 1 sản phẩm - PASS
app.get('/rating/:idProduct', async (req, res) => {
  try {
    const rating = await db.collection('Rating').find({ product: req.params.idProduct }).toArray();
    res.json(rating);
    console.log("Success");
  } catch (err) {
    res.status(500).json({ message: err.message });
    console.log("Failed");
  }
})
// TẠO API để sửa rating của 1 user - PASS
app.put('/rating/update/:user/:product', async (req, res) => {
  try {
    var myQuery = { user: req.params.user, product: req.params.product };
    var newData = {
      $set: {
        rating: req.body.rating
      }
    };
    await db.collection('Rating').updateOne(myQuery, newData, function (err, result) {
      if (err) throw err;
      console.log(result);
    })
  } catch (err) {
    console.log(err);
  }
})
// Tạo API để thêm 1 dữ liệu rating - PASS
app.post('/rating/add/:user/:product', async (req, res) => {
  try {
    const rate = {
      user: req.params.user,
      product: req.params.product,
      rating: req.body.rating,
    }
    await db.collection('Rating').insertOne(rate, function (err, result) {
      if (err) throw err;
      console.log(result);
    })
  } catch (err) {
    console.log(err);
  }
})

// Tạo API để lấy trung bình đánh giá của sản phẩm - PASS
app.get('/rating/average/:id', async (req, res) => {
  try {
    var myQuery = [
      {
        $match: {
          product: req.params.id
        }
      },
      {
        $group: {
          _id: req.params.id,
          avgRate: {
            $avg: "$rating"
          }
        }
      }
    ];
    const abc = await db.collection('Rating').aggregate(myQuery).toArray();
    res.json(abc);
    console.log("Success");
  } catch (err) {
    res.status(500).json({ message: err.message });
    console.log(err);
  }
})

// Pass
app.get('/order/new', async (req, res) => {
  try {
    const order = await db.collection('Order').find({ status: "new" }).toArray();
    res.json(order);
    console.log('Success');
  } catch (err) {
    res.status(500).json({ message: err.message });
    console.error("Failed");
  }
})

// Pass

app.get('/order/delivered', async (req, res) => {
  try {
    const order = await db.collection('Order').find({ status: "delivered" }).toArray();
    res.json(order);
    console.log('Success');
  } catch (err) {
    res.status(500).json({ message: err.message });
    console.error("Failed");
  }
})

app.get('/order/delivering', async (req, res) => {
  try {
    const order = await db.collection('Order').find({ status: "delivering" }).toArray();
    res.json(order);
    console.log('Success');
  } catch (err) {
    res.status(500).json({ message: err.message });
    console.error("Failed");
  }
})

// API endpoint để thống kê doanh thu trong một ngày
app.get('/revenue/day', async (req, res) => {
  // const date = req.params.date;
  // Tìm các đơn hàng được giao trong ngày đã cho
  var myQuery = [
    {
      $match: {
        orderDateTime: {
          $regex: "2024/03/30",
          $options: 'i'
        },
        status: 'delivered'
      }
    },
    {
      $group: {
        _id: null,
        totalRevenue: {
          $sum: {
            $subtract: [
              {
                $add: [
                  {
                    $reduce: {
                      input: "$detailOrders",
                      initialValue: 0,
                      in: {
                        $add: [
                          "$$value",
                          { $multiply: ["$$this.amount", "$$this.product.price"] }
                        ]
                      }
                    }
                  },
                  "$deliveryFee"
                ]
              },
              { $multiply: ["$discount", { $reduce: { input: "$detailOrders", initialValue: 0, in: { $add: ["$$value", { $multiply: ["$$this.amount", "$$this.product.price"] }] } } }] } // Chiết khấu
            ]
          }
        }
      }
    }
  ]
  const check = await db.collection("Order").aggregate(myQuery).toArray();
  res.json(check);
  db.close();
});

// API endpoint để thống kê doanh thu trong một tháng
app.get('/revenue/month/:year/:month', (req, res) => {
  const year = req.params.year;
  const month = req.params.month;

  MongoClient.connect(url, { useNewUrlParser: true, useUnifiedTopology: true }, (err, client) => {
    if (err) {
      console.error('Error connecting to MongoDB:', err);
      res.status(500).send('Internal Server Error');
      return;
    }

    const db = client.db(dbName);
    const collection = db.collection('orders');

    // Tìm các đơn hàng được giao trong tháng đã cho
    collection.aggregate([
      { $match: { orderDateTime: { $regex: year + '/' + month, $options: 'i' }, status: 'delivered' } },
      { $group: { _id: null, totalRevenue: { $sum: '$totalPrice' } } }
    ]).toArray((err, result) => {
      if (err) {
        console.error('Error querying MongoDB:', err);
        res.status(500).send('Internal Server Error');
        return;
      }

      if (result.length > 0) {
        res.json({ totalRevenue: result[0].totalRevenue });
      } else {
        res.json({ totalRevenue: 0 });
      }

      client.close();
    });
  });
});

app.listen(3001, function (req, res) {
  console.log('Hello');
})