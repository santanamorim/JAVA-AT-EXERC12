import lombok.Data;

@Data
public class Aluno {
    private Long id;
    private String nome;
    private Double nota;

    // Construtor
    public Aluno(Long id, String nome, Double nota) {
        this.id = id;
        this.nome = nome;
        this.nota = nota;
    }
}