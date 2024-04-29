const mongoose = require('mongoose');

const productSchema = new mongoose.Schema({
  name: { type: String, required: true, trim: true },
  image: { type: String, required: true },
  description: { type: String, required: true },
  price: { type: Number, required: true },
  popular: { type: Boolean, required: true},
  category: { type: mongoose.Schema.Types.ObjectId, ref: 'Category', required: true }
});

const Product = mongoose.model('Product', productSchema);

module.exports = Product;