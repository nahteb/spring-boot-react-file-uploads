package com.example.springbootfileuploads;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootfileuploads.storage.StorageFileNotFoundException;
import com.example.springbootfileuploads.storage.StorageService;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private StorageService storageService;

  @Test
  public void shouldListAllFiles() throws Exception {
    given(this.storageService.loadAll())
        .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

    this.mvc.perform(get("/getFiles")).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$..[0]")
            .value("http://localhost/files/first.txt"))
        .andExpect(MockMvcResultMatchers.jsonPath("$..[1]")
            .value("http://localhost/files/second.txt"));
  }

  @Test
  public void shouldSaveUploadedFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
        "text/plain", "Spring Framework".getBytes());
    this.mvc.perform(multipart("/uploadFile").file(multipartFile))
        .andExpect(status().isOk());

    then(this.storageService).should().store(multipartFile);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void should404WhenMissingFile() throws Exception {
    given(this.storageService.loadAsResource("test.txt"))
        .willThrow(StorageFileNotFoundException.class);

    this.mvc.perform(get("/files/test.txt")).andExpect(status().isNotFound());
  }

}
