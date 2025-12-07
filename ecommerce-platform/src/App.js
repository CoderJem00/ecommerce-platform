import React, { useState, createContext, useContext, useEffect } from 'react';
import { ShoppingCart, Search, Heart, User, Menu, X, Plus, Minus, Trash2, Star, Filter, ArrowRight } from 'lucide-react';

// Context for Cart Management
const CartContext = createContext();

const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);
  const [favorites, setFavorites] = useState([]);

  const addToCart = (product) => {
    setCart(prev => {
      const existing = prev.find(item => item.id === product.id);
      if (existing) {
        return prev.map(item =>
          item.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
        );
      }
      return [...prev, { ...product, quantity: 1 }];
    });
  };

  const removeFromCart = (productId) => {
    setCart(prev => prev.filter(item => item.id !== productId));
  };
  // Sample Products Data
  const Products= [
    { id: 1, name: 'Wireless Headphones', price: 89.99, category: 'Electronics', image: '🎧', rating: 4.5, reviews: 234 },
    { id: 2, name: 'Smartphone', price: 699.99, category: 'Electronics', image: '📱', rating: 4.7, reviews: 512 },
    { id: 3, name: 'Laptop', price: 1299.99, category: 'Electronics', image: '💻', rating: 4.6, reviews: 321 },
    { id: 4, name: 'Gaming Console', price: 499.99, category: 'Electronics', image: '🎮', rating: 4.8, reviews: 213 },
    { id: 5, name: 'Bluetooth Speaker', price: 149.99, category: 'Electronics', image: '🔊', rating: 4.4, reviews: 145 },
  ];
  const updateQuantity = (productId, quantity) => {
    if (quantity <= 0) {
      removeFromCart(productId);
      return;
    }
    setCart(prev =>
      prev.map(item => item.id === productId ? { ...item, quantity } : item)
    );
  };

  const toggleFavorite = (productId) => {
    setFavorites(prev =>
      prev.includes(productId) ? prev.filter(id => id !== productId) : [...prev, productId]
    );
  };

  const cartTotal = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
  const cartCount = cart.reduce((sum, item) => sum + item.quantity, 0);

  return (
    <CartContext.Provider value={{ cart, favorites, addToCart, removeFromCart, updateQuantity, toggleFavorite, cartTotal, cartCount }}>
      {children}
    </CartContext.Provider>
  );
};

const useCart = () => useContext(CartContext);

// Product Card Component
const ProductCard = ({ product }) => {
  const { addToCart, toggleFavorite, favorites } = useCart();
  const isFavorite = favorites.includes(product.id);

  return (
    <div className="product-card">
      <div className="product-image">
        <span className="product-emoji">{product.imageUrl || product.image}</span>
        <button 
          className={`favorite-btn ${isFavorite ? 'active' : ''}`}
          onClick={() => toggleFavorite(product.id)}
        >
          <Heart size={20} fill={isFavorite ? '#ef4444' : 'none'} />
        </button>
      </div>
      <div className="product-info">
        <span className="product-category">{product.category}</span>
        <h3 className="product-name">{product.name}</h3>
        <div className="product-rating">
          <Star size={16} fill="#fbbf24" color="#fbbf24" />
          <span>{product.rating}</span>
          <span className="reviews">({product.reviewCount || product.reviews})</span>
        </div>
        <div className="product-footer">
          <span className="product-price">${product.price}</span>
          <button className="add-to-cart-btn" onClick={() => addToCart(product)}>
            <Plus size={18} /> Add
          </button>
        </div>
      </div>
    </div>
  );
};

