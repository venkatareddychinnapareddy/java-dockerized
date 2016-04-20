package es.joseoc.java.dockerized;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.newBufferedReader;
import static java.time.ZoneOffset.UTC;
import static org.apache.commons.io.FilenameUtils.EXTENSION_SEPARATOR_STR;
import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.IOUtils.toInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.core.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Worker {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

	private final Path inputFile;
	private final Path outputFolder;
	private transient String contentTransformed;

	protected Worker(String inputFile, String outputFolder) {
		this.inputFile = Paths.get(inputFile);
		this.outputFolder = Paths.get(outputFolder);
	}

	protected void transformData() {
		checkInputFileValid();
		String content = readInputFile();
		contentTransformed = transformContent(content);
	}

	private void checkInputFileValid() {
		if (!Files.exists(inputFile)) {
			throw new RuntimeException(format("The file %s does not exist", inputFile.toString()));
		}
		if (!Files.isRegularFile(inputFile)) {
			throw new RuntimeException(format("The file %s is not a regular file", inputFile.toString()));
		}
		if (!Files.isReadable(inputFile)) {
			throw new RuntimeException(format("The file %s is not readable", inputFile.toString()));
		}		
	}

	private String readInputFile() {
		try {
			return IOUtils.toString(newBufferedReader(inputFile));
		} catch (IOException e) {
			new RuntimeException(format("Error reading the file %s", inputFile.toString()), e);
		}
		return null;
	}

	private String transformContent(String content) {
		return WordUtils.swapCase(content);
	}

	protected void generateResult() {
		checkOutputValidFolder();
		Path outputFile = buildOutputFilePath();
		copyToOutput(outputFile);		
	}

	private Path buildOutputFilePath() {
		String filename = getBaseName(inputFile.toString()) + EXTENSION_SEPARATOR_STR + now() + EXTENSION_SEPARATOR_STR + getExtension(inputFile.toString());
		Path outputFile = Paths.get(outputFolder.toString(), filename);
		LOGGER.debug("output file: " + outputFile.toString());
		return outputFile;
	}

	private String now() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH_mm_ss.SSS'Z'").withZone(UTC).format(ZonedDateTime.now(UTC));
	}

	private void copyToOutput(Path outputFile) {
		try (InputStream in = readContentTransformed()) {
			Files.copy(in, outputFile);
		} catch (IOException e) {
			throw new RuntimeException(format("Error copying the content to the file %s", outputFile.toString()), e);
		}
	}

	private InputStream readContentTransformed() {
		return toInputStream(contentTransformed, UTF_8); 
	}

	private void checkOutputValidFolder() {
		if (!Files.exists(outputFolder)) {
			try {
				Files.createDirectories(outputFolder);
			} catch (IOException e) {
				throw new RuntimeException(format("Error creating the output folder '%s'", outputFolder.toString()), e);
			}
			LOGGER.debug(format("The folder %s has been created", outputFolder.toString()));
		} else {
			if (!Files.isDirectory(outputFolder)) {
				throw new RuntimeException(format("The output folder given '%s' is not a directory", outputFolder.toString()));
			}
			if (!Files.isWritable(outputFolder)) {
				throw new RuntimeException(format("The output folder given '%s' is not writable", outputFolder.toString()));
			}			
		}
	}

	public static void transformAndGenerateResult(String inputFile, String outputFolder) {
		Worker worker = new Worker(inputFile, outputFolder);
		worker.transformData();
		worker.generateResult();
	}
}
