// Interface đại diện cho một môn học từ Backend trả về
export interface Course {
    id: number;
    tenMonHoc: string;
    soTinChi: number;
    soChoToiDa: number;
    soChoConLai?: number;
}

// Interface đại diện cho dữ liệu nhập từ Form
export interface CourseFormValues {
    tenMonHoc: string;
    soTinChi: string;
    soChoToiDa: string;
}

// Interface cho dữ liệu phân trang trả về từ API
export interface PagedResponse<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

// Giá trị khởi tạo mặc định cho Form
export const emptyCourseForm: CourseFormValues = {
    tenMonHoc: '',
    soTinChi: '',
    soChoToiDa: '',
};