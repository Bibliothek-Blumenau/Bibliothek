package com.jovemprogramador.bibliothek.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.jovemprogramador.bibliothek.model.User;
import com.jovemprogramador.bibliothek.repository.UserRepository;

@RestController
@RequestMapping("/api/upload/perfil")
public class PerfilUploadController {

	@Value("${bibliothek.dominio}")
	String pathVar2User;

	private static String UPLOADED_FOLDER0 = "src/main/resources/static/Imagens/Perfil/ImagensGif/";
	private static String UPLOADED_FOLDER1 = "src/main/resources/static/Imagens/Perfil/ImagensJPG/";
	private static String UPLOADED_FOLDER2 = "src/main/resources/static/Imagens/Perfil/ImagensPNG/";
	private static String UPLOADED_FOLDER3 = "src/main/resources/static/Imagens/Perfil/ImagensSvg/";
	private static String UPLOADED_FOLDER4 = "src/main/resources/static/Imagens/Perfil/ImagensWebp/";
	private static String PROFILE_DEFAULTPHOTO = "src/main/resources/static/Imagens/";

	final Logger log = LoggerFactory.getLogger(PerfilUploadController.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/imagens", method = RequestMethod.PUT)
	public ResponseEntity<String> putUserImagem(@RequestParam("file") MultipartFile file, String matricula,
			String roles) {
		DateTimeFormatter timeDateFormat = DateTimeFormatter.ofPattern("dd_MM_yyyy");
		String nomeImagem = null;
		String novoNome = null;
		String uploadImagePath = null;
		String asciiGrant = null;
		String url = null;

		log.info("Uma Imagem Sofreu Upload: " + file.getOriginalFilename() + ", Matricula do user: " + matricula);

		log.info("Role of User: " + roles);

		if (file.isEmpty()) {
			throw new MultipartException("Por favor selecione uma imagem");
		}

		User user = userRepository.findByMatricula(matricula);

		try {
			byte[] bytes = file.getBytes();

			if (file.getOriginalFilename().endsWith(".gif")) {
				Path path = Paths.get(UPLOADED_FOLDER0);
				if (!Files.exists(path)) {
					Files.createDirectory(path);
				}
				nomeImagem = file.getOriginalFilename();
				asciiGrant = toAscii(nomeImagem);
				novoNome = user.getMatricula() + "-" + LocalDateTime.now().format(timeDateFormat).toString() + "-"
						+ asciiGrant; // nome na imagem
				Files.write(path.resolve(novoNome), bytes);
				uploadImagePath = UPLOADED_FOLDER0 + novoNome;
			} else if (file.getOriginalFilename().endsWith(".jpeg") || file.getOriginalFilename().endsWith(".jpg")
					|| file.getOriginalFilename().endsWith(".jpe")) {
				Path path = Paths.get(UPLOADED_FOLDER1);
				if (!Files.exists(path)) {
					Files.createDirectory(path);
				}
				nomeImagem = file.getOriginalFilename();
				asciiGrant = toAscii(nomeImagem);
				novoNome = user.getMatricula() + "-" + LocalDateTime.now().format(timeDateFormat).toString() + "-"
						+ asciiGrant;
				Files.write(path.resolve(novoNome), bytes);
				uploadImagePath = UPLOADED_FOLDER1 + novoNome;
			} else if (file.getOriginalFilename().endsWith(".png")) {
				Path path = Paths.get(UPLOADED_FOLDER2);
				if (!Files.exists(path)) {
					Files.createDirectory(path);
				}
				nomeImagem = file.getOriginalFilename();
				asciiGrant = toAscii(nomeImagem);
				novoNome = user.getMatricula() + "-" + LocalDateTime.now().format(timeDateFormat).toString() + "-"
						+ asciiGrant;
				Files.write(path.resolve(novoNome), bytes);
				uploadImagePath = UPLOADED_FOLDER2 + novoNome;
			} else if (file.getOriginalFilename().endsWith(".svg")) {
				Path path = Paths.get(UPLOADED_FOLDER3);
				if (!Files.exists(path)) {
					Files.createDirectory(path);
				}
				nomeImagem = file.getOriginalFilename();
				asciiGrant = toAscii(nomeImagem);
				novoNome = user.getMatricula() + "-" + LocalDateTime.now().format(timeDateFormat).toString() + "-"
						+ asciiGrant;
				Files.write(path.resolve(novoNome), bytes);
				uploadImagePath = UPLOADED_FOLDER3 + novoNome;
			} else if (file.getOriginalFilename().endsWith(".webp")) {
				Path path = Paths.get(UPLOADED_FOLDER4);
				if (!Files.exists(path)) {
					Files.createDirectory(path);
				}
				nomeImagem = file.getOriginalFilename();
				asciiGrant = toAscii(nomeImagem);
				novoNome = user.getMatricula() + "-" + LocalDateTime.now().format(timeDateFormat).toString() + "-"
						+ asciiGrant;
				Files.write(path.resolve(novoNome), bytes);
				uploadImagePath = UPLOADED_FOLDER4 + novoNome;
			}

			log.info("O caminho da imagem postada: " + uploadImagePath);
			url = pathVar2User + "/api/upload/perfil/user/imagem/" + matricula; // probleminha de duas barras
			user.setImagePath(uploadImagePath); // guarda o caminho
			user.setFotoPerfil(url);
			userRepository.save(user);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(url); // String simples como retorno
	}

	@RequestMapping(value = "/user/imagem/{matricula}", method = RequestMethod.GET)
	public ResponseEntity<Resource> getUserImagem(@PathVariable String matricula) {
		User user = userRepository.findByMatricula(matricula);
		String imagePath = user.getImagePath();
		String defaultImage = "profile-picture.png";

		if (imagePath == null || !fileExists(imagePath)) {
			user.setImagePath(PROFILE_DEFAULTPHOTO + defaultImage);
			imagePath = user.getImagePath();
		}

		log.info("A Imagem: " + imagePath + ", foi carregada.");

		try {
			Path path = Paths.get(imagePath); // caminho
			Resource resource = new UrlResource(path.toUri());

			if (resource.exists() || resource.isReadable()) {
				String extension = FilenameUtils.getExtension(imagePath).toLowerCase();
				MediaType mediaType = MediaType.IMAGE_JPEG;

				if (extension.equals("gif")) {
					mediaType = MediaType.IMAGE_GIF;
				} else if (extension.equals("jpg") || extension.equals("jpe") || extension.equals("jpeg")) {
					mediaType = MediaType.IMAGE_JPEG; // os jpg's tem o mesmo MIME
				} else if (extension.equals("png")) {
					mediaType = MediaType.IMAGE_PNG;
				} else if (extension.equals("svg") || extension.equals("svg+xml")) {
					mediaType = MediaType.valueOf("image/svg+xml");
				} else if (extension.equals("webp")) {
					mediaType = MediaType.valueOf("image/webp");
				}
				return ResponseEntity.ok().contentType(mediaType).body(resource);
			} else {
				log.error("Arquivo não existe ou não é legivel " + path);
				throw new RuntimeException("Falha ao carregar imagem");
			}
		} catch (MalformedURLException e) {
			log.error("Erro ao criar URL: " + e.getMessage());
			throw new RuntimeException("Falha ao carregar imagem");
		}
	}

	// Eram para ser serviços, com o pouco tempo deixei tudo junto.

	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<?> handleMultipartException(MultipartException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<?> tamanhoUploadException(MaxUploadSizeExceededException e) {
		String errorMessage = "Erro: O tamanho do arquivo excede o limite máximo permitido.";
		return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(errorMessage);
	}

	public String toAscii(String str) {
		// Garantir padrão ASCII
		String asciiStr = str.replaceAll("[^\\p{ASCII}]", "_");
		return asciiStr;
	}

	public boolean fileExists(String filePath) {
		Path path = Paths.get(filePath);
		return Files.exists(path) && Files.isRegularFile(path);
	}

}