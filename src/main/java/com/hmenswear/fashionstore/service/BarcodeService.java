package com.hmenswear.fashionstore.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;
import java.nio.file.*;

@Service
public class BarcodeService {
    public String generate(String code){
        try{
            Path dir=Paths.get("uploads","barcodes"); Files.createDirectories(dir);
            Path file=dir.resolve(code+".png");
            var matrix=new MultiFormatWriter().encode(code, BarcodeFormat.CODE_128, 480, 120);
            MatrixToImageWriter.writeToPath(matrix,"PNG",file);
            return "/uploads/barcodes/"+code+".png";
        }catch(Exception e){ return null; }
    }
}
