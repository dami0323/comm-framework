package co.kr.comm.test.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TextFileService {

	/**
	 * 파일 경로를 받아 전체 내용을 문자열로 읽음
	 */
	public String readFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		validatePath(path);
		return Files.readString(path, StandardCharsets.UTF_8);
	}

	/**
	 * 파일을 라인 단위로 읽어 리스트로 반환
	 */
	public List<String> readFileLines(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		validatePath(path);
		return Files.readAllLines(path, StandardCharsets.UTF_8);
	}

	/**
	 * 경로 유효성 검사
	 */
	private void validatePath(Path path) throws IOException {
		if (!Files.exists(path)) {
			throw new IOException("파일을 찾을 수 없습니다: " + path.toAbsolutePath());
		}
		if (!Files.isRegularFile(path)) {
			throw new IOException("유효한 파일이 아닙니다: " + path.toAbsolutePath());
		}
		if (!path.toString().toLowerCase().endsWith(".txt")) {
			throw new IOException("텍스트 파일(.txt)만 읽기 가능합니다: " + path.getFileName());
		}
	}
}