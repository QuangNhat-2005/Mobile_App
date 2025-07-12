package com.example.caoquangnhat_2123110077; // QUAN TRỌNG: Đảm bảo dòng này khớp với tên package của bạn

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // Đây là địa chỉ gốc của API. Mọi endpoint (như "api/games") sẽ được nối vào sau nó.
    // Ví dụ: https://www.freetogame.com/api/games
    private static final String BASE_URL = "https://www.freetogame.com/";

    // Biến static để giữ một thể hiện (instance) duy nhất của Retrofit.
    private static Retrofit retrofit = null;

    // Phương thức public, static để các lớp khác có thể gọi và lấy được ApiService.
    public static ApiService getApiService() {
        // Nếu retrofit chưa được khởi tạo...
        if (retrofit == null) {
            // ...thì khởi tạo nó.
            retrofit = new Retrofit.Builder()
                    // 1. Đặt địa chỉ gốc của API.
                    .baseUrl(BASE_URL)
                    // 2. Chỉ định bộ chuyển đổi. Ở đây ta dùng GsonConverterFactory để
                    //    Retrofit biết rằng nó cần dùng Gson để phân tích JSON.
                    .addConverterFactory(GsonConverterFactory.create())
                    // 3. Xây dựng đối tượng Retrofit.
                    .build();
        }
        // Sau khi có Retrofit, ta dùng nó để tạo ra một thể hiện của ApiService.
        // Retrofit sẽ tự động "implement" cái interface ApiService cho chúng ta.
        return retrofit.create(ApiService.class);
    }
}