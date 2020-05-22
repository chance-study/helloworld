package org.chance.simple.controller;

import org.chance.simple.exception.BizException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * HelloController
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2020/5/20
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @GetMapping("/random")
    public ResponseEntity<byte[]> random(@RequestParam String action, @RequestParam Integer seed) throws IOException {

        if ("exception".equals(action)) {
            throw new BizException();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // https://unsplash.it/1600/900?random
        BufferedImage bufferedImage = ImageIO.read(new URL("https://i.picsum.photos/id/93/1600/900.jpg"));

        ImageIO.write(bufferedImage, "jpg", out);

        // 设置一个head
        HttpHeaders headers = new HttpHeaders();
        //设置ContentType的值 IMAGE_JPEG在浏览器返回图片
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);

    }

}