// Shopping Cart Sidebar
const CartSidebar = ({ isOpen, onClose }) => {
  const { cart, updateQuantity, removeFromCart, cartTotal } = useCart();

  return (
    <>
      <div className={`cart-overlay ${isOpen ? 'active' : ''}`} onClick={onClose} />
      <div className={`cart-sidebar ${isOpen ? 'active' : ''}`}>
        <div className="cart-header">
          <h2>Shopping Cart</h2>
          <button className="close-btn" onClick={onClose}>
            <X size={24} />
          </button>
        </div>
        
        <div className="cart-items">
          {cart.length === 0 ? (
            <div className="empty-cart">
              <ShoppingCart size={64} color="#ccc" />
              <p>Your cart is empty</p>
            </div>
          ) : (
            cart.map(item => (
              <div key={item.id} className="cart-item">
                <span className="cart-item-emoji">{item.imageUrl || item.image}</span>
                <div className="cart-item-details">
                  <h4>{item.name}</h4>
                  <span className="cart-item-price">${item.price}</span>
                </div>
                <div className="cart-item-actions">
                  <div className="quantity-controls">
                    <button onClick={() => updateQuantity(item.id, item.quantity - 1)}>
                      <Minus size={16} />
                    </button>
                    <span>{item.quantity}</span>
                    <button onClick={() => updateQuantity(item.id, item.quantity + 1)}>
                      <Plus size={16} />
                    </button>
                  </div>
                  <button className="remove-btn" onClick={() => removeFromCart(item.id)}>
                    <Trash2 size={18} />
                  </button>
                </div>
              </div>
            ))
          )}
        </div>

        {cart.length > 0 && (
          <div className="cart-footer">
            <div className="cart-total">
              <span>Total:</span>
              <span className="total-amount">${cartTotal.toFixed(2)}</span>
            </div>
            <button className="checkout-btn">
              Proceed to Checkout <ArrowRight size={20} />
            </button>
          </div>
        )}
      </div>
    </>
  );
};

