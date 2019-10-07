package domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// Construindo a classe time e associando seus atributos aos atributos da tabela no banco de dados
@Entity
@Table(name = "times")
public class Times {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @OneToMany(mappedBy = "time")
    private List<Membros> membro;

    @CreationTimestamp //CreationTimestamp faz com que ao ser criado a tabela automicamente adicone a hora da criação
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Times() { // Construtor vazio para que o JPA possa criar a classe com as informações do banco
    }

    public Times(final String nome) {
        this.nome = nome;
    }

    public String toString() {
        return "id: " + id + " nome: " + nome + " createdAt: " + createdAt + " updatedAt: " + updatedAt;
    }

    // Getters e Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAT(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public List<Membros> getMembro(){
        return membro;
    }
    public void setMembro(final List<Membros> membro){
        this.membro = membro;
    }
}