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
    const formData = new FormData();
    formData.append('name', data.name);
    if (data.description) {
      formData.append('description', data.description);
    }
    return api.post('/brands', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
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
};

export default api;