// Main App Component
const ECommerce = () => {
  const [isCartOpen, setIsCartOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState(['All']);
  const [loading, setLoading] = useState(true);
  const { cartCount } = useCart();

  // Fetch products from backend
  useEffect(() => {
    fetchProducts();
    fetchCategories();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/products');
      const data = await response.json();
      setProducts(data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching products:', error);
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/products/categories');
      const data = await response.json();
      setCategories(['All', ...data]);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const filteredProducts = products.filter(product => {
    const matchesSearch = product.name.toLowerCase().includes(searchQuery.toLowerCase());
    const matchesCategory = selectedCategory === 'All' || product.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  if (loading) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', fontSize: '24px' }}>
        Loading products...
      </div>
    );
  }

  return (
    <div className="ecommerce-app">
      {/* Header */}
      <header className="header">
        <div className="header-content">
          <div className="logo">
            <ShoppingCart size={32} />
            <span>ShopHub</span>
          </div>
          
          <div className="search-bar">
            <Search size={20} />
            <input
              type="text"
              placeholder="Search products..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </div>

          <div className="header-actions">
            <button className="icon-btn">
              <User size={24} />
            </button>
            <button className="icon-btn cart-btn" onClick={() => setIsCartOpen(true)}>
              <ShoppingCart size={24} />
              {cartCount > 0 && <span className="cart-badge">{cartCount}</span>}
            </button>
          </div>
        </div>
      </header>

      {/* Category Filter */}
      <div className="category-filter">
        <Filter size={20} />
        <div className="category-buttons">
          {categories.map(category => (
            <button
              key={category}
              className={`category-btn ${selectedCategory === category ? 'active' : ''}`}
              onClick={() => setSelectedCategory(category)}
            >
              {category}
            </button>
          ))}
        </div>
      </div>

      {/* Products Grid */}
      <main className="main-content">
        <div className="products-grid">
          {filteredProducts.length === 0 ? (
            <div style={{ gridColumn: '1 / -1', textAlign: 'center', padding: '50px', color: '#999' }}>
              No products found
            </div>
          ) : (
            filteredProducts.map(product => (
              <ProductCard key={product.id} product={product} />
            ))
          )}
        </div>
      </main>

      {/* Cart Sidebar */}
      <CartSidebar isOpen={isCartOpen} onClose={() => setIsCartOpen(false)} />

      <style>{`
        * {
          margin: 0;
          padding: 0;
          box-sizing: border-box;
        }

        body {
          font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
          background: #f5f7fa;
        }

        .ecommerce-app {
          min-height: 100vh;
        }

        .header {
          background: white;
          box-shadow: 0 2px 10px rgba(0,0,0,0.1);
          position: sticky;
          top: 0;
          z-index: 100;
        }

        .header-content {
          max-width: 1400px;
          margin: 0 auto;
          padding: 20px 30px;
          display: flex;
          align-items: center;
          gap: 30px;
        }

        .logo {
          display: flex;
          align-items: center;
          gap: 10px;
          font-size: 24px;
          font-weight: 700;
          color: #667eea;
        }

        .search-bar {
          flex: 1;
          max-width: 600px;
          display: flex;
          align-items: center;
          gap: 10px;
          background: #f5f7fa;
          padding: 12px 20px;
          border-radius: 25px;
        }

        .search-bar input {
          flex: 1;
          border: none;
          background: none;
          outline: none;
          font-size: 16px;
        }

        .header-actions {
          display: flex;
          gap: 15px;
        }

        .icon-btn {
          background: none;
          border: none;
          cursor: pointer;
          padding: 10px;
          border-radius: 50%;
          transition: background 0.3s;
          position: relative;
        }

        .icon-btn:hover {
          background: #f5f7fa;
        }

        .cart-badge {
          position: absolute;
          top: 5px;
          right: 5px;
          background: #ef4444;
          color: white;
          font-size: 12px;
          font-weight: 600;
          padding: 2px 6px;
          border-radius: 10px;
          min-width: 20px;
        }

        .category-filter {
          max-width: 1400px;
          margin: 20px auto;
          padding: 0 30px;
          display: flex;
          align-items: center;
          gap: 15px;
        }

        .category-buttons {
          display: flex;
          gap: 10px;
          flex-wrap: wrap;
        }

        .category-btn {
          padding: 8px 20px;
          border: 2px solid #e5e7eb;
          background: white;
          border-radius: 20px;
          cursor: pointer;
          transition: all 0.3s;
          font-weight: 500;
        }

        .category-btn:hover {
          border-color: #667eea;
          color: #667eea;
        }

        .category-btn.active {
          background: #667eea;
          color: white;
          border-color: #667eea;
        }

        .main-content {
          max-width: 1400px;
          margin: 0 auto;
          padding: 30px;
        }

        .products-grid {
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
          gap: 25px;
        }

        .product-card {
          background: white;
          border-radius: 16px;
          overflow: hidden;
          box-shadow: 0 4px 15px rgba(0,0,0,0.08);
          transition: all 0.3s;
        }

        .product-card:hover {
          transform: translateY(-8px);
          box-shadow: 0 12px 30px rgba(0,0,0,0.15);
        }

        .product-image {
          position: relative;
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          height: 200px;
          display: flex;
          align-items: center;
          justify-content: center;
        }

        .product-emoji {
          font-size: 80px;
        }

        .favorite-btn {
          position: absolute;
          top: 15px;
          right: 15px;
          background: white;
          border: none;
          padding: 8px;
          border-radius: 50%;
          cursor: pointer;
          transition: all 0.3s;
          box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .favorite-btn:hover {
          transform: scale(1.1);
        }

        .favorite-btn.active {
          background: #fee;
        }

        .product-info {
          padding: 20px;
        }

        .product-category {
          color: #667eea;
          font-size: 12px;
          font-weight: 600;
          text-transform: uppercase;
          letter-spacing: 0.5px;
        }

        .product-name {
          margin: 8px 0;
          font-size: 18px;
          font-weight: 600;
          color: #333;
        }

        .product-rating {
          display: flex;
          align-items: center;
          gap: 5px;
          margin: 10px 0;
          font-size: 14px;
        }

        .reviews {
          color: #999;
        }

        .product-footer {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-top: 15px;
        }

        .product-price {
          font-size: 24px;
          font-weight: 700;
          color: #333;
        }

        .add-to-cart-btn {
          background: #667eea;
          color: white;
          border: none;
          padding: 10px 20px;
          border-radius: 25px;
          cursor: pointer;
          display: flex;
          align-items: center;
          gap: 5px;
          font-weight: 600;
          transition: all 0.3s;
        }

        .add-to-cart-btn:hover {
          background: #5568d3;
          transform: scale(1.05);
        }

        .cart-overlay {
          position: fixed;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          background: rgba(0,0,0,0.5);
          opacity: 0;
          pointer-events: none;
          transition: opacity 0.3s;
          z-index: 200;
        }

        .cart-overlay.active {
          opacity: 1;
          pointer-events: all;
        }

        .cart-sidebar {
          position: fixed;
          top: 0;
          right: -450px;
          width: 450px;
          height: 100vh;
          background: white;
          box-shadow: -5px 0 20px rgba(0,0,0,0.1);
          transition: right 0.3s;
          z-index: 201;
          display: flex;
          flex-direction: column;
        }

        .cart-sidebar.active {
          right: 0;
        }

        .cart-header {
          padding: 25px;
          border-bottom: 1px solid #e5e7eb;
          display: flex;
          justify-content: space-between;
          align-items: center;
        }

        .cart-header h2 {
          font-size: 24px;
          color: #333;
        }

        .close-btn {
          background: none;
          border: none;
          cursor: pointer;
          padding: 5px;
        }

        .cart-items {
          flex: 1;
          overflow-y: auto;
          padding: 20px;
        }

        .empty-cart {
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          height: 100%;
          gap: 15px;
          color: #999;
        }

        .cart-item {
          display: flex;
          gap: 15px;
          padding: 15px;
          background: #f9fafb;
          border-radius: 12px;
          margin-bottom: 15px;
        }

        .cart-item-emoji {
          font-size: 48px;
        }

        .cart-item-details {
          flex: 1;
        }

        .cart-item-details h4 {
          font-size: 16px;
          margin-bottom: 5px;
        }

        .cart-item-price {
          color: #667eea;
          font-weight: 600;
        }

        .cart-item-actions {
          display: flex;
          flex-direction: column;
          gap: 10px;
          align-items: flex-end;
        }

        .quantity-controls {
          display: flex;
          align-items: center;
          gap: 10px;
          background: white;
          border-radius: 20px;
          padding: 5px;
        }

        .quantity-controls button {
          background: #667eea;
          color: white;
          border: none;
          width: 28px;
          height: 28px;
          border-radius: 50%;
          cursor: pointer;
          display: flex;
          align-items: center;
          justify-content: center;
        }

        .quantity-controls span {
          min-width: 30px;
          text-align: center;
          font-weight: 600;
        }

        .remove-btn {
          background: none;
          border: none;
          color: #ef4444;
          cursor: pointer;
          padding: 5px;
        }

        .cart-footer {
          padding: 25px;
          border-top: 1px solid #e5e7eb;
        }

        .cart-total {
          display: flex;
          justify-content: space-between;
          margin-bottom: 20px;
          font-size: 18px;
          font-weight: 600;
        }

        .total-amount {
          color: #49b36dff;
          font-size: 24px;
        }

        .checkout-btn {
          width: 100%;
          background: #9fea66ff;
          color: white;
          border: none;
          padding: 15px;
          border-radius: 12px;
          font-size: 16px;
          font-weight: 600;
          cursor: pointer;
          display: flex;
          align-items: center;
          justify-content: center;
          gap: 10px;
          transition: background 0.3s;
        }

        .checkout-btn:hover {
          background: #5568d3;
        }

        @media (max-width: 768px) {
          .header-content {
            flex-wrap: wrap;
            padding: 15px;
          }

          .search-bar {
            order: 3;
            width: 100%;
            max-width: none;
          }

          .cart-sidebar {
            width: 100%;
            right: -100%;
          }

          .products-grid {
            grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
            gap: 15px;
          }

          .main-content {
            padding: 15px;
          }
        }
      `}</style>
    </div>
  );
};

export default function App() {
  return (
    <CartProvider>
      <ECommerce />
    </CartProvider>
  );
}