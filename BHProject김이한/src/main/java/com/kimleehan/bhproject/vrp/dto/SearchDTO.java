package com.kimleehan.bhproject.vrp.dto;


import com.kimleehan.bhproject.vrp.dto.base.CustomPageable;
import lombok.Data;

@Data
public class SearchDTO {
    private int page;
    private int size;
    private CustomPageable pageable;
    public void setSize(int size) {
        this.size = size;
        if (pageable == null) {
            pageable = new CustomPageable();
        }
        pageable.setSize(size);
    }
    public void setPage(int page) {
        this.page = page;
        if (pageable == null) {
            pageable = new CustomPageable();
        }
        pageable.setPage(page);
    }

}
