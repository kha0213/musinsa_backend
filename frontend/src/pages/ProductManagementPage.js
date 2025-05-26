import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { brandService, productService, homeService } from '../services/api';
import './ProductManagementPage.css';

const ProductManagementPage = () => {
  const navigate = useNavigate();
  const [brands, setBrands] = useState([]);
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
      const [brandsResponse, categoriesResponse] = await Promise.all([
        brandService.getAllBrands(),
        homeService.getBrandCategories()
      ]);
      
      setBrands(brandsResponse.data);
      setBrandCategories(categoriesResponse.data);
      
      // 각 브랜드별로 상품 데이터를 개별 조회
      const initialInputs = {};
      
      // 모든 브랜드에 대해 상품 데이터 로드
      for (const brandCategory of categoriesResponse.data) {
        try {
          const productResponse = await productService.getProductsByBrand(brandCategory.brandId);
          const products = productResponse.data;
          
          // 상품 데이터를 input에 설정
          products.forEach(product => {
            if (product.price) {
              const key = `${product.brandId}-${product.categoryId}`;
              initialInputs[key] = {
                price: product.price.toString()
              };
            }
          });
        } catch (err) {
          console.log(`브랜드 ${brandCategory.brandId}의 상품 데이터 로드 실패:`, err);
          // 개별 브랜드 오류는 무시하고 계속
        }
      }
      
      setProductInputs(initialInputs);
      
    } catch (err) {
      setError('데이터를 불러오는 중 오류가 발생했습니다.');
      console.error('Error loading data:', err);
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
      
      // 해당 브랜드의 카테고리들 찾기
      const brandCategory = brandCategories.find(bc => bc.brandId === brandId);
      if (!brandCategory) return;
      
      // 저장할 상품 데이터 준비
      const productsToSave = [];
      brandCategory.categoryProducts?.forEach(categoryProduct => {
        const categoryId = categoryProduct.category?.id;
        const key = `${brandId}-${categoryId}`;
        const input = productInputs[key];
        
        if (input && input.price) {
          productsToSave.push({
            categoryId: categoryId,
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
      console.error('Error saving products:', err);
      alert('상품 저장 중 오류가 발생했습니다.');
    } finally {
      setSaving(prev => ({ ...prev, [brandId]: false }));
    }
  };

  const formatPrice = (price) => {
    return price?.toLocaleString() || '';
  };

  const getCategoryNames = () => {
    return brandCategories[0]?.categoryProducts?.map(cp => cp.category?.name) || [];
  };

  if (loading) return <div className="loading">데이터를 불러오는 중...</div>;
  if (error) return <div className="error">{error}</div>;

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
            {brandCategories.map((brandCategory, index) => (
              <tr key={index}>
                <td className="brand-name">{brandCategory.brandName}</td>
                {brandCategory.categoryProducts?.map((categoryProduct, idx) => {
                  const categoryId = categoryProduct.category?.id;
                  const key = `${brandCategory.brandId}-${categoryId}`;
                  const inputValue = productInputs[key];
                  
                  return (
                    <td key={idx} className="input-cell">
                      <input
                        type="number"
                        placeholder="가격"
                        className="price-input"
                        value={inputValue?.price || ''}
                        onChange={(e) => handleInputChange(
                          brandCategory.brandId, 
                          categoryId, 
                          e.target.value
                        )}
                      />
                    </td>
                  );
                })}
                <td className="save-cell">
                  <button
                    className="btn btn-primary"
                    onClick={() => handleSaveBrand(brandCategory.brandId, brandCategory.brandName)}
                    disabled={saving[brandCategory.brandId]}
                  >
                    {saving[brandCategory.brandId] ? '저장 중...' : '저장'}
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
