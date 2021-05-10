package com.example.demo.ocr;


import com.example.demo.services.FormServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@CrossOrigin(origins = "*")

    @RequestMapping("/ocr")
    @RestController
    public class Ocr {


    @Autowired
    private FormServices formServices;
    @PostMapping("/getOcr")
    public List getOcr(@RequestParam("file") MultipartFile file)  {



     return   formServices.pdfRead(file);
    }
}





