package co.kr.comm.test.controller;

import co.kr.comm.test.service.TextFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class TextFileController {

	private final TextFileService textFileService;

	/**
	 * GET /api/files/read?path=C:/test/test.txt
	 * 파일 전체 내용을 문자열로 반환
	 */
	@GetMapping("/read")
	public String readFile(@RequestParam("path") String path) {
		try {
			String lines = textFileService.readFile(path);
			return "<pre>" + lines + "</pre>";
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * GET /api/files/read-lines?path=C:/example/sample.txt
	 * 파일을 라인 단위로 읽어 리스트로 반환
	 */
	@GetMapping("/read-lines")
	public String readFileLines(@RequestParam("path") String path) {
		try {
			List<String> lines = textFileService.readFileLines(path);
            return String.join("\n", lines);
		} catch (IOException e) {
			return "";
		}
	}
}