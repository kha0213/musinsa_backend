import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { productService, categoryService } from '../services/api';
import './CategoryPriceRangePage.css';

const CategoryPriceRangePage = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [categoriesLoading, setCategoriesLoading] = useState(true);

  useEffect(() => {
    loadCategories();
  }, []);

  const loadCategories = async () => {
    try {
      setCategoriesLoading(true);
      const response = await categoryService.getAllCategories();
      setCategories(response.data);
      if (response.data.length > 0) {
        setSelectedCategory(response.data[0].name);
      }
    } catch (err) {
      console.error('Error loading categories:', err);
      setError('카테고리 목록을 불러오는 중 오류가 발생했습니다.');
    } finally {
      setCategoriesLoading(false);
    }
  };

  const loadPriceRange = async (categoryName) => {
    if (!categoryName) return;
    
    try {
      setLoading(true);
      setError(null);
      
      const response = await productService.getCategoryPriceRange(categoryName);
      setData(response.data);
      
    } catch (err) {
      console.error('Error loading price range:', err);
      setError(`데이터를 불러오는 중 오류가 발생했습니다: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  const handleCategoryChange = (e) => {
    const categoryName = e.target.value;
    setSelectedCategory(categoryName);
    if (categoryName) {
      loadPriceRange(categoryName);
    }
  };

  const handleSearch = () => {
    if (selectedCategory) {
      loadPriceRange(selectedCategory);
    }
  };

  const formatPrice = (price) => {
    return price?.toLocaleString() || '0';
  };

  if (categoriesLoading) {
    return <div className="loading">카테고리 목록을 불러오는 중...</div>;
  }

  return (
    <div className="category-price-range-page">
      <div className="header">
        <h1>카테고리별 최저가격/최고가격 브랜드와 상품 가격 조회</h1>
        <button 
          className="btn btn-secondary"
          onClick={() => navigate('/')}
        >
          홈으로
        </button>
      </div>

      <div className="search-section">
        <div className="search-form">
          <div className="form-group">
            <label htmlFor="category-select">카테고리 선택:</label>
            <select 
              id="category-select"
              value={selectedCategory} 
              onChange={handleCategoryChange}
              className="category-select"
            >
              <option value="">카테고리를 선택하세요</option>
              {categories.map((category) => (
                <option key={category.id} value={category.name}>
                  {category.name}
                </option>
              ))}
            </select>
          </div>
          <button 
            onClick={handleSearch}
            disabled={!selectedCategory || loading}
            className="btn btn-primary search-btn"
          >
            {loading ? '조회 중...' : '조회'}
          </button>
        </div>
      </div>

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}

      {data && (
        <div className="results-section">
          <div className="category-title">
            <h2>카테고리: {data.category}</h2>
          </div>

          <div className="price-tables">
            {/* 최저가 테이블 */}
            <div className="price-table-container">
              <h3 className="table-title lowest-title">최저가</h3>
              <div className="table-wrapper">
                <table className="price-table lowest-table">
                  <thead>
                    <tr>
                      <th>브랜드</th>
                      <th>가격</th>
                    </tr>
                  </thead>
                  <tbody>
                    {data.lowestPrice && data.lowestPrice.length > 0 ? (
                      data.lowestPrice.map((item, index) => (
                        <tr key={index}>
                          <td className="brand-name">{item.brandName}</td>
                          <td className="price">{formatPrice(item.price)}</td>
                        </tr>
                      ))
                    ) : (
                      <tr>
                        <td colSpan="2" className="no-data">
                          데이터가 없습니다.
                        </td>
                      </tr>
                    )}
                  </tbody>
                </table>
              </div>
            </div>

            {/* 최고가 테이블 */}
            <div className="price-table-container">
              <h3 className="table-title highest-title">최고가</h3>
              <div className="table-wrapper">
                <table className="price-table highest-table">
                  <thead>
                    <tr>
                      <th>브랜드</th>
                      <th>가격</th>
                    </tr>
                  </thead>
                  <tbody>
                    {data.highestPrice && data.highestPrice.length > 0 ? (
                      data.highestPrice.map((item, index) => (
                        <tr key={index}>
                          <td className="brand-name">{item.brandName}</td>
                          <td className="price">{formatPrice(item.price)}</td>
                        </tr>
                      ))
                    ) : (
                      <tr>
                        <td colSpan="2" className="no-data">
                          데이터가 없습니다.
                        </td>
                      </tr>
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div className="summary">
            <p>선택한 카테고리에서 가장 저렴한 브랜드와 가장 비싼 브랜드의 상품 가격을 보여줍니다.</p>
            <p>동일한 가격을 가진 여러 브랜드가 있을 경우 모두 표시됩니다.</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default CategoryPriceRangePage;
