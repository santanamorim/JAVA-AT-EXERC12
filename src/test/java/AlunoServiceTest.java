import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AlunoServiceTest {

    private AlunoService alunoService;
    private HttpClient client;

    @BeforeEach
    public void setUp() {
        alunoService = new AlunoService();
        client = HttpClient.newHttpClient();
    }

    @Test
    public void testGetTodosAlunos() {
        List<Aluno> alunos = alunoService.getTodosAlunos();
        assertNotNull(alunos, "A lista de alunos não deve ser nula.");
        assertEquals(3, alunos.size(), "A lista de alunos deve conter 3 alunos.");
    }

    @Test
    public void testGetAlunoById() {
        Aluno aluno = alunoService.getAlunoById(1L);
        assertNotNull(aluno, "O aluno com id 1 deve existir.");
        assertEquals("Ana", aluno.getNome(), "O nome do aluno deve ser 'Ana'.");

        aluno = alunoService.getAlunoById(999L);
        assertNull(aluno, "O aluno com id 999 não deve existir.");
    }

    @Test
    public void testAtualizarAluno() {
        boolean resultado = alunoService.atualizarAluno(1L, "Ana Maria", 9.0);
        assertTrue(resultado, "A atualização do aluno deve ser bem-sucedida.");

        Aluno aluno = alunoService.getAlunoById(1L);
        assertNotNull(aluno, "O aluno com id 1 deve existir.");
        assertEquals("Ana Maria", aluno.getNome(), "O nome do aluno deve ser 'Ana Maria'.");
        assertEquals(9.0, aluno.getNota(), 0.01, "A nota do aluno deve ser 9.0.");
    }

    @Test
    public void testExcluirAluno() {
        boolean resultado = alunoService.excluirAluno(2L);
        assertTrue(resultado, "A exclusão do aluno deve ser bem-sucedida.");

        Aluno aluno = alunoService.getAlunoById(2L);
        assertNull(aluno, "O aluno com id 2 não deve existir.");
    }

    @Test
    public void alunoServiceAlunoIdEncontradoTest() {
        Aluno aluno = alunoService.getAlunoById(1L);
        assertNotNull(aluno, "O aluno com o ID 1 deve ser encontrado.");
        assertEquals("Ana", aluno.getNome(), "O nome do aluno deve ser 'Ana'.");
    }

    @Test
    public void alunoServiceAlunoIdNaoEncontradoTest() {
        Aluno aluno = alunoService.getAlunoById(999L);
        assertNull(aluno, "O aluno com o ID 999 não deve ser encontrado.");
    }

    @Test
    public void testConsultarAlunoHttp() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:4567/alunos/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "O código de status HTTP deve ser 200.");
        assertTrue(response.body().contains("Ana"), "O corpo da resposta deve conter o nome 'Ana'.");
    }

    @Test
    public void testIncluirAlunoHttp() throws Exception {
        String alunoJson = """
        {
            "id": 4,
            "nome": "João",
            "nota": 7.5
        }
        """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:4567/alunos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(alunoJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "O código de status HTTP deve ser 201.");
        assertTrue(response.body().contains("João"), "O corpo da resposta deve conter o nome 'João'.");
    }
}
