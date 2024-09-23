import static spark.Spark.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlunoController {

    private AlunoService alunoService;
    private ObjectMapper objectMapper;

    public AlunoController() {
        alunoService = new AlunoService();
        objectMapper = new ObjectMapper();
        configurarRotas();
    }

    private void configurarRotas() {
        get("/alunos", (req, res) -> {
            res.type("application/json");
            return objectMapper.writeValueAsString(alunoService.getTodosAlunos());
        });

        get("/alunos/:id", (req, res) -> {
            res.type("application/json");
            long id = Long.parseLong(req.params(":id"));
            Aluno aluno = alunoService.getAlunoById(id);
            if (aluno != null) {
                return objectMapper.writeValueAsString(aluno);
            } else {
                res.status(404);
                return objectMapper.writeValueAsString("Aluno não encontrado.");
            }
        });

        post("/alunos", (req, res) -> {
            res.type("application/json");
            Aluno novoAluno = objectMapper.readValue(req.body(), Aluno.class);
            alunoService.adicionarAluno(novoAluno);
            res.status(201);
            return objectMapper.writeValueAsString(novoAluno);
        });

        put("/alunos/:id", (req, res) -> {
            res.type("application/json");
            long id = Long.parseLong(req.params(":id"));
            Aluno alunoAtualizado = objectMapper.readValue(req.body(), Aluno.class);
            boolean atualizado = alunoService.atualizarAluno(id, alunoAtualizado.getNome(), alunoAtualizado.getNota());
            if (atualizado) {
                return objectMapper.writeValueAsString("Aluno atualizado com sucesso.");
            } else {
                res.status(404);
                return objectMapper.writeValueAsString("Aluno não encontrado.");
            }
        });

        delete("/alunos/:id", (req, res) -> {
            res.type("application/json");
            long id = Long.parseLong(req.params(":id"));
            boolean excluido = alunoService.excluirAluno(id);
            if (excluido) {
                return objectMapper.writeValueAsString("Aluno excluído com sucesso.");
            } else {
                res.status(404);
                return objectMapper.writeValueAsString("Aluno não encontrado.");
            }
        });
    }

    public static void main(String[] args) {
        port(4567);
        new AlunoController();
    }
}
