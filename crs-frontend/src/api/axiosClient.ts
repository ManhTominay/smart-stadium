import axios from 'axios';

const axiosClient = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request Interceptor: Tự động gắn Token vào Header nếu có trong localStorage
axiosClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('crs_token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// Response Interceptor: Bắt lỗi 401 để tự động xóa token và chuyển hướng về trang /login
axiosClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (axios.isAxiosError(error) && error.response?.status === 401) {
            // Xóa thông tin đăng nhập cũ
            localStorage.removeItem('crs_token');
            localStorage.removeItem('crs_user');

            // Chuyển hướng người dùng về trang đăng nhập nếu chưa ở /login
            if (window.location.pathname !== '/login') {
                window.location.href = '/login';
            }
        }
        return Promise.reject(error);
    }
);

export default axiosClient;