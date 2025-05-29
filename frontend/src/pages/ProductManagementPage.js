import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {brandService, categoryService, homeService, productService} from '../services/api';
import './ProductManagementPage.css';

const ProductManagementPage = () => {
  const navigate = useNavigate();
  const [brands, setBrands] = useState([]);
  const [categories, setCategories] = useState([]);
  const [brandCategories, setBrandCategories] = useState([]);
  const [productInputs, setProductInputs] = useState({});
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState({});
  const [error, setError] = useState(null);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(null);
      
      console.log('Loading data...');
      
      const [brandsResponse, categoriesResponse, homeResponse] = await Promise.all([
        brandService.getAllBrands(),
        categoryService.getAllCategories(),
        homeService.getBrandCategories()
      ]);
      
      console.log('Brands:', brandsResponse.data);
      console.log('Categories:', categoriesResponse.data);
      console.log('Home data:', homeResponse.data);
      
      setBrands(brandsResponse.data || []);
      setCategories(categoriesResponse.data || []);
      setBrandCategories(homeResponse.data || []);
      
      // 기존 상품 데이터를 input에 초기화 (홈 데이터에서 추출)
      const initialInputs = {};
      (homeResponse.data || []).forEach(brandCategory => {
        const brandId = brandCategory.brandId;
        (brandCategory.categoryProducts || []).forEach(categoryProduct => {
          const categoryId = categoryProduct.category?.id;
          const price = categoryProduct.product?.price;
          
          if (price && categoryId) {
            const key = `${brandId}-${categoryId}`;
            initialInputs[key] = {
              price: price.toString()
            };
          }
        });
      });
      
      console.log('Initial inputs:', initialInputs);
      setProductInputs(initialInputs);
      
    } catch (err) {
      console.error('Error loading data:', err);
      setError(`데이터를 불러오는 중 오류가 발생했습니다: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (brandId, categoryId, value) => {
    const key = `${brandId}-${categoryId}`;
    setProductInputs(prev => ({
      ...prev,
      [key]: {
        price: value
      }
    }));
  };

  const handleSaveBrand = async (brandId, brandName) => {
    try {
      setSaving(prev => ({ ...prev, [brandId]: true }));
      
      // 저장할 상품 데이터 준비
      const productsToSave = [];
      categories.forEach(category => {
        const key = `${brandId}-${category.id}`;
        const input = productInputs[key];
        
        if (input && input.price) {
          productsToSave.push({
            categoryId: category.id,
            price: parseFloat(input.price)
          });
        }
      });
      
      if (productsToSave.length > 0) {
        await productService.saveProductsByBrand(brandId, productsToSave);
        alert(`${brandName} 브랜드의 상품이 성공적으로 저장되었습니다.`);
        
        // 데이터 새로고침
        await loadData();
      } else {
        alert('저장할 상품 데이터가 없습니다.');
      }
      
    } catch (err) {
      alert(err);
    } finally {
      setSaving(prev => ({ ...prev, [brandId]: false }));
    }
  };

  const formatPrice = (price) => {
    return price?.toLocaleString() || '';
  };

  const getCategoryNames = () => {
    return categories?.map(category => category.name) || [];
  };

  if (loading) return <div className="loading">데이터를 불러오는 중...</div>;
  if (error) return <div className="error">{error}</div>;
  if (!categories.length || !brands.length) {
    return <div className="loading">데이터를 불러오는 중...</div>;
  }

  return (
    <div className="product-management-page">
      <div className="header">
        <h1>상품 관리</h1>
        <div className="header-buttons">
          <button 
            className="btn btn-secondary"
            onClick={() => navigate('/admin/brands')}
          >
            브랜드 관리
          </button>
          <button 
            className="btn btn-secondary"
            onClick={() => navigate('/')}
          >
            홈으로
          </button>
        </div>
      </div>

      <div className="table-container">
        <table className="product-table">
          <thead>
            <tr>
              <th>브랜드</th>
              {getCategoryNames().map((categoryName, index) => (
                <th key={index}>{categoryName}</th>
              ))}
              <th>저장</th>
            </tr>
          </thead>
          <tbody>
            {brands.map((brand, index) => (
              <tr key={index}>
                <td className="brand-name">{brand.name}</td>
                {categories.map((category, idx) => {
                  const key = `${brand.id}-${category.id}`;
                  const inputValue = productInputs[key];
                  
                  return (
                    <td key={idx} className="input-cell">
                      <input
                        type="number"
                        placeholder="가격"
                        className="price-input"
                        value={inputValue?.price || ''}
                        onChange={(e) => handleInputChange(
                          brand.id, 
                          category.id, 
                          e.target.value
                        )}
                      />
                    </td>
                  );
                })}
                <td className="save-cell">
                  <button
                    className="btn btn-primary"
                    onClick={() => handleSaveBrand(brand.id, brand.name)}
                    disabled={saving[brand.id]}
                  >
                    {saving[brand.id] ? '저장 중...' : '저장'}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ProductManagementPage;
