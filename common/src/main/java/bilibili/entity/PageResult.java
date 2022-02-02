package bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Integer total;
    private Integer page;
    private Integer size;
    private List<T> data;

    public PageResult(Integer total, List<T> data) {
        this.total = total;
        this.data = data;
    }
}
