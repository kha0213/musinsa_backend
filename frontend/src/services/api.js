import axios from 'axios';

const API_BASE_URL = process.env.NODE_ENV === 'production' 
  ? '/api' 
  : 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 응답 인터셉터 - 에러 처리
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.data?.error) {
      throw new Error(error.response.data.error);
    }
    throw error;
  }
);

export const brandService = {
  // 모든 브랜드 조회
  getAllBrands: () => api.get('/brands'),
  
  // 브랜드 등록
  createBrand: (data) => {
    return api.post('/brands', {
      name: data.name,
      description: data.description || null
    });
  },
};

export const homeService = {
  // 홈 페이지 데이터 조회
  getBrandCategories: () => api.get('/home/brand-categories'),
};

export const productService = {
  // 브랜드별 상품 저장
  saveProductsByBrand: (brandId, products) => 
    api.post(`/products/brand/${brandId}`, products),
  
  // 브랜드별 상품 조회 (관리용)
  getProductsByBrand: (brandId) => 
    api.get(`/products/brand/${brandId}`),
    
  // 카테고리별 최저가격 브랜드와 상품가격 조회
  getCategoryLowPriceBrands: () =>
    api.get('/products/lowest-by-category'),
    
  // 단일 브랜드로 모든 카테고리 구매 시 최저가 브랜드 조회
  getLowestBrandTotal: () =>
    api.get('/products/lowest-brand-total'),
    
  // 카테고리별 최저/최고가 브랜드 조회
  getCategoryPriceRange: (categoryName) =>
    api.get(`/products/category/price-range?categoryName=${encodeURIComponent(categoryName)}`),
};

export const categoryService = {
  // 모든 카테고리 조회
  getAllCategories: () => api.get('/categories'),
};

export default api;
