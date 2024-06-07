package com.bqt.newspaper.payload;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileResponse {
    private List<String> names;

}
