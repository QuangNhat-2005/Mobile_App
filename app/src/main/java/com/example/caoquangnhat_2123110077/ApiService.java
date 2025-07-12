package com.example.caoquangnhat_2123110077; // QUAN TRỌNG: Đảm bảo dòng này khớp với tên package của bạn

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    /**
     * Định nghĩa một yêu cầu GET đến endpoint "api/games".
     *
     * @GET("api/games"): Annotation này cho Retrofit biết:
     * 1. Phương thức HTTP là GET.
     * 2. Đường dẫn của API là "api/games". Đường dẫn này sẽ được nối vào sau Base URL
     *    mà chúng ta sẽ cấu hình ở bước tiếp theo.
     *    (Ví dụ: Base URL "https://www.freetogame.com/" + "api/games")
     *
     * Call<List<GameApiResponse>>: Đây là kiểu dữ liệu trả về của yêu cầu.
     * 1. List<GameApiResponse>: Chúng ta mong đợi server trả về một danh sách (JSON array)
     *    và mỗi phần tử trong danh sách đó có thể được chuyển đổi thành một đối tượng GameApiResponse.
     * 2. Call<>: Retrofit bọc kết quả này trong một đối tượng Call, cho phép chúng ta
     *    thực thi yêu cầu một cách đồng bộ hoặc bất đồng bộ.
     */
    @GET("api/games")
    Call<List<GameApiResponse>> getGames();

}