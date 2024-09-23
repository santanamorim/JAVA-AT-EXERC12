import java.util.ArrayList;
import java.util.List;

public class AlunoService {
    private List<Aluno> alunos;

    public AlunoService() {
        alunos = new ArrayList<>();
        alunos.add(new Aluno(1L, "Ana", 8.5));
        alunos.add(new Aluno(2L, "Bruno", 7.0));
        alunos.add(new Aluno(3L, "Carlos", 9.2));
    }

    public List<Aluno> getTodosAlunos() {
        return new ArrayList<>(alunos);
    }

    public Aluno getAlunoById(Long id) {
        return alunos.stream()
                .filter(aluno -> aluno.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean atualizarAluno(Long id, String nome, Double nota) {
        Aluno aluno = getAlunoById(id);
        if (aluno != null) {
            aluno.setNome(nome);
            aluno.setNota(nota);
            return true;
        }
        return false;
    }

    public boolean excluirAluno(Long id) {
        return alunos.removeIf(aluno -> aluno.getId().equals(id));
    }

    public void adicionarAluno(Aluno novoAluno) {
        alunos.add(novoAluno);
    }
}
