// path: crs-frontend/src/api/courseApi.ts

import axiosClient from './axiosClient';
import type { Course, PagedResponse, CourseFormValues } from '../types/course';

// Lấy danh sách môn học (giữ nguyên từ Buổi 5)
export const getCourses = (
    keyword: string = '',
    page: number = 0,
    size: number = 10
) => {
    return axiosClient.get<PagedResponse<Course>>(
        '/api/courses',
        {
            params: {
                keyword,
                page,
                size,
            },
        }
    );
};

// Hàm phụ trợ chuyển đổi dữ liệu từ form sang định dạng Payload gửi lên Server
const toPayload = (values: CourseFormValues) => ({
    tenMonHoc: values.tenMonHoc.trim(),
    soTinChi: Number(values.soTinChi),
    soChoToiDa: Number(values.soChoToiDa),
});

// Thêm mới môn học
export const createCourse = (values: CourseFormValues) => {
    return axiosClient.post<Course>('/api/courses', toPayload(values));
};

// Cập nhật thông tin môn học
export const updateCourse = (id: number, values: CourseFormValues) => {
    return axiosClient.put<Course>(`/api/courses/${id}`, toPayload(values));
};

// Xóa môn học
export const deleteCourse = (id: number) => {
    return axiosClient.delete(`/api/courses/${id}`);
};