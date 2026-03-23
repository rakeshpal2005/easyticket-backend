package com.rakesh.bms.Exception;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Date timestamp;
    private int status;
    private String error;
    private String messege;
    private String path;



}
